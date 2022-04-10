package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/3/31 - 03 - 31 - 12:16
 * @Description: week1
 * @version: 1.0
 */

import com.sun.scenario.effect.impl.prism.PrCropPeer;

/**
 * 在一个数组中，算出一个数左边比它小的数的总和，叫数的小和，所有数的小和累加起来，叫数组小和。求数组小和。
 * 例子： [1,3,4,2,5]
 *  1左边比1小的数：没有
 * 3左边比3小的数：1
 * 4左边比4小的数：1、3
 * 2左边比2小的数：1
 * 5左边比5小的数：1、3、4、 2
 * 所以数组的小和为1+1+3+1+1+3+4+2=16
 */
public class SmallSum {

    public static int smallSum(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }


    // 在数组范围l...r范围上返回小和
    public static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        /*利用归并排序的思想，在有序数组的基础上，先求出左半部分的小和
        * 再求出右半部分的小和，最后求出左半部分和右半部分merge过程中产生的小和
        * 小和问题转化：对于当前数，它的右边有几个数比它大，
        * */
        int mid = l  +((r - l) >> 1);
        return process(arr, l, mid)
                + process(arr, mid + 1, r)
                + merge(arr, l, mid, r);
    }

    private static int merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int index = 0;
        int p1 = l;
        int p2 = mid + 1;
        int sum = 0;
        while (p1 <= mid && p2 <= r) {
            sum += arr[p1] < arr[p2] ? (r - p2 + 1) * arr[p1] : 0;
            help[index++] = arr[p1] < arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= mid) {
            help[index++] = arr[p1++];
        }

        while (p2 <= r) {
            help[index++] = arr[p2++];
        }

        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        int[] arr = {1,3,4,2,5};
        System.out.println(smallSum(arr));
    }
}
