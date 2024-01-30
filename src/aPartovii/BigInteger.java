package aPartovii;

class BigInteger {
    private byte[] numbers;
    private boolean negative = false;

    public enum Compare {
        EQUAL,
        GREATER,
        LESS,
    }

    private static String validate_number_string(String str) {

        if (str.length() == 0) {
            return "0";
        }

        int lb = 0;
        int ub = str.length();
        String x = "";
        boolean has_sign = false;

        if (str.startsWith("+") || str.startsWith("-")) {
            lb = 1;
            x += str.charAt(0);
            has_sign = true;
        }
        boolean firstNoneZeroNumber = false;
        for (int idx = lb; idx < ub; idx++) {
            char num = str.charAt(idx);

            if (num == '0' && !firstNoneZeroNumber) continue;
            firstNoneZeroNumber = true;
        
            if (num >= '0' && num <= '9') {
                x += num;
            }
        }

        if (x.length() == 0) {
            x = "0";
        } else if (x.length() == 1 && has_sign) {
            x = "0";
        }

        return x;
    }

    public BigInteger(String number) {
        boolean has_sign = false;
        number = validate_number_string(number);

        if (number.startsWith("-")) {
            this.negative = true;
            has_sign = true;
        } else if (number.startsWith("+")) {
            this.negative = false;
            has_sign = true;
        }

        int lb = has_sign ? 1 : 0;
        int ub = number.length();

        this.numbers = new byte[ub - lb];
        for (int i = lb; i < ub; i++) {
            char num = number.charAt(i);
            this.numbers[i - lb] = Byte.parseByte(Character.toString(num));
        }
    }

    @Override
    public String toString() {
        String x = this.negative ? "-" : "+";
        int l = this.numbers.length;
        for (int i = 0; i < l; i++) {
            x += this.numbers[i];
        }

        if (x.compareTo("-0") == 0 
                || x.compareTo("+0")==0) {
            x = "0";
        }
        return x;
    }

    public Compare compareTo(BigInteger p2) {
        int p1_l = this.numbers.length;
        int p2_l = p2.numbers.length;

        if (this.negative && !p2.negative) {
            return Compare.LESS;
        }

        if (!this.negative && p2.negative) {
            return Compare.GREATER;
        }

        if (p1_l > p2_l) {
            if (this.negative) {
                return Compare.LESS;
            } else {
                return Compare.GREATER;
            }
        }

        if (p2_l > p1_l) {
            if (p2.negative) {
                return Compare.GREATER;
            } else {
                return Compare.LESS;
            }
        }

        for (int idx = 0; idx < p1_l; idx++) {
            if (this.numbers[idx] > p2.numbers[idx]) {
                if (this.negative) {
                    return Compare.LESS;
                } else {
                    return Compare.GREATER;
                }
            } else if (this.numbers[idx] < p2.numbers[idx]) {
                if (p2.negative) {
                    return Compare.GREATER;
                } else {
                    return Compare.LESS;
                }
            }
        }
        return Compare.EQUAL;
    }

    public Compare compareTo(String b) {
        BigInteger bi_p2 = new BigInteger(b);
        return this.compareTo(bi_p2);
    }

    private BigInteger _addition(BigInteger b) {
        int l1 = this.numbers.length;
        int l2 = b.numbers.length;

        String sum = "";
        int s;
        int c = 0;
        for (int idx1 = l1 - 1, idx2 = l2 - 1;
                idx1 >= 0 || idx2 >= 0;
                idx1--, idx2--) {
            s = 0;
            s += c;
            if (idx1 >= 0) {
                s += this.numbers[idx1];
            }
            if (idx2 >= 0) {
                s += b.numbers[idx2];
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

        BigInteger out = new BigInteger(sum);
        out.negative = false;
        return out;
    }

    private BigInteger _subtract(BigInteger a) {
        if (this.compareTo(a) == Compare.EQUAL) {
            return new BigInteger("0");
        } else if (this.compareTo(a) == Compare.LESS) {
            BigInteger out = a._subtract(this);
            out.negative = true;
            return out;
        }

        int l1 = this.numbers.length;
        int l2 = a.numbers.length;

        String sub = "";
        int c = 0, s;
        for (int idx1 = l1 - 1, idx2 = l2 - 1;
                idx1 >= 0 || idx2 >= 0;
                idx1--, idx2--) {
            s = 0;
            s += c;
            if (idx1 >= 0) {
                s += this.numbers[idx1];
            }
            if (idx2 >= 0) {
                s -= a.numbers[idx2];
            }
            if (s < 0) {
                s += 10;
                c = -1;
            } else {
                c = 0;
            }
            sub = s + sub;
        }

        if (c == -1) System.err.println("c: " + c);        
        return new BigInteger(sub);
    }

    public void add(BigInteger b) {
        BigInteger tmp;
        if (!this.negative && !b.negative) {
            tmp = this._addition(b);
        } else if (!this.negative && b.negative) {
            b.negative = false;
            tmp = this._subtract(b);
            b.negative = true;
        } else if (this.negative && !b.negative) {
            this.negative = false;
            tmp = b._subtract(this);
        } else {
            b.negative = false;
            this.negative = false;
            tmp = this._addition(b);
            b.negative = true;
            tmp.negative = true;
        }
        this.numbers = tmp.numbers;
        this.negative = tmp.negative;
    }

    public void sub(BigInteger a) {
        BigInteger tmp;

        if (!this.negative && !a.negative) {
            tmp = this._subtract(a);
        } else if (!this.negative && a.negative) {
            a.negative = false;
            tmp = this._addition(a);
            a.negative = true;
        } else if (this.negative && !a.negative) {
            this.negative = false;
            tmp = this._addition(a);
            tmp.negative = true;
        } else {
            a.negative = false;
            this.negative = false;
            tmp = a._subtract(this);
            a.negative = true;
        }

        this.numbers = tmp.numbers;
        this.negative = tmp.negative;
    }
}
