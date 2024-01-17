package sobhan-h007;
import java.util.Arrays;

public class MyBigInteger {
    private int[] digits;

    public MyBigInteger(String number) {
        int length = number.length();
        digits = new int[length];
        for (int i = 0; i < length; i++) {
            digits[i] = number.charAt(length - 1 - i) - '0';
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = digits.length - 1; i >= 0; i--) {
            result.append(digits[i]);
        }
        return result.toString();
    }

    public MyBigInteger add(MyBigInteger other) {
        int maxLength = Math.max(digits.length, other.digits.length);
        int[] result = new int[maxLength + 1];
        int carry = 0;

        for (int i = 0; i < maxLength; i++) {
            int digit1 = i < digits.length ? digits[i] : 0;
            int digit2 = i < other.digits.length ? other.digits[i] : 0;

            int sum = digit1 + digit2 + carry;
            carry = sum / 10;
            result[i] = sum % 10;
        }

        if (carry > 0) {
            result[maxLength] = carry;
        }

        return new MyBigInteger(Arrays.toString(result));
    }

    public MyBigInteger multiply(MyBigInteger other) {
        int[] result = new int[digits.length + other.digits.length];

        for (int i = 0; i < digits.length; i++) {
            int carry = 0;
            for (int j = 0; j < other.digits.length || carry > 0; j++) {
                int product = result[i + j] + digits[i] * (j < other.digits.length ? other.digits[j] : 0) + carry;
                result[i + j] = product % 10;
                carry = product / 10;
            }
        }

        return new MyBigInteger(Arrays.toString(result));
    }

    public static void main(String[] args) {
        MyBigInteger num1 = new MyBigInteger("123456789012345678901234567890");
        MyBigInteger num2 = new MyBigInteger("987654321098765432109876543210");

        // جمع
        MyBigInteger sum = num1.add(num2);
        System.out.println("جمع: " + sum);

        // ضرب
        MyBigInteger product = num1.multiply(num2);
        System.out.println("ضرب: " + product);
    }
}
