package org.programming.mitra.exercises;

import java.lang.reflect.Field;

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
