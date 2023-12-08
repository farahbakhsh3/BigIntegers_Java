package biginteger;

import jdk.internal.misc.Signal;

public class BigIntegers {

    private byte[] number_array;
    private boolean isNegative = false;

    public BigIntegers(String number) {
        Boolean haveSign = false;

        if (number.startsWith("-")) {
            this.isNegative = true;
            haveSign = true;
        } else if (number.startsWith("+")) {
            this.isNegative = false;
            haveSign = true;
        }

        int lb = haveSign ? 1 : 0;
        int ub = number.length();

        this.number_array = new byte[ub - lb];
        for (int i = lb; i < ub; i++) {
            char num = number.charAt(i);
            if (num > '0' && num < '9') {
                this.number_array[i - lb] = Byte.parseByte(Character.toString(num));
            } else {
                
            }

        }
    }

    @Override
    public String toString() {
        String x = this.isNegative ? "-" : "+";
        int l = this.number_array.length;
        for (int i = 0; i < l; i++) {
            x += this.number_array[i];
        }
        return x;
    }

    public void toStringIndex() {
        int l = this.number_array.length;
        System.out.println();
        for (int i = 0; i < l; i++) {
            System.out.printf("%d : %d\n", i, this.number_array[i]);
        }
    }

    public void Add(BigIntegers p2) {
        int l1 = this.number_array.length;
        int l2 = p2.number_array.length;

        int l = l1 > l2 ? l1 : l2;
        String sum = "";
        int s = 0;
        int c = 0;
        for (int idx1 = l1 - 1, idx2 = l2 - 1;
                idx1 >= 0 || idx2 >= 0;
                idx1--, idx2--) {
            s = 0;
            s += c;
            if (idx1 >= 0) {
                s += this.number_array[idx1];
            }
            if (idx2 >= 0) {
                s += p2.number_array[idx2];
            }
            if (s > 9) {
                s -= 10;
                c = 1;
            } else {
                c = 0;
            }
            sum = s + sum;
        }
        if (c == 1) {
            sum = c + sum;
        }

        BigIntegers out = new BigIntegers(sum);
        this.number_array = out.number_array;
    }

    public void Add(String p2) {
        BigIntegers bi_p2 = new BigIntegers(p2);
        this.Add(bi_p2);
    }
}
