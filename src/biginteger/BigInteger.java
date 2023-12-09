
package biginteger;

import biginteger.BigIntegers;


public class BigInteger {

    public static void main(String[] args) {
        
        String P1 = "12";
        String P2 = "25";

        // And Continue...
        BigIntegers p1 = new BigIntegers(P1);
        System.out.println("P1: " + p1.toString());

        BigIntegers p2 = new BigIntegers(P2);
        System.out.println("P2: " + p2.toString());

        System.out.println("---------------");

        p1.Add(p2);
        System.out.println("P1 : " + p1.toString());
        System.out.println("---------------");

        p1.Sub(p2);
        System.out.println("P1 : " + p1.toString());
        System.out.println("---------------");

        System.out.println(p1.compareTo(p2));
    }
}
