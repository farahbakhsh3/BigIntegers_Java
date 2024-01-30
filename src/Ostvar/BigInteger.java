public class MyBigInteger {
    private String number;

    public MyBigInteger(int n) {
        this.number = String.valueOf(n);
    }

    public MyBigInteger(String n) {
        for (int i = 0; i < n.length(); i++) {
            int d = n.charAt(i) - '0';
            if (d < 0 || d > 9) {
                throw new Error("bad string = " + n);
            }
        }
        this.number = n;
    }

    public String toString() {
        return this.number;
    }

    public MyBigInteger add(MyBigInteger A) {
        StringBuilder C = new StringBuilder();
        int carry = 0,
            l_a = A.number.length(),
            l_b = this.number.length(),
            len = Math.max(l_a, l_b);

        for (int i = 0; i < len || carry != 0; i++) {
            int d0 = (i < l_a) ? (A.number.charAt(l_a - i - 1) - '0') : 0;
            int d1 = (i < l_b) ? (this.number.charAt(l_b - i - 1) - '0') : 0;
            int sum = d0 + d1 + carry;
            if (sum > 9) {
                carry = 1;
                sum -= 10;
            } else {
                carry = 0;
            }
            C.append(sum);
        }

        return new MyBigInteger(C.reverse().toString());
    }

    public MyBigInteger subtract(MyBigInteger B) {
        StringBuilder C = new StringBuilder();
        int borrow = 0,
            l_a = this.number.length(),
            l_b = B.number.length(),
            len = Math.max(l_a, l_b);

        for (int i = 0; i < len || borrow != 0; i++) {
            int d_a = (i < l_a) ? (this.number.charAt(l_a - i - 1) - '0') : 0;
            int d_b = (i < l_b) ? (B.number.charAt(l_b - i - 1) - '0') : 0;
            int diff = d_a - d_b - borrow;
            if (diff < 0) {
                borrow = 1;
                diff += 10;
            } else {
                borrow = 0;
            }
            C.append(diff);
        }

        return new MyBigInteger(C.reverse().toString());
    }
}
