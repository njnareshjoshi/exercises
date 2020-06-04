package org.programming.mitra.exercises.logics;

import java.util.Arrays;
import java.util.HashMap;

public class TwoElementSumInArray {

    public static void main(String[] args) {
        int[] arr = new int[]{2, 7, 11, 15};
        System.out.println(Arrays.toString(twoSumTwoPass(arr, 9)));
        System.out.println(Arrays.toString(twoSumOnePass(arr, 9)));

        arr = new int[]{3, 4, 2};
        System.out.println(Arrays.toString(twoSumTwoPass(arr, 6)));
        System.out.println(Arrays.toString(twoSumOnePass(arr, 6)));

        arr = new int[]{3, 3};
        System.out.println(Arrays.toString(twoSumTwoPass(arr, 6)));
        System.out.println(Arrays.toString(twoSumOnePass(arr, 6)));
    }

    public static int[] twoSumTwoPass(int[] arr, int target) {
        int[] result = new int[2];

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], i);
            arr[i] = target - arr[i];
        }

        for (int i = 0; i < arr.length; i++) {
            Integer match = map.get(arr[i]);
            if (match != null && i != match) {
                result[0] = i;
                result[1] = match;

                return result;
            }
        }
        return null;
    }

    public static int[] twoSumOnePass(int[] arr, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            int diff = target - arr[i];
            Integer match = map.get(diff);
            if (match != null) {
                return new int[]{match, i};
            }
            map.put(arr[i], i);
        }
        return null;
    }

}
