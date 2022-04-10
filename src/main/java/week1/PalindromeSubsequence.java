package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/1 - 04 - 01 - 18:40
 * @Description: week1
 * @version: 1.0
 */

/**
 * 给定一个字符串str，返回这个字符串的最长回文子序列长度比如 ：
 * str = “a12b3c43def2ghi1kpm”最长回文子序列是“1234321”或者“123c321”，返回长度7
 */
public class PalindromeSubsequence {
    /**
     * 范围上的尝试模型
     */
    public static int palindromeSubsequence(String s) {
        if (s == null || s.length() == 0)
            return 0;
        return process(s.toCharArray(), 0, s.length() - 1);
    }

    // 返回字符串str start...end 范围最长回文子序列的长度
    public static int process(char[] str, int start, int end) {
        if (start == end) {
            return 1;
        }
        if (start + 1 == end)
            return str[start] == str[end] ? 2 : 1;
        // 决策1：考虑start位置的字符，不考虑end位置的字符
        int p1 = process(str, start, end - 1);
        // 决策2：不考虑start位置的字符，考虑end位置的字符
        int p2 = process(str, start + 1, end);
        // 决策3：start位置和end位置的字符都考虑
        int p3 = str[start] == str[end] ? 2 + process(str, start + 1, end - 1) : 0;
        // 决策4：start位置和end位置的字符都不考虑
        int p4 = process(str, start + 1, end - 1);
        return Math.max(p1, Math.max(p2, Math.max(p3, p4)));
    }

    public static int dp(String s) {
        if (s == null || s.length() == 0)
            return 0;
        char[] str = s.toCharArray();
        int N = s.length();
        int[][] dp = new int[N][N];
        dp[N - 1][N - 1] = 1;
        for (int i = 0; i < N - 1; i++) {
            dp[i][i] = 1;
            dp[i][i + 1] = str[i] == str[i + 1] ? 2 : 1;
        }

        for (int start = N - 3; start >= 0; start--) {
            for (int end = start + 2; end < N; end++) {
//                int p1 = dp[start][end - 1];
//                int p2 = dp[start + 1][end];
//                int p3 = str[start] == str[end] ? 2 + dp[start + 1][end - 1] : 0;
//                int p4 = dp[start + 1][end - 1];
//                dp[start][end] = Math.max(p1, Math.max(p2, Math.max(p3, p4)));
                dp[start][end] = Math.max(dp[start][end - 1], dp[start + 1][end]);
                if (str[start] == str[end])
                    dp[start][end] = Math.max(dp[start][end], dp[start + 1][end - 1] + 2);
            }
        }

        return dp[0][N - 1];
    }

    public static String generateRandomString(int maxLen) {
        char[] str = new char[(int) (Math.random() * maxLen)];
        for (int i = 0; i < str.length; i++) {
            double p = Math.random();
            if (p < 0.33)
                str[i] = (char) ((int) (Math.random() * 10) + '0');
            else if (p < 0.66)
                str[i] = (char) ((int) (Math.random() * 26) + 'a');
            else
                str[i] = (char) ((int) (Math.random() * 26) + 'A');
        }
        return String.valueOf(str);
    }

    public static void main(String[] args) {
        int testTimes = 500000;
        int maxLen = 15;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            String s = generateRandomString(maxLen);
            int ans1 = palindromeSubsequence(s);
            int ans2 =dp(s);
            if (ans1 != ans2) {
                System.out.println("s: " + s);
                System.out.println("ans1: " + ans1 + "  ans2:" + ans2);
                break;
            }
        }
        System.out.println("测试结束");
        String s = "a12b3c43def2ghi1kpm";
        System.out.println(palindromeSubsequence(s));
        System.out.println(dp(s));
    }
}
