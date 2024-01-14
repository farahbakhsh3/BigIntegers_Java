package AliAC;

public class BigInteger {
    public boolean negative;
    public String string_value;
    private String string_value_no_sign;
    private int[] value;
    private int[] temp_value;
    private int l_value;

    public BigInteger(String value){
        this.negative = false;
        this.string_value = value;
        if (value.startsWith("-")){
            this.negative = true;
            value = value.substring(1);
        }
        this.string_value_no_sign = value;
        this.value = new int[string_value_no_sign.length()];
        for (int i=0; i<value.length();i++){
            this.value[i] = Character.getNumericValue(value.charAt(i));
        }
        this.l_value = value.length();
    }

    public String toString(){
        return this.string_value;
    }

    private BigInteger[] evaluate(BigInteger that){
        BigInteger a = this;
        BigInteger b = that;
        BigInteger[] items = new BigInteger[2];

        if (a.l_value == b.l_value){
            for (int i = 0; i<a.l_value; i++){
                if (a.value[i]<b.value[i]){
                    BigInteger temp = a;
                    a = b;
                    b = temp;
                    break;
                }
                else if (a.value[i]>b.value[i]){
                    break;
                }
            }
        }
        else if (b.l_value > a.l_value){
            BigInteger temp = a;
            a = b;
            b = temp;
        }

        
        b.temp_value = new int[b.l_value+(a.l_value-b.l_value)];       
        for (int i=0; i<(a.l_value-b.l_value); i++){
            b.temp_value[i] = 0;
        }
        int j = 0;
        for (int i=a.l_value-b.l_value; i<a.l_value; i++){
            b.temp_value[i] = b.value[j];
            j++;
        }

        items[0] = a;
        items[1] = b;

        return items;
    }
    
    private BigInteger[] evaluate(BigInteger that, boolean sub){
        BigInteger a = this;
        BigInteger b = that;
        BigInteger[] items = new BigInteger[3];
        BigInteger n = new BigInteger("0");
        

        if (a.l_value == b.l_value){
            for (int i = 0; i<a.l_value; i++){
                if (a.value[i]<b.value[i]){
                    if (sub){
                        n = new BigInteger("1");
                    }
                    BigInteger temp = a;
                    a = b;
                    b = temp;
                    break;
                }
                else if (a.value[i]>b.value[i]){
                    break;
                }
            }
        }
        else if (b.l_value > a.l_value){
            if (sub){
                n = new BigInteger("1");
                    }
            BigInteger temp = a;
            a = b;
            b = temp;
        }

        
        b.temp_value = new int[b.l_value+(a.l_value-b.l_value)];       
        for (int i=0; i<(a.l_value-b.l_value); i++){
            b.temp_value[i] = 0;
        }
        int j = 0;
        for (int i=a.l_value-b.l_value; i<a.l_value; i++){
            b.temp_value[i] = b.value[j];
            j++;
        }

        items[0] = a;
        items[1] = b;
        items[2] = n;

        return items;
    }

    private boolean compare_no_sign(BigInteger that){
        if (this.l_value > that.l_value){
            return true;
        }
        else if (this.l_value < that.l_value){
            return false;
        }
        else if (this.l_value == that.l_value){
            for (int i=0; i<this.l_value;i++){
                if (this.value[i]<that.value[i]){
                    return false;
                }
                else if (this.value[i]>that.value[i]){
                    return true;
                }
            }
        }
        return true;
    }

    public boolean gt(BigInteger that){
        if (this.negative && that.negative){
            return ! this.compare_no_sign(that);
        }
        else if (this.negative){
            return false;
        }
        else if (that.negative){
            return true;
        }
        else if (this.string_value_no_sign.equals(that.string_value_no_sign)){
            return ! this.compare_no_sign(that);
        }
        return  this.compare_no_sign(that);
    }

    public boolean lt(BigInteger that){
        if (this.negative && that.negative){
            if (this.string_value_no_sign.equals(that.string_value_no_sign)){
                return ! this.compare_no_sign(that);
            }
            return this.compare_no_sign(that);
        }
        else if (this.negative){
            return true;
        }
        else if (that.negative){
            return false;
        }
        else if (this.string_value_no_sign.equals(that.string_value_no_sign)){
            return ! this.compare_no_sign(that);
        }
        return  ! this.compare_no_sign(that);
    }

    public boolean ge(BigInteger that){
        if (this.negative && that.negative){
            if (this.string_value_no_sign.equals(that.string_value_no_sign)){
                return this.compare_no_sign(that);
            }
            return ! this.compare_no_sign(that);
        }
        else if (this.negative){
            return false;
        }
        else if (that.negative){
            return true;
        }
        else if (this.string_value_no_sign.equals(that.string_value_no_sign)){
            return this.compare_no_sign(that);
        }
        return  this.compare_no_sign(that);
    }

    public boolean le(BigInteger that){
        if (this.negative && that.negative){
            return this.compare_no_sign(that);
        }
        else if (this.negative){
            return true;
        }
        else if (that.negative){
            return false;
        }
        else if (this.string_value_no_sign.equals(that.string_value_no_sign)){
            return this.compare_no_sign(that);
        }
        return ! this.compare_no_sign(that);
    }

    private static String inner_add(BigInteger a, BigInteger b){
            String result = "";
            int carry = 0;
            for (int i=a.l_value-1; i>=0; i--){
                int p = a.value[i]+b.temp_value[i]+carry;
                if (p >= 10){
                    if (i == 0){
                        for (int j=String.valueOf(p).length()-1;j>=0;j--){ 
                            result = String.valueOf(p).charAt(j)+result ;
                        }
                    }
                    else{
                        carry = p/10;
                        result = p%10 + result;
                    }
                }
                else{
                    carry = 0;
                    result = p + result;
                }
            }
            return result;
            }

