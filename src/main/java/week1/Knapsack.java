package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/3/30 - 03 - 30 - 21:19
 * @Description: week1
 * @version: 1.0
 */

/**
 * 给定两个长度都为N的数组weights和values，
 * weights[i]和values[i]分别代表 i号物品的重量和价值。（所有可能决策 i号物品要或者不要）
 * 给定一个正数bag，表示一个载重bag的袋子，你装的物品不能超过这个重量。(决策终止)
 * 返回你能装下最多的价值是多少? （返回所有决策最优）
 */
public class Knapsack {

    public static int maxValue(int[] weights, int[] values, int bags) {
        if (weights == null || values == null || values.length == 0 || weights.length != values.length || bags <= 0)
            return 0;
        return process(weights, values, 0, bags);
    }

    //返回index...length上的最大价值且重量不超过bags
    public static int process(int[] weights, int[] values, int index, int bags) {
        if (bags < 0) {
            return -1; // 标识上一个index不能选
        }
        if (index == weights.length) {
            return 0;
        }

        /*两种决策 当前index位置的物品要或者不要*/
        // 不要 当前节点获得的最大价值等于index+1...length上的最大价值
        int p1 = process(weights, values, index + 1, bags);
        // 要 当前节点获得的最大价值等于values[index]的价值 + index+1...length上的最大价值
        int p2 = process(weights, values, index + 1, bags - weights[index]);
        p2 = p2 == -1 ? 0 : p2 + values[index];
        return Math.max(p1, p2);
    }

    /*代入具体的例子发现有重复调用，所以可以优化*/

    public static int dp(int[] weights, int[] values, int bags) {
        if (weights == null || values == null || values.length == 0 || weights.length != values.length || bags <= 0)
            return 0;
        int N = weights.length;
        int[][] dp = new int[N + 1][bags + 1];

        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= bags; j++) {
                int p1 = dp[i + 1][j];
                int p2 = j - weights[i] < 0 ? 0 : dp[i + 1][j - weights[i]] + values[i];
                dp[i][j] = Math.max(p1, p2);
            }

        }
        return dp[0][bags];
    }



    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
        int[] values = { 5, 6, 3, 19, 12, 4, 2 };
        int bag = 15;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(dp(weights, values, bag));
    }
}
