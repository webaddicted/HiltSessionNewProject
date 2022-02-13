package com.webaddicted.hiltsession.view;

import android.util.Log;

/**
 * Author : Deepak Sharma(webaddicted)
 * Email : techtamper@gmail.com
 * Profile : https://github.com/webaddicted
 */
public class Test {
    static int array[] = {5, 7, 6, 4, 1, 2, 3};

    public static void mergeSortData() {
        int a[][] = {{1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}};
        spiralMatrixPrint(a);
        divide(0, array.length - 1);
    }

    // Recursive algorithm for merge sort
    public static void divide(int start, int end) {
        int mid = (start + end) / 2;
        if (start < end) {
            // divide left half
            divide(start, mid);
            // divide right half
            divide(mid + 1, end);
            // Merge left and right half
            merge(start, mid, end);
        }
    }

    private static void merge(int start, int mid, int end) {
        // Initializing temp array and index
        int[] tempArray = new int[array.length];
        int tempArrayIndex = start;
        int startIndex = start;
        int midIndex = mid + 1;

        // It will iterate until smaller list reaches to the end
        while (startIndex <= mid && midIndex <= end) {
            if (array[startIndex] < array[midIndex]) {
                tempArray[tempArrayIndex++] = array[startIndex++];
            } else {
                tempArray[tempArrayIndex++] = array[midIndex++];
            }
        }
        // Copy remaining elements
        while (startIndex <= mid) {
            tempArray[tempArrayIndex++] = array[startIndex++];
        }
        while (midIndex <= end) {
            tempArray[tempArrayIndex++] = array[midIndex++];
        }
        // Temp array is sorted array & array is origional array
        // now copy temp array into array
        for (int i = start; i <= end; i++) {
            array[i] = tempArray[i];
        }

        Log.d("TAG", "After merging:   ");
        printArray(tempArray, start, end);
        System.out.println();
    }

    public static void printArray(int arr[], int start, int end) {
        for (int i = 0; i < arr.length; i++) {
            Log.d("TAG", "Sorted : " + arr[i] + " ");
        }
        System.out.println();
    }


    public static void spiralMatrixPrint(int[][] arr) {

        int rows = arr.length;
        int cols = arr[0].length;
        // Defining the boundaries of the matrix.
        int top = 0, bottom = rows - 1, left = 0, right = cols - 1;

        // Defining the direction in which the array is to be traversed.
        int dir = 1;

        while (top <= bottom && left <= right) {

            if (dir == 1) {    // moving left->right
                for (int i = left; i <= right; ++i) {
                    System.out.print(arr[top][i] + " ");
                }
                // Since we have traversed the whole first
                // row, move down to the next row.
                ++top;
                dir = 2;
            } else if (dir == 2) {     // moving top->bottom
                for (int i = top; i <= bottom; ++i) {
                    System.out.print(arr[i][right] + " ");
                }
                // Since we have traversed the whole last
                // column, move left to the previous column.
                --right;
                dir = 3;
            } else if (dir == 3) {     // moving right->left
                for (int i = right; i >= left; --i) {
                    System.out.print(arr[bottom][i] + " ");
                }
                // Since we have traversed the whole last
                // row, move up to the previous row.
                --bottom;
                dir = 4;
            } else if (dir == 4) {     // moving bottom->up
                for (int i = bottom; i >= top; --i) {
                    System.out.print(arr[i][left] + " ");
                }
                // Since we have traversed the whole first
                // col, move right to the next column.
                ++left;
                dir = 1;
            }
        }
    }

}
