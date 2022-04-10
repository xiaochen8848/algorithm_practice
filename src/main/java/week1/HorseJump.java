package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/1 - 04 - 01 - 17:25
 * @Description: week1
 * @version: 1.0
 */

import com.sun.xml.internal.bind.v2.model.core.ID;
import sun.text.resources.cldr.ii.FormatData_ii;

import java.net.PortUnreachableException;

/**
 * 请同学们自行搜索或者想象一个象棋的棋盘，然后把整个棋盘放入第一象限，
 * 棋盘的最左下角是(0,0)位置那么整个棋盘就是横坐标上9条线、
 * 纵坐标上10条线的区域给你三个
 * 参数 x，y，k返回“马”从(0,0)位置出发，
 * 必须走k步最后落在(x,y)上的方法数有多少种?  10*9的盘
 */
public class HorseJump {
    // 我们就认为x代表棋盘中的行 y棋盘中的列
    public static int horseJump(int x, int y, int k) {
        if (x < 0 || x > 9 || y < 0 || y > 8 || k < 1) {
            return 0;
        }
        return process(x, y, 0, 0, k);
    }
    // 当前位置(a,b)--->(x,y)，且必须走完k步的方法数
    public static int process(int x, int y, int a, int b, int k) {
        if (a < 0 || a > 9 || b < 0 || b > 8) {
            return 0;
        }
        if (k == 0) {
            return a == x && b == y ? 1 : 0;
        }
        return process(x, y, a - 1, b - 2, k - 1)
                + process(x, y, a - 2, b - 1, k - 1)
                + process(x, y, a - 2, b + 1, k - 1)
                + process(x, y, a - 1, b + 2, k - 1)
                + process(x, y, a + 1, b + 2, k - 1)
                + process(x, y, a + 2, b + 1, k - 1)
                + process(x, y, a + 2, b - 1, k - 1)
                + process(x, y, a + 1, b - 2, k - 1);

    }

    public static int dp(int x, int y, int k) {
        if (x < 0 || x > 9 || y < 0 || y > 8 || k < 1)
            return 0;
        int[][][] dp = new int[10][9][k + 1];
        dp[x][y][0] = 1;

        for (int rest = 1; rest <= k; rest++) {
            for (int a = 0; a < 10; a++) {
                for (int b = 0; b < 9; b++) {
                    dp[a][b][rest] = pick(dp, a - 1, b - 2, rest - 1)
                            + pick(dp, a - 2, b - 1, rest - 1)
                            + pick(dp, a - 2, b + 1, rest - 1)
                            + pick(dp, a - 1, b + 2, rest - 1)
                            + pick(dp, a + 1, b + 2, rest - 1)
                            +  pick(dp, a + 2, b + 1, rest - 1)
                            +  pick(dp, a + 2, b - 1, rest - 1)
                            +  pick(dp, a + 1, b - 2, rest - 1);
                }
            }
        }
        return dp[0][0][k];
    }
    public static int pick(int[][][] dp, int x, int y, int k) {
        if (x < 0 || x > 9 || y < 0 || y > 8)
            return 0;
        return dp[x][y][k];
    }

    public static int[] generateRandomArgs() {
        int[] arr = new int[3];
        arr[0] = (int)(Math.random() * 10);
        arr[1] = (int)(Math.random() * 9);
        arr[2] = (int)(Math.random() * 10);
        return arr;
    }
    public static void main(String[] args) {
       int testTimes = 50000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArgs();
            int x = arr[0];
            int y = arr[1];
            int k = arr[2];
            int ans1 = horseJump(x, y, k);
            int ans2 = dp(x, y, k);
            if (ans1 != ans2) {
                System.out.println("x: " + x + " y: " + y + " k:" + k);
                System.out.println("ans1: " + ans1 + " ans2" + ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }
}
