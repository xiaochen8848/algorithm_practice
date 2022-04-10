package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/3/31 - 03 - 31 - 12:41
 * @Description: week1
 * @version: 1.0
 */

/**
 * 在一个数组中，任何一个前面的数a，和任何一个后面的数b，
 * 如果(a,b)是降序的，就称为逆序对，返回数组中所有的逆序对
 */
public class ReversePair {
    public static int reversePairNumber(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    /**
     * 在归并排序的基础上，问题转化为对于当前左半部分的数，右边有几个比它小的
     */

    // 在数组l..r范围上返回逆序对的个数
    public static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }

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
        int ans = 0;
        while (p1 <= mid && p2 <= r) {
            ans += arr[p1] > arr[p2] ? (r - p2 + 1) : 0;
            help[index++] = arr[p1] > arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= mid) {
            help[index++] = arr[p1++];
        }

        while (p2 <= r) {
            help[index++] = arr[p2++];
        }

        if (help.length >= 0) System.arraycopy(help, 0, arr, l, help.length);
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = {6, 2, 4, 3, 7, 9, 4, 10, 1, 0, 3};
        System.out.println(reversePairNumber(arr));
    }
}
