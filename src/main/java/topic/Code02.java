package topic;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/6 - 04 - 06 - 13:25
 * @Description: topic
 * @version: 1.0
 */

import java.util.Arrays;

/**
 * 一个建筑商想建造一排N个可以有K种不同颜色的房子。他的目标是使成本最小化，同时确保没有两个相邻的房子是同一颜色的。
 * 给出一个N乘K的矩阵，其中第n行和第k列代表用第k种颜色建造第n个房子的成本，请返回能实现这一目标的最小成本。
 */
public class Code02 {
    /**
     * 确保没有两个相邻的房子是同一颜色的 所以pre为index-1位置的房子选择的颜色下标
     * 每个index位置的房子可以选择的颜色是0...k-1，除了pre
     *
     */
    // 返回index...N房子的最小成本
    public static int process(int[][] arr, int index, int pre) {
        if (index == arr.length) {
            return 0;
        }
        // index位置可以做的决策有：
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr[index].length; i++) {
            if (i != pre) {
                min = Math.min(min, arr[index][i] + process(arr, index + 1, i));
            }
        }
        return min;
    }

    public static int process1(int[][] arr, int index) {
        if (index == arr.length) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        if (index == 0) {
            for (int i = 0; i < arr[index].length; i++) {
                int cost = arr[index][i];
                if (index + 1 < arr.length) {
                    swap(arr, index + 1, i);
                }
                min = Math.min(min, arr[index][i] + process1(arr, index + 1));
            }
        } else {
            for (int i = 0; i < arr[index].length - 1; i++) {
                int cost = arr[index][i];
                if (index + 1 < arr.length) {
                    swap(arr, index + 1, i);
                }
                min = Math.min(min, cost + process1(arr, index + 1));
            }
        }
        return min;
    }

        // 有问题
    public static int dp1(int[][] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int[]dp = new int[N + 1];

        for (int index = N - 1; index > 0; index--) {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < arr[index].length - 1; i++) {
                int cost = arr[index][i];
                if (index + 1 < arr.length) {
                    swap(arr, index + 1, i);
                }
                min = Math.min(min, cost + dp[index + 1]);
            }
            dp[index] = min;
        }
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr[0].length; i++) {
            min = Math.min(min, arr[0][i] + dp[1]);
        }
        dp[0] = min;
        System.out.println(Arrays.toString(dp));
        return dp[0];
    }

    public static void swap(int[][] arr, int index, int i) {
        int k = arr[0].length;
        int temp = arr[index][i];
        arr[index][i] = arr[index][k - 1];
        arr[index][k - 1] = temp;
    }

    public static int dp(int[][] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int k = arr[0].length;
        int[][] dp = new int[N + 1][k + 1];
        for (int index = N - 1; index >= 0; index--) {
            for (int pre = 0; pre <= k; pre++) {
                int min = Integer.MAX_VALUE;
                for (int i = 0; i < k; i++) {
                    if (i != pre) {
                        min = Math.min(min, arr[index][i] + dp[index + 1][i]);
                    }
                }
                dp[index][pre] = min;
            }
        }
        return dp[0][k];
    }

    public static int minCost(int[][] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        return process(arr, 0, arr[0].length);
    }

    public static int minCost1(int[][] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        return process1(arr, 0);
    }



    public static int[][] generateRandomArray(int maxSize, int maxValue) {
        int[][] arr = new int[(int) (Math.random() * maxSize) + 1][(int) (Math.random() * maxSize) + 2];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                arr[i][j] = (int) (Math.random() * maxValue + maxValue) - (int) (Math.random() * maxValue);
            }
        }
        return arr;
    }



    public static void main(String[] args) {
//        int[][] arr = {{1, 2, 3}, {4, 5, 6}, {1, 8, 9}, {10, 11, 12}};
//        System.out.println(dp(arr));
//        System.out.println(minCost(arr));
//        System.out.println(dp1(arr));
        int maxSize = 15;
        int maxValue = 50;
        int testTime = 500000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[][] arr = generateRandomArray(maxSize, maxValue);
            int ans1 = dp(arr);  // 我的
            int ans2 = getMinimumCost(arr); // 架构的
            if (ans1 != ans2) {
                print(arr);
                System.out.println("ans1:" + ans1);
                System.out.println("ans2:" + ans2);
                break;
            }
        }
        System.out.println("测试结束");
    }

    private static void print(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int getMinimumCost(int[][] matrix) {
        //N个房子 等于 min(matrix(N-1)) + min(matrix(N)) 当颜色不一致的时候
        //等于 min(matrix(N-1)) + secondMin(matrix(N)) 当颜色冲突的时候
        int minCost = 0;
        int lastIndexOfMinCost = -1;
        int secondLowerCost = 0;
        for (int i = 0; i < matrix.length; i++) {
            int minCostInRow = Integer.MAX_VALUE;
            int secondLowerCostInRow = Integer.MAX_VALUE;
            int indexOfMinCostInRow = -1;
            int currentCost;
            for (int j = 0; j < matrix[i].length; j++) {
                //上一个最小值，除了不能和最近一次冲突的颜色相加，别的都能相加
                //此时，会造成一个漏洞，不知道还有没有更优化的，更优化的，可以考虑
                //次小值和上一个最小值没有相加过的值相加，不需要再和另外的值相加了
                //因为没有意义，只是防止没有加的那个值非常小，以至于次小加它会更小
                if (lastIndexOfMinCost != j) {
                    currentCost = minCost + matrix[i][j];
                }else {
                    //上一个倒数第二小的值，和这个没有加的值相加，修补漏洞
                    currentCost = secondLowerCost + matrix[i][j];
                }
                //加完值后，要比一下 找到最小值
                if (currentCost < minCostInRow) {
                    secondLowerCostInRow = minCostInRow;
                    minCostInRow = currentCost;
                    indexOfMinCostInRow = j;
                } else if (currentCost < secondLowerCostInRow) {
                    secondLowerCostInRow = currentCost;
                }
            }
            minCost = minCostInRow;
            lastIndexOfMinCost = indexOfMinCostInRow;
            secondLowerCost = secondLowerCostInRow;
        }
        return minCost;
    }
}
