package org.programming.mitra.exercises;

/**
 * @author Naresh Joshi
 *
 * Java Integer Cache - Why Integer.valueOf(127) == Integer.valueOf(127) Is True
 *
 * See complete article on below link
 *
 * https://www.programmingmitra.com/2018/11/java-integer-cache.html
 */
public class IntegerCacheExample {
    public static void main(String[] args) {
        // For Value 128

        // Operation == on two primitive types with same value returns true because both holds same value
        int a = 128;
        int b = 128;
        System.out.println(a == b); // Output -- true

        // Operation == on two reference types with same value returns false because both are different objects with different addresses
        Integer aObj = 128;
        Integer bObj = 128;
        System.out.println(aObj == bObj); // Output -- false

        System.out.println(aObj.equals(bObj)); // Output -- true

        // For value 127
        int x = 127;
        int y = 127;
        System.out.println(x == y); // Output -- true

        Integer xObj = Integer.valueOf(127); // Auto boxing example, compiler converts it to Integer c = Integer.valueOf(128);
        Integer yObj = 127; // Compiler converts it to Integer d = Integer.valueOf(128);
        System.out.println(xObj == yObj); // Output -- true

        // Output of above line is true because Integer class caches integer objects which falls in range -128 to 127,
        // and returns same object for every autoboxing invocation

        System.out.println(xObj.equals(yObj)); // Output -- true
    }
}
