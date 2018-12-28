package org.programming.mitra.exercises;

import java.lang.reflect.Field;

/**
 * @author Naresh Joshi
 *
 * See complete articles on below links
 *
 * https://www.programmingmitra.com/2018/02/why-string-is-immutable-and-final-in-java.html,
 * https://www.programmingmitra.com/2018/02/how-to-create-immutable-class-in-java.html
 */
public class StringImmutabilityExample {
    public static void main(String[] args) throws Exception {

        String string = "Naresh";

        Class<String> type = String.class;
        Field field = type.getDeclaredField("value");
        field.setAccessible(true);

        char[] value = (char[]) field.get(string);
        value[0] = 'M';

        System.out.println(string);

    }
}
