package org.programming.mitra.exercises.logics;

import java.util.Arrays;

public class KthSmallestNumber {
    public static void main(String[] args) {
        int[] arr = {2, 9, 4, 6, 5, 3, 0, 1, 8, 10};
        System.out.println(findKthSmallestNumberUsingDnC(arr, 0, arr.length - 1, 9));
    }

    // Finding kth smallest item using divide and conquer
    private static int findKthSmallestNumberUsingDnC(int[] arr, int start, int end, int k) {
        int i = start;
        int j = end;
        int p = (start + end) / 2;

        System.out.println();
        System.out.println("For pivot " + arr[p]);

        while (j >= i) {
            if (p > i && arr[p] < arr[i]) {
                swap(arr, i, p);
                p = i;
            }
            i++;

            if (p < j && arr[p] > arr[j]) {
                swap(arr, p, j);
                p = j;
            }
            j--;
            System.out.println(Arrays.toString(arr));
        }

        System.out.println(Arrays.toString(arr));

        int lengthOfArrBeforePivot = p - start;
        if (lengthOfArrBeforePivot == k - 1) {
            return arr[p];
        } else if (lengthOfArrBeforePivot > k - 1) {
            return findKthSmallestNumberUsingDnC(arr, start, lengthOfArrBeforePivot, k);
        } else {
            return findKthSmallestNumberUsingDnC(arr, p + 1, end, k - lengthOfArrBeforePivot - 1);
        }
    }

    static void swap(int[] arr, int i, int j) {
        System.out.println("Swapping " + arr[i] + " with " + arr[j]);
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
