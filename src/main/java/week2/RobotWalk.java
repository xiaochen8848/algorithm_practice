package week2;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/8 - 04 - 08 - 21:24
 * @Description: week2
 * @version: 1.0
 */

/**
 * 假设有排成一行的N个位置，记为1~N，N 一定大于或等于 2
 * 开始时机器人在其中的M位置上(M 一定是 1~N 中的一个)，如果机器人来到1位置，那么下一步只能往右来到2位置；
 * 如果机器人来到N位置，那么下一步只能往左来到 N-1 位置；如果机器人来到中间位置，
 * 那么下一步可以往左走或者往右走；(所有可能决策 条件)
 * 问：规定机器人必须走 K 步，最终能来到P位置(成功决策条件)(P也是1~N中的一个)的方法有多少种
 * 给定四个参数 N、M、K、P，（返回所有决策个数）。
 */
public class RobotWalk {

    public static int ways(int N, int M, int K, int P) {
        if (M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        return process(N, M, K, P);


    }

    public static int process(int n, int cur, int rest, int p) {
        if (rest == 0) {
            return cur == p ? 1 : 0;
        }
        if (cur == 1) {
            return process(n, cur + 1, rest - 1, p);
        }
        if (cur == n) {
            return process(n, cur - 1, rest - 1, p);
        }
        // 中间位置
        return process(n, cur + 1, rest - 1, p)
                + process(n, cur - 1, rest - 1, p);
    }

    // 添加缓存
    public static int ways1(int N, int M, int K, int P) {
        if (M < 1 || M > N || P < 1 || P > N) {
            return 0;
        }
        int[][] dp = new int[N + 1][K + 1];
        // 初始值都是-1 用来标记之前没有处理过
        for (int i = 0; i <= N; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = -1;
            }
        }
        return process(N, M, K, P, dp);
    }
    public static int process(int n, int cur, int rest, int p, int[][] dp) {
        if (dp[cur][rest] != -1) {
            return dp[cur][rest];
        }
        int ans = 0;
        if (rest == 0) {
            ans = cur == p ? 1 : 0;
        } else if (cur == 1) {
            ans = process(n, cur + 1, rest - 1, p);
        } else if (cur == n) {
            ans = process(n, cur - 1, rest - 1, p);
        } else {
           ans = process(n, cur + 1, rest - 1, p)
                   + process(n, cur - 1, rest - 1, p);
        }
        dp[cur][rest] = ans;
        return ans;
    }

    public static void main(String[] args) {
        System.out.println(ways1(5, 2, 6, 4));
        System.out.println(ways(5, 2, 6, 4));
    }
}