    public BigInteger add(BigInteger that){
        BigInteger a,b;
        BigInteger[] items = this.evaluate(that);
        String num_result;
        a = items[0];
        b = items[1];
        BigInteger inner_this = new BigInteger(a.string_value_no_sign);
        BigInteger inner_that = new BigInteger(b.string_value_no_sign);
        
        if (a.negative && b.negative){
            num_result = "-"+inner_add(a,b);
        }
        else if (a.negative){
            
            num_result = "-"+inner_this.sub(b).string_value;
        }
        else if (b.negative){
            num_result = inner_this.sub(inner_that).string_value;
        }
        else{
            num_result = inner_add(a,b);
        }
        
        return new BigInteger(num_result);
            
        }


    private static String inner_sub(BigInteger a, BigInteger b){
            String result = "";
            int borrow = 0;
            for (int i=a.l_value-1; i>=0; i--){
                int p = a.value[i]-borrow-b.temp_value[i];
                if (p<0){
                    result = a.value[i]-borrow+10-b.temp_value[i]+result;
                    borrow = 1;
                }
                else{
                    result= a.value[i]-borrow-b.temp_value[i]+result;
                    borrow = 0;
                }
            }
            if (result.length()>1){ 
                while (result.startsWith("0")){
                    result = result.substring(1);
                }
            }
            
            return result;
    }

    public BigInteger sub(BigInteger that){
        BigInteger a,b;
        BigInteger n;
        BigInteger[] items = this.evaluate(that,true);
        String num_result;
        BigInteger inner_this;
        BigInteger inner_that;
        a = items[0];
        b = items[1];
        n = items[2];
        inner_this = new BigInteger(a.string_value_no_sign);
        inner_that = new BigInteger(b.string_value_no_sign);

        if (this.string_value.equals("0") && !that.string_value.equals("0") && !that.negative){
            return new BigInteger("-"+b.string_value);
        }
        else if ((this.string_value.equals("0")) && (that.negative)){
            return inner_that;
        }
        else if (this.string_value.equals(that.string_value)){
            return new BigInteger("0");
        }


        if (a.negative && b.negative){
            if (n.string_value.equals("1")){
                num_result = inner_sub(a,b);
            }
            else{
                num_result = "-"+inner_sub(a,b);
            }
        }
        else if (a.negative){
            if (n.string_value.equals("1")){
                num_result = inner_this.add(inner_that).string_value;
            }
            else{
                num_result = "-"+inner_this.add(inner_that).string_value;
            }
        }
        else if (b.negative){
            if (n.string_value.equals("1")){
                num_result = "-"+inner_this.add(inner_that).string_value;
            }
            else{
                num_result = inner_this.add(inner_that).string_value;
            }
        }
        else{
            if (n.string_value.equals("1")){
                num_result = "-"+inner_sub(a,b);
            }
            else{
                num_result = inner_sub(a,b);
            }
        }
        
        return new BigInteger(num_result);
    }

    public BigInteger mul(BigInteger that){
        BigInteger a,b;
        BigInteger[] items = this.evaluate(that);
        BigInteger num_result = new BigInteger("0");
        a = items[0];
        b = items[1];

        
        if (a.string_value.equals("0") || b.string_value.equals("0")){
            return new BigInteger("0");
        }
        int carry = 0;
        int zero_counter = 0;
        for (int i = b.temp_value.length - 1; i >= 0; i--){
            String part = "";
            if (b.temp_value[i] == 0){
                zero_counter += 1;
                continue;
            }
            if (zero_counter>0){
                part = part+"0".repeat(zero_counter);
            }
            carry = 0;
            for (int j = a.value.length - 1; j >= 0; j--){
                int p = b.temp_value[i]*a.value[j]+carry;
                if (p>9){    
                    part = p%10+part;
                    carry = p/10;
                }
                else{
                   part = p+part;
                    carry = 0;
                }
            }
            if (carry != 0){
                part = carry+part;
            }
            zero_counter++;
                
            num_result = num_result.add(new BigInteger(part));
        }
        
        if (a.negative && b.negative){
            }
        else if (a.negative || b.negative){
            num_result = new BigInteger("-"+num_result.string_value);
        }
                
        return num_result;
    }
	
	
	public BigInteger pow(int exponent) {
    if (exponent < 0) {
        throw new IllegalArgumentException("Exponent must be non-negative");
    }

    BigInteger result = new BigInteger("1");
    BigInteger base = this;

    for (int i = 0; i < exponent; i++) {
        result = result.mul(base);
    }

    return result;
	}



    private static String inner_div(BigInteger inner_this, BigInteger that, String c, int n, int zeros){
        if (n == zeros && zeros !=0){  
            c = c+".";
        }
        if (n == 15){
            if (zeros== 0){
                return c.charAt(0)+"."+c.charAt(1);
            }
            if (zeros > n){
                return c.charAt(0)+"."+c.substring(1)+"e+"+zeros;
            }
            return c;
        }
        inner_this = inner_this.mul(new BigInteger("10"));
        int j = 0;
        while (inner_this.ge(that)){
            inner_this = inner_this.sub(that);
            j++;
        }
        n++;
        c = c+j;
        
        return inner_div(inner_this,that,c,n,zeros);
    }
    public String div(BigInteger that){
        int zeros = this.l_value-that.l_value;
        that = that.mul(new BigInteger('1'+"0".repeat(zeros)));

        if (this.string_value !="0"){
            return inner_div(this,that,"",0,zeros);
        }
        else{
            return "0";
        }
    }
}