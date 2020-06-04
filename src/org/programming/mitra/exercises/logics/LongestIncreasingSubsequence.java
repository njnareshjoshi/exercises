package org.programming.mitra.exercises.logics;

public class LongestIncreasingSubsequence {
    public static void main(String[] args) {
        int[] arr = new int[]{7, 4, 1, 8, 2, 9, 3, 6, 0, 10};
        findLongestIncreasingSubsequence(arr);
    }

    private static void findLongestIncreasingSubsequence(int[] arr) {
        int longestIncrSubSeqLength = 0;
        for (int i = 0; i < arr.length; i++) {
            int incrSubSeqLength = 0;
            int lastBigDigit = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] > lastBigDigit) {
                    lastBigDigit = arr[j];
                    incrSubSeqLength++;
                }
            }

            longestIncrSubSeqLength = Math.max(longestIncrSubSeqLength, incrSubSeqLength);
        }

        System.out.println(longestIncrSubSeqLength);
    }

}
