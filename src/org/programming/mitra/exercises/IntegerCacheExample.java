package org.programming.mitra.exercises;

public class IntegerCacheExample
{
    public static void main(String[] args)
    {
        int a = 127;
        int b = 127;

        System.out.println(a == b); // Output -- true

        Integer cObj = 127; // Auto boxing example, compiler converts it to Integer c = Integer.valueOf(128);
        Integer dObj = 127; // Compiler converts it to Integer d = Integer.valueOf(128);

        int e = cObj; // Auto unboxing example, Compiler converts this line to int e = c.intValue();


        // Output of below line is true because Integer class cache integer objects which falls in range -128 to 127,
        // and returns same object for every autoboxing invocation
        System.out.println(cObj == dObj); // Output -- true

        System.out.println(cObj.equals(dObj)); // Output -- true

        a = 128;
        b = 128;

        System.out.println(a == b); // Output -- true

        cObj = 128;
        dObj = 128;

        System.out.println(cObj == dObj); // Output -- false

        System.out.println(cObj.equals(dObj)); // Output -- true
    }
}
