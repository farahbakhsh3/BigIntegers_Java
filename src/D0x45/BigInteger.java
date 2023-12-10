package D0x45;

public class BigInteger implements Cloneable {
    // safe amount of digits to allocate
    public static final int MAX_DIGITS = 1024;

    // digits prefixed with zero (e.g. 125000 -> {...,0,0,0,1,2,5,0,0,0})
    protected byte[] digits;

    // index of the most significant digit in the digits array (e.g. [0, MAX_DIGITS - 1])
    protected int msd;

    // is less than zero
    protected boolean negative;

    public BigInteger() {
        this.digits = new byte[MAX_DIGITS];
        this.msd = MAX_DIGITS - 1;
        this.negative = false;
    }

    public static BigInteger from(String str) {
        BigInteger result = new BigInteger();
        int i = str.length() - 1,
            consecutive_zeros = -1;
        char c = 0;
        result.msd = MAX_DIGITS;

        // str[0] <-- str[str.len-1]
        for (; i >= 0; --i) {
            c = str.charAt(i);
            if (c >= '0' && c <= '9') {
                consecutive_zeros = (c == '0') ? (consecutive_zeros+1) : 0;
                result.digits[--result.msd] = (byte)(c - '0');
            }
            else if (i == 0 && (c == '+' || c == '-')) result.negative = (c == '-');
            else throw new Error(
                String.format("invalid character '%c' at index '%d'", c, i)
            );
        }

        // fix preceding zeros offset
        // by moving most significant pointer forward
        result.msd += consecutive_zeros;

        return result;
    }

    public static BigInteger from(Integer num) { return from(num.toString()); }
    public static BigInteger from(Long num) { return from(num.toString()); }
    public static BigInteger from(Short num) { return from(num.toString()); }
    public static BigInteger from(Byte num) { return from(num.toString()); }

    public boolean equals(BigInteger that) { return this.cmp(that) == 0; }
    public boolean isZero() { return this.digits[this.msd] == 0; }
    public boolean isNegative() { return !this.isZero() && this.negative; }

    public BigInteger clone() {
        BigInteger clone = new BigInteger();
        for (int i = 0; i < MAX_DIGITS; ++i)
            clone.digits[i] = this.digits[i];
        clone.msd = this.msd;
        clone.negative = this.negative;
        return clone;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.isNegative() ? '-' : '+');
        for (int i = this.msd; i < MAX_DIGITS; ++i) {
            // separate every 3 characters with comma
            if ( (i != this.msd) && ((MAX_DIGITS - i) % 3 == 0)) sb.append(',');
            sb.append((char)('0' + this.digits[i]));
        }
        return sb.toString();
    }

    /** 0 if equal, -1 if less and +1 if greater */
    public int cmp(BigInteger that)
    {
        final boolean n_this = this.isNegative(), n_that = that.isNegative();
        int i = this.msd < that.msd ? this.msd : that.msd,
            cmp_d = 0;

        // digits[i] ---> digits[MAX_DIGITS-1]
        // loop starts with i = min(this.msd, that.msd)
        // given this = [0,1,2,3,4] for  "1234", msd = MAX_DIGITS-4
        // and   that = [1,2,3,4,5] for "12345", msd = MAX_DIGITS-5 (minimum)
        //     loop ends ^ here at i=that.msd
        for (; (i < MAX_DIGITS-1) && (this.digits[i] == that.digits[i]); ++i);

        // compare the two left-most digits:
        // this.digits[i]=0, that.digits[i]=1 -> cmp_d=-1, since 0 < 1
        cmp_d = (this.digits[i] != that.digits[i])
            ? (this.digits[i] < that.digits[i] ? -1 : +1)
            : 0;

        // -A <=> B, A <=> -B
        if (n_this && !n_that) return -1;
        if (!n_this && n_that) return +1;

        return cmp_d
            // negate the cmp_d since (A < B) <=> (-A > -B)
            * (n_this && n_that ? -1 : +1);
    }

    public BigInteger add(BigInteger that)
    {
        final boolean n_this = this.isNegative(), n_that = that.isNegative();
        BigInteger a;

        // -A +  B = B - A
        if (n_this && !n_that) {
            a = this.clone();
            a.negative = false;
            return that.sub(a);
        }

        //  A + -B = A - B
        if (!n_this && n_that) {
            a = that.clone();
            a.negative = false;
            return this.sub(a);
        }

        //  A +  B =   A + B
        // -A + -B = -(A + B)
        a = new BigInteger();
        a.negative = n_this;

        for (int i = MAX_DIGITS-1; i >= 0; --i) {
            // 0 <= result.digits[i] <= 18
            a.digits[i] += (byte)(this.digits[i] + that.digits[i]);
            // change msd to i if non-zero digit
            a.msd = (a.digits[i] != 0) ? i : a.msd;
            // store the carry in the result digit
            if (a.digits[i] >= 10) {
                a.digits[i] -= 10;
                ++a.digits[i-1]; // carry
            }
        }

        return a;
    }

    public BigInteger sub(BigInteger that)
    {
        final boolean n_this = this.isNegative(), n_that = that.isNegative();
        BigInteger a, b, c;
        int cmp;
        
        //   A  - (-B) =   A+B
        if (!n_this && n_that) {
            a = that.clone();
            a.negative = false;
            return this.add(a);
        }

        // (-A) -   B  = -(A+B)
        if (n_this && !n_that) {
            a = this.clone();
            a.negative = false;
            b = a.add(that);
            b.negative = true;
            return b;
        }

        // (-A) - (-B) =   B-A
        if (n_this && n_that) {
            a = this.clone();
            b = that.clone();
            a.negative = false;
            b.negative = false;
            return b.sub(a);
        }

        //   A  -   B  =   A-B
        c = new BigInteger();
        cmp = this.cmp(that);

        // swap A and B for negative result
        if (cmp >= 0) {
            a = this;
            b = that;
        } else {
            a = that;
            b = this;
            c.negative = true;
        }

        for (int i = MAX_DIGITS-1; i >= 0; --i) {
            // -9 <= result.
            c.digits[i] += (byte)(a.digits[i] - b.digits[i]);
            c.msd = (c.digits[i] == 0) ? c.msd : i;
            // store the carry in the result digit
            if (c.digits[i] < 0) {
                c.digits[i] += 10;
                --c.digits[i-1];
            }
        }

        return c;
    }
}
