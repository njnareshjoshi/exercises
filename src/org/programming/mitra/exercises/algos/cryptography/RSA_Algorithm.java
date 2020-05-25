package org.programming.mitra.exercises.algos.cryptography;

import java.math.BigInteger;
import java.util.Random;

public class RSA_Algorithm {

    public static char[] multiply(char[] num1, char[] num2) {

        boolean isNegative = false;
        if (num1[0] == '-') {
            isNegative = true;
            num1[0] = 0;
        }

        if (num2[0] == '-') {
            isNegative = !isNegative;
            num2[0] = 0;
        }

        int m = num1.length;
        int n = num2.length;

        int[] result = new int[m + n];
        for (int i = n - 1; i >= 0; i--) {
            int carry = 0;
            int position = m + i;
            for (int j = m - 1; j >= 0; j--) {
                int product = result[position] + carry + (toInt(num2[i]) * toInt(num1[j]));
                result[position] = product % 10;
                carry = product / 10;
                position--;
            }
            result[position] = carry;
        }

        String product = removeLeadingZeros(result);
        return (isNegative ? "-" + product : product).toCharArray();
    }

    // Method expects both number to be positive
    public static String add(char[] num1, char[] num2) {
        int m = num1.length;
        int n = num2.length;
        int s = Math.max(m, n);

        int[] result = new int[s + 1];
        while (m > 0 || n > 0) {
            char c1 = (m > 0) ? num1[m - 1] : '0';
            char c2 = (n > 0) ? num2[n - 1] : '0';

            int sum = toInt(c1) + toInt(c2) + result[s];
            result[s] = sum % 10;
            result[s - 1] = sum / 10;

            m--;
            n--;
            s--;
        }

        return removeLeadingZeros(result);
    }

    // Method expects both number to be positive and num1 >= num2
    public static char[] subtract(char[] num1, char[] num2) {
        num1 = removeLeadingZeros(num1);
        num2 = removeLeadingZeros(num2);

        int m = num1.length;
        int n = num2.length;
        int s = Math.max(m, n);

        char[] result = new char[s];
        while (m > 0 || n > 0) {
            int c1 = toInt((m > 0) ? num1[m - 1] : '0');
            int c2 = toInt((n > 0) ? num2[n - 1] : '0');

            if (c1 < c2 && m > 0) {
                c1 = 10 + c1;
                num1[m - 2] = toChar(toInt(num1[m - 2]) - 1);
            }

            result[s - 1] = toChar(c1 - c2);

            m--;
            n--;
            s--;
        }

        return removeLeadingZeros(result);
    }

    public static char[] divide(char[] num1, char[] num2) {
        return divideAndMod(num1, num2)[0].toCharArray();
    }

    public static char[] mod(char[] num1, char[] num2) {
        return divideAndMod(num1, num2)[1].toCharArray();
    }

    // gcd(num1, num2) = x.num1 + y.num2, where x is the mod inverse of num1
    public static char[] modInverse(char[] num1, char[] num2) {
        num1 = removeLeadingZeros(num1);
        num2 = removeLeadingZeros(num2);


        if (num2[0] == '0') {
            return "1".toCharArray();
        } else {

        }

        return gcd(num2, mod(num1, num2));
    }

    static int modInverse(int a, int b) {
        int m0 = b;
        int x = 1, y = 0;

        if (b == 1)
            return 0;

        while (a > 1) {
            // q is quotient
            int q = a / b;

            int t = b;

            // b is remainder now, process same as Euclid's algo
            b = a % b;
            a = t;
            t = y;

            // Update x and y
            y = x - q * y;
            x = t;
        }

        // Make x positive
        if (x < 0)
            x += m0;

        return x;
    }

    static int modPower(int x, int y, int p) {
        // Initialize result
        int res = 1;

        // Update x if it is more than or equal to p
        x = x % p;

        if (x == 0)
            return 0; // In case x is divisible by p;

        while (y > 0) {
            // If y is odd, multiply x with result
            if ((y & 1) == 1)
                res = (res * x) % p;

            // y must be even now
            y = y / 2;
            x = (x * x) % p;
        }
        return res;
    }

