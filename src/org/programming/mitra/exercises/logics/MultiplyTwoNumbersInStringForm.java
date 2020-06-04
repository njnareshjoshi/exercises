package org.programming.mitra.exercises.logics;

public class MultiplyTwoNumbersInStringForm {
    public static void main(String[] args) {
        System.out.println(multiply("0", "0") + " == " + (0));
        System.out.println(multiply("9234", "431") + " == " + (9234 * 431));
        System.out.println(multiply("2", "6") + " == " + (2 * 6));
        System.out.println(multiply("12", "3") + " == " + (12 * 3));
        System.out.println(multiply("10", "890") + " == " + (10 * 890));
        System.out.println(multiply("999", "99999") + " == " + (999 * 99999));
        System.out.println(multiply("-999", "99999") + " == " + (-999 * 99999));
        System.out.println(multiply("999", "-999999") + " == " + (999 * -999999));
        System.out.println(multiply("-999", "-99999") + " == " + (-999 * -99999));
        System.out.println(multiply("999", "999999") + " == " + (999 * 999999));
    }

    public static String multiply(String num1, String num2) {

        boolean isNegative = false;
        if (num1.charAt(0) == '-') {
            isNegative = true;
            num1 = num1.substring(1);
        }

        if (num2.charAt(0) == '-') {
            if (isNegative) {
                isNegative = false;
            } else {
                isNegative = true;
            }
            num2 = num2.substring(1);
        }

        int m = num1.length();
        int n = num2.length();

        int[] result = new int[m + n];
        for (int i = n - 1; i >= 0; i--) {
            int carry = 0;
            int position = m + i;
            for (int j = m - 1; j >= 0; j--) {
                int product = result[position] + carry + ((num2.charAt(i) - '0') * (num1.charAt(j) - '0'));
                result[position] = product % 10;
                carry = product / 10;
                position--;
            }
            result[position] = carry;
        }

        boolean leadingZeros = true;
        StringBuilder product = new StringBuilder(isNegative ? "-" : "");
        for (int i : result) {
            if (i > 0) {
                leadingZeros = false;
            }

            if (!leadingZeros) {
                product.append(i);
            }
        }

        return product.length() == 0 ? "0" : product.toString();
    }
}
