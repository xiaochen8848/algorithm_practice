package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/3/30 - 03 - 30 - 19:05
 * @Description: week1
 * @version: 1.0
 */

/**
 * 假设有排成一行的N个位置，记为1~N，N 一定大于或等于 2
 * 开始时机器人在其中的M位置上(M 一定是 1~N 中的一个)，
 * 如果机器人来到1位置，那么下一步只能往右来到2位置；
 * 如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；
 * 如果机器人来到中间位置，那么下一步可以往左走或者往右走；(所有可能决策 条件)
 * 问：规定机器人必须走 K 步，最终能来到P位置(成功决策条件)(P也是1~N中的一个)的方法有多少种
 * 给定四个参数 N、M、K、P，（返回所有决策个数）。
 */
public class RobotWalk {
    public static int ways(int N, int M, int K, int P) {
        // 无效参数
        if (M < 1 || M > N || K < 1  || P < 1 || P > N) {
            return 0;
        }

        return process(N, M, K, P);
    }

    // 在范围1~N上，求M-->P位置，必须走完K步的方法数
    public static int process(int N, int M, int K, int P) {
        // 不能在做决策时
        if (K == 0) {
            return M == P ? 1 : 0;
        }

        if (M == 1) {
            return process(N, M + 1, K - 1, P);
        }

        if (M == N) {
            return process(N, M - 1, K - 1, P);
        }

        return process(N, M - 1, K - 1, P) +
                process(N, M + 1, K - 1, P);
    }

    /**
     * 代入具体的实例，发现有重复调用，所以可以优化成严格表结构
     */

    public static int dp(int N, int M, int K, int P) {
        // 无效参数
        if (M < 1 || M > N || K < 1 || P < 1 || P > N) {
            return 0;
        }

        int[][] dp = new int[N + 1][K+ 1];
        dp[P][0] = 1;
        // 遍历顺序通过画图得出
//        for (int k = 1; k <= K; k++) {
//            for (int m = 1; m <= N; m++) {
//                // 这里代码直接抄递归代码
//                if (m == 1) {
//                     dp[m][k] = dp[m + 1][k - 1];
//                } else if (m == N) {
//                    dp[m][k] = dp[m - 1][k - 1];
//                } else {
//                    dp[m][k] = dp[m - 1][k - 1] + dp[m + 1][k - 1];
//                }
//            }
//        }

        // 小小的简化
        for (int k = 1; k <= K; k++) {
            dp[1][k] = dp[2][k - 1];
            for (int m = 2; m < N; m++) {
              dp[m][k] = dp[m - 1][k - 1] + dp[m + 1][k - 1];
            }
            dp[N][k] = dp[N - 1][k -1];
        }

        // 由调用递归代码得
        return dp[M][K];

    }


    public static void main(String[] args) {
        int N = 8, M = 4, K = 6, P = 4;
        System.out.println(ways(N, M, K, P));
        System.out.println(dp(N, M, K, P));
    }


}
