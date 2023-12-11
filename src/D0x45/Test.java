package D0x45;

public class Test {
    protected static final char[] cmp_char = { '<', '=', '>' };

    public static final int CMP_EQUAL = 0,
            CMP_LESS = -1,
            CMP_GREATER = +1;

    public static void main(String[] args) {
        // test comparison
        cmp("1", "1", CMP_EQUAL);
        cmp("0", "0", CMP_EQUAL);
        cmp("-1", "+1", CMP_LESS);
        cmp("+1", "-1", CMP_GREATER);
        cmp("-5", "-5", CMP_EQUAL);
        cmp("-5", "-6", CMP_GREATER);
        cmp("-6", "-5", CMP_LESS);
        cmp("1", "10", CMP_LESS);
        cmp("10", "0", CMP_GREATER);
        cmp("-10", "+9", CMP_LESS);

        // test addition
        add("0", "0", "+0");
        add("-0", "-0", "+0");
        add("+0", "-0", "+0");
        add("-0", "+0", "+0");
        add("-1", "-0", "-1");
        add("-1", "-1", "-2");
        add("-2", "+2", "+0");
        add("10", "+9", "+19");
        add("-100", "-100", "-200");
        add("-10", "-9", "-19");
        add("-190", "+90", "-100");
        add("-180", "+90", "-90");
        add("-100", "+90", "-10");
        add("+999999", "+1", "+1,000,000");
        add("+100000", "-1", "+99,999");

        // subtraction
        sub("0", "0", "+0");
        sub("-0", "+1", "-1");
        sub("+1", "-1", "+2");
        sub("+100000", "+1", "+99,999");
        sub("0", "+99999", "-99,999");
        sub("-9", "+99990", "-99,999");
        sub("0", "0", "+0");
        sub("-10", "-5", "-5");
        sub("100", "110", "-10");
        sub("100", "80", "+20");

        // multiplication
        mul("0", "0", "+0");
        mul("34", "12", "+408");
        mul("+11", "-55", "-605");
        mul("+1", "-1", "-1");
        mul("-1", "-1", "+1");
        mul("-9999", "-9", "+89,991");
        mul("23958233", "5830", "+139,676,498,390");
    }

    public static void cmp(BigInteger a, BigInteger b, int expected) {
        final int cmp = a.cmp(b);
        System.out.format(
                "A = %s [%c] B = %s\n",
                a.toString(),
                cmp_char[cmp + 1],
                b.toString());
        if (cmp != expected)
            throw new Error(String.format("expected %d for compare, got: %d", expected, cmp));
    }

    public static void add(BigInteger a, BigInteger b, String expected) {
        final String addition = a.add(b).toString();
        System.out.format(
                "%s + %s = %s\n",
                a.toString(),
                b.toString(),
                addition);
        if (!expected.equalsIgnoreCase(addition))
            throw new Error(String.format("expected '%s' for addition, got: %s", expected, addition));
    }

    public static void sub(BigInteger a, BigInteger b, String expected) {
        final String subtraction = a.sub(b).toString();
        System.out.format(
                "%s - %s = %s\n",
                a.toString(),
                b.toString(),
                subtraction);
        if (!expected.equalsIgnoreCase(subtraction))
            throw new Error(String.format("expected '%s' for subtraction, got: %s", expected, subtraction));
    }

    public static void mul(BigInteger a, BigInteger b, String expected) {
        final String multiplication = a.mul(b).toString();
        System.out.format(
                "%s * %s = %s\n",
                a.toString(),
                b.toString(),
                multiplication);
        if (!expected.equalsIgnoreCase(multiplication))
            throw new Error(String.format("expected '%s' for multiplication, got: %s", expected, multiplication));
    }

    public static void cmp(String a, String b, int e) { cmp(BigInteger.from(a), BigInteger.from(b), e); }
    public static void add(String a, String b, String e) { add(BigInteger.from(a), BigInteger.from(b), e); }
    public static void sub(String a, String b, String e) { sub(BigInteger.from(a), BigInteger.from(b), e); }
    public static void mul(String a, String b, String e) { mul(BigInteger.from(a), BigInteger.from(b), e); }
}
