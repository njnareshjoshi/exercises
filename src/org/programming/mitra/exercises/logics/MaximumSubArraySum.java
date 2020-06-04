package org.programming.mitra.exercises.logics;

public class MaximumSubArraySum {
    public static void main(String[] args) {
        int[] arr1 = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(findMaxSubArraySumUsing3Loops(arr1));
        System.out.println(findMaxSubArraySumUsing2Loops(arr1));
        System.out.println(findMaxSubArraySumUsingDnC(arr1, 0, arr1.length - 1));
        System.out.println(findMaxSubArraySumUsingDnC2(arr1, 0, arr1.length - 1)[3]);
        System.out.println(findMaxSubArraySumUsing1Loop(arr1));


        int[] arr2 = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4, 4};
        System.out.println(findMaxSubArraySumUsing3Loops(arr2));
        System.out.println(findMaxSubArraySumUsing2Loops(arr2));
        System.out.println(findMaxSubArraySumUsingDnC(arr2, 0, arr2.length - 1));
        System.out.println(findMaxSubArraySumUsingDnC2(arr2, 0, arr2.length - 1)[3]);
        System.out.println(findMaxSubArraySumUsing1Loop(arr2));
    }

    // O(n^3)
    public static int findMaxSubArraySumUsing3Loops(int[] arr) {
        int maxSum = arr[0];
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += arr[k];
                }

                maxSum = Math.max(maxSum, sum);
            }
        }
        return maxSum;
    }

    // O(n^2)
    public static int findMaxSubArraySumUsing2Loops(int[] arr) {
        int maxSum = arr[0];
        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            for (int j = i; j < arr.length; j++) {
                sum += arr[j];
                maxSum = Math.max(maxSum, sum);
            }
        }
        return maxSum;
    }

    // O(nlogn)
    public static int findMaxSubArraySumUsingDnC(int[] arr, int start, int end) {
        if (end == start)
            return arr[start];

        int mid = (start + end) / 2;

        int leftArrSum = findMaxSubArraySumUsingDnC(arr, start, mid);
        int rightArrSum = findMaxSubArraySumUsingDnC(arr, mid + 1, end);
        int crossingArrSum = findMaxCrossingSum(arr, start, mid, end);

        return Math.max(Math.max(leftArrSum, rightArrSum), crossingArrSum);
    }

    private static int findMaxCrossingSum(int[] arr, int start, int mid, int end) {

        int leftArrSum = Integer.MIN_VALUE;
        int rightArrSum = Integer.MIN_VALUE;

        int sum = 0;
        for (int i = mid; i >= start; i--) {
            sum += arr[i];
            leftArrSum = Math.max(leftArrSum, sum);
        }

        sum = 0;
        for (int i = mid + 1; i <= end; i++) {
            sum += arr[i];
            rightArrSum = Math.max(rightArrSum, sum);
        }

        return leftArrSum + rightArrSum;
    }

    // O(n)
    // Uses more for less technique, more parameters and returns for less calculation
    public static int[] findMaxSubArraySumUsingDnC2(int[] arr, int start, int end) {
        if (end == start)
            return new int[]{arr[start], arr[start], arr[start], arr[start]}; // Array represents TS, MPS, MSS, MAS

        int mid = (start + end) / 2;

        int[] leftSumsArr = findMaxSubArraySumUsingDnC2(arr, start, mid);
        int[] rightSumsArr = findMaxSubArraySumUsingDnC2(arr, mid + 1, end);

        return findMaxCrossingSum(leftSumsArr, rightSumsArr);
    }

    private static int[] findMaxCrossingSum(int[] leftSumsArr, int[] rightSumsArr) {
        return new int[]{
                leftSumsArr[0] + rightSumsArr[0], // TS1 + TS2
                Math.max(leftSumsArr[1], leftSumsArr[0] + rightSumsArr[1]), // MAX(MPS1, TS1+MPS2)
                Math.max(leftSumsArr[2] + rightSumsArr[0], rightSumsArr[2]), // MAX(MSS1+TS2, MSS2)
                Math.max(Math.max(leftSumsArr[3], rightSumsArr[3]), leftSumsArr[2] + rightSumsArr[1]) // MAX(MAS1, MAS2, MSS1+MPS2)
        };
    }

    // Kadane's Algorithm, we can create dp array and store best possible sub array sum for given index
    // arr -2, 1,           -3,          4,         -1,         2,         1,         -5,         4
    // dp  -2, (1|(1-2))=1, (-3|1-3)=-2, (4|4-2)=4, (-1|4-1)=3, (2|3+2)=5, (1|1+5)=6, (-5|6-5)=1, (4|4+1)=5
    // By looking at dp we can see max sub array sum is 6
    // O(n)
    public static int findMaxSubArraySumUsing1Loop(int[] arr) {

        int maxSum = arr[0];
        int bestSum = arr[0];
        for (int i = 1; i < arr.length; i++) {
            bestSum = Math.max(arr[i], arr[i] + bestSum);
            maxSum = Math.max(maxSum, bestSum);
        }
        return maxSum;
    }


}
