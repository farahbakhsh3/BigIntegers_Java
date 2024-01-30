import java.util.* ;

public class SimpleBigInteger {
    private static final int MAX_DIGITS = 100;
    private int[] digits;
    private boolean negative;

    public SimpleBigInteger() {
        digits = new int[MAX_DIGITS];
        negative = false;
    }

    public SimpleBigInteger(String num) {
        this();
        parseString(num);
    }

    private void parseString(String num) {
        int startIndex = 0;

        if (num.charAt(0) == '-') {
            negative = true;
            startIndex = 1;
        }

        for (int i = num.length() - 1, j = 0; i >= startIndex; i--, j++) {
            digits[j] = num.charAt(i) - '0';
        }
    }

    public SimpleBigInteger add(SimpleBigInteger other) {
        SimpleBigInteger result = new SimpleBigInteger();
        int carry = 0;

        for (int i = 0; i < MAX_DIGITS; i++) {
            int sum = digits[i] + other.digits[i] + carry;
            result.digits[i] = sum % 10;
            carry = sum / 10;
        }

        result.negative = negative || other.negative;
        return result;
    }

    public SimpleBigInteger subtract(SimpleBigInteger other) {
        SimpleBigInteger result = new SimpleBigInteger();
        int borrow = 0;

        for (int i = 0; i < MAX_DIGITS; i++) {
            int diff = digits[i] - other.digits[i] - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result.digits[i] = diff;
        }

        result.negative = negative || other.negative;
        return result;
    }

    public SimpleBigInteger multiply(SimpleBigInteger other) {
        SimpleBigInteger result = new SimpleBigInteger();
        SimpleBigInteger temp = new SimpleBigInteger();

        for (int i = 0; i < MAX_DIGITS; i++) {
            int carry = 0;

            for (int j = 0; j < MAX_DIGITS; j++) {
                int product = digits[i] * other.digits[j] + temp.digits[i + j] + carry;
                temp.digits[i + j] = product % 10;
                carry = product / 10;
            }
        }

        result.digits = Arrays.copyOf(temp.digits, MAX_DIGITS);
        result.negative = negative || other.negative;
        return result;
    }

    public SimpleBigInteger divide(SimpleBigInteger divisor) {
        SimpleBigInteger result = new SimpleBigInteger();
        SimpleBigInteger remainder = new SimpleBigInteger();

        SimpleBigInteger numerator = this.clone();
        SimpleBigInteger absDivisor = divisor.clone();
        absDivisor.negative = false;

        while (numerator.cmp(absDivisor) >= 0) {
            int count = 0;

            while (numerator.cmp(absDivisor) >= 0) {
                numerator = numerator.subtract(absDivisor);
                count++;
            }

            result = result.add(new SimpleBigInteger(Integer.toString(count)));
        }

        remainder = numerator.clone();
        result.negative = negative || divisor.negative;
        return result;
    }

    private int cmp(SimpleBigInteger other) {
        for (int i = MAX_DIGITS - 1; i >= 0; i--) {
            if (digits[i] < other.digits[i]) {
                return -1;
            } else if (digits[i] > other.digits[i]) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        if (negative) {
            result.append("-");
        }

        boolean leadingZeros = true;
        for (int i = MAX_DIGITS - 1; i >= 0; i--) {
            if (leadingZeros && digits[i] == 0) {
                continue;
            } else {
                leadingZeros = false;
            }
            result.append(digits[i]);
        }

        if (result.length() == 0) {
            result.append("0");
        }

        return result.toString();
    }

    public SimpleBigInteger clone() {
        SimpleBigInteger clone = new SimpleBigInteger();
        clone.digits = Arrays.copyOf(digits, MAX_DIGITS);
        clone.negative = negative;
        return clone;
    }

    public static void main(String[] args) {
        SimpleBigInteger num1 = new SimpleBigInteger("123456789012345678901234567890");
        SimpleBigInteger num2 = new SimpleBigInteger("987654321098765432109876543210");

        // Addition
        SimpleBigInteger sum = num1.add(num2);
        System.out.println("Sum: " + sum);

        // Subtraction
        SimpleBigInteger diff = num1.subtract(num2);
        System.out.println("Difference: " + diff);

        // Multiplication
        SimpleBigInteger product = num1.multiply(num2);
        System.out.println("Product: " + product);

        // Division
        SimpleBigInteger quotient = num1.divide(num2);
        System.out.println("Quotient: " + quotient);
    }
}
