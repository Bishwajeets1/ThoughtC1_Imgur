package java_dummy;

public class BinarySearch {

    public static void main(String[] args) {

        int[] arr = { 2, 3, 4, 5, 6, 7, 8, 9 };
        int[] arr1 = { 90, 30, 20, 10, 9, 4, 3, 2, 1 };
        // int ans = binarySearch(arr, 10);
        // System.out.println(ans);

        orderAnosticBinarySearch(arr1, 2);

    }

    // return the index

    static int binarySearch(int[] arr, int target) {

        int start = 0;
        int end = arr.length - 1;

        while (start <= end) {
            // find the middle element

            // int mid = (start + end)/2; // this may exceed the int range

            int mid = start + (end - start) / 2; /// same formula as in the top

            if (target < arr[mid]) {
                end = mid - 1;
            } else if (target > arr[mid]) {
                start = mid + 1;
            } else {
                return mid;
            }

        }

        return -1;
    }

    static int binarySearchForUnSortedArray(int[] arr, int target) {

        int start = 0;
        int end = arr.length - 1;

        while (start <= end) {
            // find the middle element

            // int mid = (start + end)/2; // this may exceed the int range

            int mid = start + (end - start) / 2; /// same formula as in the top

            if (target > arr[mid]) {
                end = mid - 1;
            } else if (target < arr[mid]) {
                start = mid + 1;
            } else {
                return mid;
            }

        }

        return -1;
    }

    // orger agnostic binary search

    static void orderAnosticBinarySearch(int[] arr, int target) {

        // find wherather the arrray is sorted in ascending or descending

        boolean isAec;

        int start = 0;
        int end = arr.length - 1;

        if (arr[start] < arr[end]) {
            isAec = true;
        } else {
            isAec = false;
        }

        if (isAec) {
            int ans = binarySearch(arr, target);
            System.out.println(ans);
        } else {
            int ans = binarySearchForUnSortedArray(arr, target);
            System.out.println(ans);
        }
    }
}
