package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/1 - 04 - 01 - 15:44
 * @Description: week1
 * @version: 1.0
 */

import javax.sound.midi.Soundbank;

/**
 * 给定两个字符串str1和str2，返回这两个字符串的最长公共子序列长度比如 ：
 * str1 = “a12b3c456d”,str2 = “1ef23ghi4j56k”
 * 最长公共子序列是“123456”，所以返回长度6。
 *
 * 注意：公共子序列不一定连续
 */
public class LongestCommonSubsequence {

    /**
     *样本对应模型 一般讨论结尾如何如何
     */
    public static int longestCommonSubsequence(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0) {
            return 0;
        }
        return process(s1.toCharArray(), s1.length() - 1, s2.toCharArray(), s2.length() - 1);
    }
    // 返回字符串str1 在范围0...i和字符串str2 在范围0...j最长公共子序列
    public static int process(char[] str1, int i, char[] str2, int j) {
       // 什么情况下能直接返回结果？
       if (i == 0 && j == 0) { // 当都只有一个字符时
           return str1[i] == str2[j] ? 1 : 0;
       } else if (i == 0) { // 只有str1只有一个字符
           return str1[i] == str2[j] ? 1 : process(str1, i, str2,j - 1);
       } else if (j == 0){ // 只有str2只有一个字符
           return str1[i] == str2[j] ? 1 : process(str1, i - 1, str2, j);
       } else { // str1和str2都不只一个字符
           // 决策1：字符串str1 i位置的字符考虑 字符串str2 j位置的字符不考虑
           int p1 = process(str1, i, str2, j - 1);
           // 决策2：字符串str1 i位置的字符不考虑 字符串str2 j位置的字符考虑
           int p2 = process(str1, i - 1, str2, j);
           // 决策3：字符串str1 i位置的字符考虑 字符串str2 j位置的字符考虑
           int p3 = str1[i] == str2[j] ? 1 + process(str1, i - 1, str2, j - 1) : 0;
           return Math.max(p1, Math.max(p2, p3));
       }
    }

    public static int dp(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0) {
            return 0;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int col = s1.length();
        int row = s2.length();
        int[][] dp = new int[col][row];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int j = 1; j < row; j++) {
            dp[0][j] = str1[0] == str2[j] ? 1 : dp[0][j - 1];
        }
        for (int i = 1; i < col; i++) {
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
        }
        for (int i = 1; i < col; i++) {
            for (int j = 1; j < row; j++) {
                int p1 = dp[i][j - 1];
                int p2 = dp[i - 1][j];
                int p3 = str1[i] == str2[j] ? 1 + dp[i - 1][j - 1] : 0;
                dp[i][j] = Math.max(p1, Math.max(p2, p3));
            }
        }
        return dp[col - 1][row - 1];
    }

    public static String generateRandomString(int maxLen) {
        char[] str = new char[(int)(Math.random() * maxLen)];
        for (int i = 0; i < str.length; i++) {
            double p = Math.random();
            if (p < 0.4) {
                str[i] = (char)((int)(Math.random() * 10) + '0');
            } else if (p < 0.75){
                str[i] = (char)((int)(Math.random() * 26) + 'a');
            } else {
                str[i] = (char)((int)(Math.random() * 26) + 'A');
            }
        }
        return String.valueOf(str);
    }
    public static void main(String[] args) {
        /**
         * str1 = “a12b3c456d”,str2 = “1ef23ghi4j56k”
         */
        int testTime = 5000;
        int maxLen = 15;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            String s1 = generateRandomString(maxLen);
            String s2 = generateRandomString(maxLen);
            int ans1 = longestCommonSubsequence(s1, s2);
            int ans2 = dp(s1, s2);
            if (ans1 != ans2) {
                System.out.println("s1：" + s1);
                System.out.println("s2：" + s2);
                System.out.println("ans1：" + ans1);
                System.out.println("ans2：" + ans2);
                break;
            }
        }
        System.out.println("测试结束");
        String s1 = "a12b3c456d", s2 = "1ef23ghi4j56k";
        System.out.println(longestCommonSubsequence(s1, s2));
        System.out.println(dp(s1, s2));
    }
}
