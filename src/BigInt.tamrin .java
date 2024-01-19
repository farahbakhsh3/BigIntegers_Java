
public class BigInt {

    private int[] digits;

    public BigInt(String s) {

        int len = s.length();
    
        digits = new int[len];
    
        for (int i = 0; i < len; i++) {
            
            digits[i] = s.charAt(len - 1 - i) - '0';
        }
    }

    public BigInt(int[] arr) {
        int len = arr.length;
        digits = new int[len];
        for (int i = 0; i < len; i++) {
            digits[i] = arr[len - 1 - i];
        }
    }

    public BigInt add(BigInt o) {
        int max = digits.length > o.digits.length ? digits.length : o.digits.length;
        int[] newdigits = new int[max + 1];
        int carry = 0;
       
        for (int i = 0; i < max; i++) {
           
            int digit1 = i >= digits.length ? 0 : digits[i];
            int digit2 = i >= o.digits.length ? 0 : o.digits[i];
           
            int sum = digit1 + digit2 + carry;
            
            newdigits[i] = sum % 10;
            
            carry = sum / 10;
        }
        
        if (carry > 0) {
            newdigits[max] = carry;
        }
        
        return new BigInt(newdigits);
    }

    
    public BigInt mul(BigInt o) {
        
        int max = digits.length > o.digits.length ? digits.length : o.digits.length;
        
        int[] newdigits = new int[digits.length + o.digits.length];
        
        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                
                int digit1 = i >= digits.length ? 0 : digits[i];
                int digit2 = j >= o.digits.length ? 0 : o.digits[j];
               
                if (digit1 > 0 && digit2 > 0) {
                    int value = digit1 * digit2;
                  
                    int pos = i + j;
              
                    while (value > 0) {
                       
                        int newDigit = (newdigits[pos] + value) % 10;
                       
                        value = (newdigits[pos] + value) / 10;
                        
                        newdigits[pos] = newDigit;
                       