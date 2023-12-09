
package biginteger;

import biginteger.BigIntegers;


public class BigInteger {

    public static void main(String[] args) {
        
        String P1 = "20";
        String P2 = "25";

        // And Continue...
        BigIntegers p1 = new BigIntegers(P1);
        System.out.println(p1.toString());

        BigIntegers p2 = new BigIntegers(P2);
        System.out.println(p2.toString());

        p1.Add(p2);
        System.out.println(p1.toString());
        System.err.println("---------------");

        p1.Sub(p2);
        System.out.println(p1.toString());
        System.err.println("---------------");

        //System.err.println(p1.compareTo(p2));
    }
}
