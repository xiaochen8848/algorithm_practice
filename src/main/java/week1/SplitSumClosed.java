package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/3/31 - 03 - 31 - 14:34
 * @Description: week1
 * @version: 1.0
 */

/**
 * 给定一个正数数组arr，
 * 请把arr中所有的数分成两个集合，尽量让两个集合的累加和接近
 * 返回：
 * 最接近的情况下，较小集合的累加和
 */
public class SplitSumClosed {

    /**
     * 先求出数组累加和的一半，那么问题就转化为在数组中自由选择若干个数，使其的和接近sum/2
     * 从左到右的模型 每一个index位置，决策：要/不要
     */
    public static int right(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        return process(arr, 0, sum / 2);
    }

    public static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            return 0;
        }

        int p1 = process(arr, index + 1, rest);
        int p2 = arr[index] <= rest ? arr[index] + process(arr, index + 1, rest - arr[index]) : 0;
        return Math.max(p1, p2);
    }

    public static int dp(int[] arr) {
        int N = arr.length;
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        sum /= 2;
        int[][] dp = new int[N + 1][sum + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= sum; rest++) {
                int p1 = dp[index + 1][rest];
                int p2 = arr[index] <= rest ? arr[index] + dp[index + 1][rest - arr[index]] : 0;
                dp[index][rest] = Math.max(p1, p2);
            }
        }
        return dp[0][sum];

    }
}
