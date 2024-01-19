import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        // ایجاد دو شی BigInteger
        BigInteger num1 = new BigInteger("123456789012345678901234567890"); // مقدار اول
        BigInteger num2 = new BigInteger("987654321098765432109876543210"); // مقدار دوم

        // جمع اعداد بزرگ
        BigInteger sum = num1.add(num2);
        System.out.println("جمع: " + sum);

        // تفریق اعداد بزرگ
        BigInteger difference = num1.subtract(num2);
        System.out.println("تفریق: " + difference);

        // ضرب اعداد بزرگ
        BigInteger product = num1.multiply(num2);
        System.out.println("ضرب: " + product);

        // تقسیم اعداد بزرگ
        BigInteger quotient = num1.divide(num2);
        System.out.println("تقسیم: " + quotient);
    }
}