    public static String[] divideAndMod(char[] num1, char[] num2) {
        num1 = removeLeadingZeros(num1);
        num2 = removeLeadingZeros(num2);

        int quotient = 0;
        while (compare(num1, num2) >= 0) {
            num1 = subtract(num1, num2);
            quotient++;
        }

        return new String[]{String.valueOf(quotient), String.valueOf(num1)};
    }

    public static char[] gcd(char[] num1, char[] num2) {
        num1 = removeLeadingZeros(num1);
        num2 = removeLeadingZeros(num2);

        if (num2[0] == '0') {
            return num1;
        }

        return gcd(num2, mod(num1, num2));
    }

    public static int compare(char[] num1, char[] num2) {
        num1 = removeLeadingZeros(num1);
        num2 = removeLeadingZeros(num2);

        if (num1.length > num2.length)
            return 1;
        else if (num1.length < num2.length)
            return -1;

        int cmp = 0;
        for (int i = num1.length - 1; i >= 0; i--) {
            if (num1[i] < num2[i])
                cmp = -1;
            else if (num1[i] > num2[i])
                cmp = 1;
        }

        return cmp;
    }

    private static String removeLeadingZeros(int[] input) {
        StringBuilder sb = new StringBuilder();
        for (int item : input) {
            if (!(sb.length() == 0 && item == 0)) {
                sb.append(item);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }

    private static char[] removeLeadingZeros(char[] input) {
        if (input[0] != '0') {
            return input;
        }

        StringBuilder sb = new StringBuilder();
        for (char item : input) {
            if (!(sb.length() == 0 && item == '0')) {
                sb.append(item);
            }
        }
        return sb.length() == 0 ? new char[]{'0'} : sb.toString().toCharArray();
    }

    private static int toInt(char character) {
        return character - '0';
    }

    private static char toChar(int digit) {
        return (char) ('0' + digit);
    }

    private BigInteger N;
    private BigInteger e;
    private BigInteger d;

    public RSA_Algorithm() {
        this(1024);
    }

    public RSA_Algorithm(int bitLength) {
        Random r = new Random();

        BigInteger p = BigInteger.probablePrime(bitLength, r);
        BigInteger q = BigInteger.probablePrime(bitLength, r);

        System.out.println(p);
        System.out.println(q);

        char[] product = multiply(p.toString().toCharArray(), q.toString().toCharArray());
        N = new BigInteger(new String(product));

        char[] pMinus1 = subtract(p.toString().toCharArray(), "1".toCharArray());
        char[] qMinus1 = subtract(q.toString().toCharArray(), "1".toCharArray());

        BigInteger phiN = new BigInteger(new String(multiply(pMinus1, qMinus1)));

        e = BigInteger.probablePrime(bitLength / 2, r);
        while (phiN.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phiN) < 0) {
            e = new BigInteger(add(e.toString().toCharArray(), "1".toCharArray()));
        }

        d = e.modInverse(phiN);
    }

    public byte[] encrypt(String plainText) {
        System.out.println(new BigInteger(plainText.getBytes()).modPow(e, N));
        return new BigInteger(plainText.getBytes()).modPow(e, N).toByteArray();
    }

    public String decrypt(byte[] cipherText) {
        return new String(new BigInteger(cipherText).modPow(d, N).toByteArray());
    }

    public static void main(String[] args) {
        RSA_Algorithm rsa = new RSA_Algorithm();

        String plainText = "Hello! I am Naresh";

        System.out.println("Plain text: " + plainText);

        System.out.println("Encrypting...");
        byte[] cipherText = rsa.encrypt(plainText);

        System.out.println("Cipher text: " + decode(cipherText));

        // decrypt
        System.out.println("Decrypting...");
        String decryptedPlainText = rsa.decrypt(cipherText);

        System.out.println("Decrypted plain text: " + decryptedPlainText);
    }

    private static String decode(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(b);
        }
        return sb.toString();
    }
}