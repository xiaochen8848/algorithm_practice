package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/1 - 04 - 01 - 20:26
 * @Description: week1
 * @version: 1.0
 */

import java.util.Arrays;

/**
 * 比如：[3,1,7,0,2]
 * 3的后面有：1，0
 * 1的后面有：0
 * 7的后面有：0，2
 * 0的后面没有
 * 2的后面没有
 * 所以总共有5个
 */
public class BiggerThanRightTwice2 {
    /**
     * 分析：问题转化为：在归并排序思想的基础上，对于左半部分的数，右部分有几个数*2之后还小于当前左半部分的数
     * 返回总的个数
     */

    public static int bigger(int[] arr) {
        if (arr == null || arr.length < 2)
            return 0;
        return process(arr, 0, arr.length - 1);
    }

    // 返回数组arr l...r范围上符合题意的总个数
    public static int process(int[] arr, int l, int r) {
        if (l == r)
            return 0;

        int mid = l + ((r - l) >> 1);
        return process(arr, l, mid)
                + process(arr, mid + 1, r)
                + merge(arr, l, mid, r);
    }

    private static int merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int index = 0;
        int p1 = l;
        int p2 = mid + 1;

        // 先完成我们的需求 假定数组是按照从小到大的顺序
        int windowR = mid + 1;
        int ans = 0;
        for (int i = l; i <= mid; i++) {
            while (windowR <= r && arr[i] > arr[windowR] * 2)
                windowR++;
            ans += windowR - mid - 1;
        }

        // 完成排序
        while (p1 <= mid && p2 <= r)
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];

        while (p1 <= mid)
            help[index++] = arr[p1++];

        while (p2 <= r)
            help[index++] = arr[p2++];

        if (help.length >= 0) System.arraycopy(help, 0, arr, l, help.length);
        return ans;

    }

    // for test
    public static int test(int[] arr) {
        if (arr == null || arr.length < 2)
            return 0;
        int ans = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j] * 2)
                    ans++;
            }
        }
        return ans;
    }

    public static int[] generateRandomArray(int maxSie, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSie)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 7, 0, 2};
        System.out.println(bigger(arr));

        int testTimes = 5000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("测试开始");

        for (int i = 0; i < testTimes; i++) {
            int[] arr1 = generateRandomArray(maxSize, maxValue);
            int[] arr2 = new int[arr1.length];
            System.arraycopy(arr1, 0, arr2, 0, arr2.length);
            int ans1 = bigger(arr1);
            int ans2 = test(arr2);
            if (ans1 != ans2) {
                System.out.println(Arrays.toString(arr2));
                System.out.println("ans1 :" + ans1);
                System.out.println("ans2:" + ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
