package biginteger;

public class BigIntegers {

    private byte[] number_array;
    private boolean isNegative = false;

    public enum compResultEnum {
        bothIsEqual,
        thisIsGreater,
        thisIssmaller,
    }

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
            if (num >= '0' && num <= '9') {
                this.number_array[i - lb] = Byte.parseByte(Character.toString(num));
            } else {
                throw new IllegalArgumentException("Format is not correct.");
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

    public compResultEnum compareTo(BigIntegers p2) {
        int p1_l = this.number_array.length;
        int p2_l = p2.number_array.length;

        if (this.isNegative && !p2.isNegative) {
            return compResultEnum.thisIssmaller;
        }

        if (!this.isNegative && p2.isNegative) {
            return compResultEnum.thisIsGreater;
        }

        if (p1_l > p2_l) {
            if (this.isNegative) {
                return compResultEnum.thisIssmaller;
            } else {
                return compResultEnum.thisIsGreater;
            }
        }

        if (p2_l > p1_l) {
            if (p2.isNegative) {
                return compResultEnum.thisIsGreater;
            } else {
                return compResultEnum.thisIssmaller;
            }
        }

        for (int idx = 0; idx < p1_l; idx++) {
            if (this.number_array[idx] > p2.number_array[idx]) {
                if (this.isNegative) {
                    return compResultEnum.thisIssmaller;
                } else {
                    return compResultEnum.thisIsGreater;
                }
            } else if (this.number_array[idx] < p2.number_array[idx]) {
                if (p2.isNegative) {
                    return compResultEnum.thisIsGreater;
                } else {
                    return compResultEnum.thisIssmaller;
                }
            }
        }
        return compResultEnum.bothIsEqual;
    }

    public compResultEnum compareTo(String p2) {
        BigIntegers bi_p2 = new BigIntegers(p2);
        return this.compareTo(bi_p2);
    }

    private BigIntegers Addition(BigIntegers p2) {
        int l1 = this.number_array.length;
        int l2 = p2.number_array.length;

        String sum = "";
        int s;
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
        out.isNegative = false;
        return out;
    }

    public void Add(BigIntegers p2) {
        BigIntegers x;
        //+A + +B -> A+B -> A.Addition(B)
        if (!this.isNegative && !p2.isNegative) {
            x = this.Addition(p2);
        } //+A + -B -> A-B -> A.Subtract(B)
        else if (!this.isNegative && p2.isNegative) {
            p2.isNegative = false;
            x = this.Subtract(p2);
            p2.isNegative = true;
        } //-A + +B -> -A+B -> B-A -> B.Subtract(A)
        else if (this.isNegative && !p2.isNegative) {
            this.isNegative = false;
            x = p2.Subtract(this);
        } //-A + -B -> -A-B -> -(A+B) -> -(A.Addition(B))
        else //(this.isNegative && p2.isNegative) 
        {
            p2.isNegative = false;
            this.isNegative = false;
            x = this.Addition(p2);
            p2.isNegative = true;
            x.isNegative = true;
        }
        this.number_array = x.number_array;
        this.isNegative = x.isNegative;
    }

    private BigIntegers Subtract(BigIntegers p2) {
        if (this.compareTo(p2) == compResultEnum.bothIsEqual) {
            return new BigIntegers("0");
        } else if (this.compareTo(p2) == compResultEnum.thisIssmaller) {
            BigIntegers out = p2.Subtract(this);
            out.isNegative = true;
            return out;
        } //else {

        int l1 = this.number_array.length;
        int l2 = p2.number_array.length;

        String sub = "";
        int s;
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
                s -= p2.number_array[idx2];
            }
            if (s < 0) {
                s += 10;
                c = -1;
            } else {
                c = 0;
            }
            sub = s + sub;
        }
        if (c == -1) {
            System.err.println("c: "+c);
        }
        return new BigIntegers(sub);
    }

    public void Sub(BigIntegers p2) {
        BigIntegers x;
        //+A - +B -> A-B -> A.Subtract(B)
        if (!this.isNegative && !p2.isNegative) {
            x = this.Subtract(p2);
        } //+A - -B -> A+B -> A.Addition(B)
        else if (!this.isNegative && p2.isNegative) {
            p2.isNegative = false;
            x = this.Addition(p2);
            p2.isNegative = true;
        } //-A - +B -> -A - +B -> -A-B -> -(A+B) -> -(A.Addition(B))
        else if (this.isNegative && !p2.isNegative) {
            this.isNegative = false;
            x = this.Addition(p2);
            x.isNegative = true;
        } //-A - -B -> -A+B -> B-A -> B.Subtract(A)
        else //(this.isNegative && p2.isNegative) 
        {
            p2.isNegative = false;
            this.isNegative = false;
            x = p2.Subtract(this);
            p2.isNegative = true;
        }

        this.number_array = x.number_array;
        this.isNegative = x.isNegative;
    }
}
