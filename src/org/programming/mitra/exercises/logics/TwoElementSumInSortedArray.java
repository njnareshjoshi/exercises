package org.programming.mitra.exercises.logics;

import java.util.Arrays;
import java.util.HashMap;

public class TwoElementSumInSortedArray {

    public static void main(String[] args) {
        int[] arr = new int[]{2, 7, 11, 15};
        System.out.println(Arrays.toString(twoSumInOnePassUsingMap(arr, 9)));
        System.out.println(Arrays.toString(twoSumInOnePassUsingTwoPointers(arr, 9)));

        arr = new int[]{2, 3, 4};
        System.out.println(Arrays.toString(twoSumInOnePassUsingMap(arr, 6)));
        System.out.println(Arrays.toString(twoSumInOnePassUsingTwoPointers(arr, 6)));

        arr = new int[]{3, 3};
        System.out.println(Arrays.toString(twoSumInOnePassUsingMap(arr, 6)));
        System.out.println(Arrays.toString(twoSumInOnePassUsingTwoPointers(arr, 6)));
    }

    public static int[] twoSumInOnePassUsingMap(int[] arr, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length && arr[i] < target; i++) {
            int diff = target - arr[i];
            Integer match = map.get(diff);
            if (match != null) {
                return new int[]{match, i};
            }
            map.put(arr[i], i);
        }
        return null;
    }

    public static int[] twoSumInOnePassUsingTwoPointers(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int sum = arr[left] + arr[right];
            if (sum == target) {
                return new int[]{left, right};
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return null;
    }
}
