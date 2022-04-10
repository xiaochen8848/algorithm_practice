package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/1 - 04 - 01 - 21:06
 * @Description: week1
 * @version: 1.0
 */

/**
 * 给定一个数组arr，两个整数lower和upper，
 * 返回arr中有多少个子数组的累加和在[lower,upper]范围上、
 */
public class CountOfRangeSum {
    /**
     * 分析：
     * arr--->sum
     * 对于右半部分的数num，左半部分有几个数是在[num - upper, num - lower]上
     */

    public static int countRangeSum(int[] arr, int lower, int upper) {
        if (arr == null || arr.length == 0)
            return 0;
        int[] sum = new int[arr.length];
        sum[0] = arr[0];
        for (int i = 1; i < sum.length; i++) {
            sum[i] = sum[i - 1] + arr[i];
        }
        return process(arr, 0, arr.length - 1, lower, upper);
    }

    private static int process(int[] sum, int l, int r, int lower, int upper) {
        if (l == r)
            return sum[l] >= lower && sum[l] <= upper ? 1 : 0;
        int mid = l + ((r - l) >> 1);
        return process(sum, l, mid, lower, upper)
                + process(sum, mid + 1, r, lower, upper)
                + merge(sum, l, mid, r, lower, upper);
    }

    private static int merge(int[] sum, int l, int mid, int r, int lower, int upper) {
        int[] help = new int[r - l + 1];
        int index = 0;
        int p1 = l;
        int p2 = mid + 1;
        // [windowL, windowR)
        int windowL = l;
        int windowR = l;
        int ans = 0;
        for (int i = mid + 1; i <= r; i++) {
            int min = sum[i] - upper;
            int max = sum[i] - lower;
            while (windowL < mid && sum[windowL] < min)
                windowL++;
            while (windowR <= r && sum[windowR] <= max)
                windowR++;
            ans += windowR - windowL;
        }
        while (p1 <= mid && p2 <= r)
            help[index++] = sum[p1] <= sum[p2] ? sum[p1++] : sum[p2++];
        while (p1 <= mid)
            help[index++] = sum[p1++];
        while (p2 <= r)
            help[index++] = sum[p2++];
        if (help.length >= 0)
            System.arraycopy(help, 0, sum, l, help.length);
        return ans;
    }


}
