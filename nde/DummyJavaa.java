import java.util.Calendar;
import java.util.Date;

class DummyJavaa {

    public static void main(String[] args) {
        // int[] a = new int[] { 1, 3, 3, 3, 4, 6, 7 };

        int[] b = new int[] { 1, 0, 3, 0, 0 };

        int[] arr = moveZeroes(b);
        // for (int i = 0; i < arr.length; i++) {
        // System.out.print(arr[i] + " ");
        // }

        // for (int i = b.length - 1; i > 0; i--) {
        // System.out.println(b[i] + " ");
        // }

        Date currentTime = Calendar.getInstance().getTime();
        System.out.println(currentTime);

        // twoSum(a, 9);
        // System.out.println(remoeDuplicate(a).toString());
        // int[] b = remoeDuplicate(a);
        // for (int i = 0; i < b.lengthl; i++) {
        // System.out.print(b[i]);
        // }

        // System.out.println(removeElement(a, 3));

    }

    public static int[] twoSum(int[] nums, int target) {

        int a = 0;
        int b = 0;

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    a = i;
                    b = j;
                    System.out.println(i + "  " + j);
                }
            }
        }
        return new int[] { a, b };

    }

    public static int[] remoeDuplicate(int[] a) {
        int j = 0;
        int[] temp = new int[] { j };

        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] != a[i + 1]) {
                temp[j] = a[i];
                j++;
            }
        }
        temp[j] = a[a.length - 1];
        return temp;

    }

    public static int removeElement(int[] arr, int v) {

        int j = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != v) {
                arr[j] = arr[i];
                j++;
            }
        }

        return j;
    }

    public static int[] moveZeroes(int[] nums) {

        int j = 0;
        int[] temp = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                temp[j] = nums[i];
                j++;
            }
        }

        while (j < nums.length) {
            temp[j++] = 0;

        }

        return temp;
    }

}
