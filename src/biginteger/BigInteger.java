// An lagorithm for bigIntegerNumbers in java
// Step 0 : build an structure for BigIntegers
// Step 1 : Addition , Subtract
package biginteger;
import biginteger.BigIntegers;
        

public class BigInteger {

    public static void main(String[] args) {
        
        String P1 = "+100001";
        String P2 = "123321";
        
        // And Continue...
        BigIntegers p1 = new BigIntegers(P1);
        System.out.println(p1.toString());        
        BigIntegers p2 = new BigIntegers(P2);
        System.out.println(p2.toString());       
        
        //p1.toStringIndex();
        //p2.toStringIndex();

        p1.Add(p2);
        System.out.println(p1.toString());        
    }    
}
