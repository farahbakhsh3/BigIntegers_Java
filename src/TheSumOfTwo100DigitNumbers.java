import java.math.BigInteger;

public class BigNumbersSum {
    public static void main(String[] args) {
        String number1 = "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        String number2 = "9876543210987654321098765432109876543210987654321098765432109876543210987654321098765432109876543210";

        BigInteger bigInteger1 = new BigInteger(number1);
        BigInteger bigInteger2 = new BigInteger(number2);

        BigInteger sum = bigInteger1.add(bigInteger2);

        System.out.println("جمع دو عدد 100 رقمی: " + sum);
    }
}
