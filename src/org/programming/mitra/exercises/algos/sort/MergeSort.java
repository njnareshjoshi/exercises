package org.programming.mitra.exercises.algos.sort;

import java.util.Arrays;

public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {6, 5, 1, 7, 8, 2, 4, 3, 2, 9, 1};
        System.out.println(Arrays.toString(arr));

        int[] temp = new int[arr.length];
        mergeSort(arr, temp, 0, arr.length - 1);

        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(temp));
    }

    public static void mergeSort(int[] arr, int[] temp, int start, int end) {
        if (end <= start) {
            return;
        }

        int mid = (start + end) / 2;

        mergeSort(arr, temp, start, mid);
        mergeSort(arr, temp, mid + 1, end);

        mergeHalves(arr, temp, start, mid, end);
    }

    public static void mergeHalves(int[] arr, int[] temp, int start, int mid, int end) {
        int i = start;
        int j = mid + 1;
        int k = start;

        // Comparing items from both halves and copying in order
        while (i <= mid && j <= end) {
            if (arr[i] < arr[j]) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        // Copying remaining first halve
        while (i <= mid) {
            temp[k++] = arr[i++];
        }

        // Copying remaining second halve
        while (j <= end) {
            temp[k++] = arr[j++];
        }

        // Copying temp array back to actual array so it will be reflected in other recursive calls
        for (i = 0; i <= end; i++) {
            arr[i] = temp[i];
        }
    }
}
