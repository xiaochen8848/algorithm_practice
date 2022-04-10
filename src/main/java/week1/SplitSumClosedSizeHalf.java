package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/3/31 - 03 - 31 - 14:55
 * @Description: week1
 * @version: 1.0
 */

/**
 * 给定一个正数数组arr，请把arr中所有的数分成两个集合
 * 如果arr长度为偶数，两个集合包含数的个数要一样多
 * 如果arr长度为奇数，两个集合包含数的个数必须只差一个
 * 请尽量让两个集合的累加和接近
 * 返回：
 * 最接近的情况下，较小集合的累加和
 */
public class SplitSumClosedSizeHalf {

    public static int right2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        if ((arr.length & 1) == 0) {
            return process(arr, 0, arr.length / 2, sum / 2);
        } else {
            return Math.max(process(arr, 0, arr.length / 2, sum / 2),
                    process(arr, 0, arr.length / 2 + 1, sum / 2));
        }
    }
    // 选的个数必须达到pick个 然后和尽量接近rest
    public static int process(int[] arr, int index, int pick, int rest) {
        if (index == arr.length) {
            return pick == 0 ? 0 : -1;  // 返回-1来标识该决策无效 因为没有达到pick个
        }
        int p1 = process(arr, index + 1, pick, rest);
        /**
         * 注意这里p2的初始值也必须是-1 因为选的个数没有达到pick个有两种情况
         * 一种是index已经到了终止位置 base case 已经处理了
         * 另外一种是选择的个数没有达到pick个，但是由于arr[index] > rest不能再加了，这种情况也不能算
         */
        int p2 = -1;
        int next = -1;
        if (arr[index] <= rest) {
            next = process(arr, index + 1, pick - 1, rest - arr[index]);
        }
        if (next != -1)
            p2 = arr[index] + next;
        return Math.max(p1, p2);
    }

    public static int dp(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int sum = 0;
        for (int i : arr) {
            sum += i;
        }
        sum /= 2;
        int N = arr.length;
        int pick = (arr.length & 1) == 0 ? arr.length / 2 : arr.length / 2 + 1;
        int[][][] dp = new int[N + 1][pick + 1][sum + 1];

        // base  case
        for (int r = 0; r <= sum; r++) {
            for (int p = 1; p <= pick; p++) {
                dp[N][p][r] = -1;
            }
        }
        

        for (int index = N - 1; index >= 0; index--) {
            for (int p = 0; p <= pick; p++) {
                for (int r = 0; r <= sum; r++) {
                    int p1 = dp[index + 1][p][r];
                    int p2 = -1;
                    int next = -1;
                    if (arr[index] <= r && p - 1 >= 0) {
                        next = dp[index + 1][p - 1][r - arr[index]];
                    }
                    if (next != -1)
                        p2 = arr[index] + next;
                    dp[index][p][r] = Math.max(p1, p2);
                }
            }
        }


        if ((arr.length & 1) == 0) {
            return dp[0][arr.length / 2][sum];
        } else {
            return Math.max(dp[0][arr.length / 2][sum],
                    dp[0][arr.length / 2 + 1][sum]);
        }

    }
    public static void main(String[] args) {
        int[] arr = {21, 1, 8, 10};
        System.out.println(dp(arr));
    }
}
