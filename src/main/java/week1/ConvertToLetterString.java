package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/1 - 04 - 01 - 13:37
 * @Description: week1
 * @version: 1.0
 */


/**
 * 规定1和A对应、2和B对应、3和C对应...26和Z对应（所有可能决策条件）
 * 那么一个数字字符串比如"111”就可以转化为:"AAA"、"KA"和"AK"（所有可能的决策 ）
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果(返回所有决策个数)
 */
public class ConvertToLetterString {
    /**
     * 分析：模型 ：从左往右
     * 对于当前数字字符，它有两种决策：  直接转  /  和前面一个数字字符一起转
     */

    public static int number(String s) {
        if (s == null || s.length() < 1) {
            return 0;
        }
        return process(s.toCharArray(), 0);
    }

    // 返回字符串 index..的转化个数
    public static int process(char[] str, int index) {
        if (index == str.length) {
            return 1;
        }

        if (str[index] == '0')
            return 0;
        // 单个字符直接转
        int ways = process(str, index + 1);

        if (index + 1 < str.length && (str[index] - '0' * 10 + str[index + 1] - '0' < 27)) {
            ways += process(str, index + 2);
        }
        return ways;
    }

    public static int dp(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = s.length();
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int index = N - 1; index >= 0; index--) {
            if (str[index] == '0') {
                dp[index] = 0;
            } else {
                // 单个字符直接转
                int ways = dp[index + 1];
                if (index + 1 < str.length && (str[index] - '0' * 10 + str[index + 1] - '0' < 27)) {
                    ways += dp[index + 2];
                }
                dp[index] = ways;
            }
        }
        return dp[0];
    }

    // for test
    public static String randomString(int len) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char)(int)(Math.random() * 10 + '0');
        }
        return String.valueOf(str);
    }
    public static void main(String[] args) {
        int maxLen = 20;
        int testTime = 500000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int)(Math.random() * maxLen);
            String s = randomString(len);
            int ans1 = number(s);
            int ans2 = dp(s);
            if (ans1 != ans2) {
                System.out.println(s);
                System.out.println(ans1 + " " + ans2);
                break;
            }
        }
        System.out.println("测试结束");

    }
}
