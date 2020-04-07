package org.programming.mitra.exercises.practice.logics;

// SubStrings or SubArrays of 1234 are 1,12,123,1234,2,23,234,3,34,4
// An string of size n can have n*(n+1)/2 substrings
public class FindAllSubStrings {
    public static void main(String[] args) {
        String input = "12345";

        System.out.print("\nAll substrings of given string are:\n");
        findAllSubArrays(input.toCharArray());

        System.out.print("\nAll substrings of given string are:\n");
        findAllSubArraysRecursively(input.toCharArray(), 0, 0);
    }

    // Complexity - O(n^3)
    private static void findAllSubArrays(char[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                for (int k = i; k <= j; k++) {
                    System.out.print(arr[k]);
                }
                System.out.print(" ");
            }
        }
    }

    // Complexity - O(n^3)
    private static void findAllSubArraysRecursively(char[] arr, int start, int end) {
        if (start == arr.length) {
            return;
        } else if (end == arr.length) {
            findAllSubArraysRecursively(arr, start + 1, start + 1);
        } else {
            for (int i = start; i <= end; i++) {
                System.out.print(arr[i]);
            }
            System.out.print(" ");

            findAllSubArraysRecursively(arr, start, end + 1);
        }
    }
}
