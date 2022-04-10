package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/3/30 - 03 - 30 - 20:30
 * @Description: week1
 * @version: 1.0
 */

/**
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线
 * 玩家A和玩家B依次拿走每张纸牌
 * 规定玩家A先拿，玩家B后拿（决策对象 ）
 * 但是每个玩家每次只能拿走最左或最右的纸牌（所有可能的决策）
 * 玩家A和玩家B都绝顶聪明
 * 请返回最后获胜者的分数（返回两个决策对象中那个分数最大的那个   求所有决策最优那个）
 */
public class CardsInLine {
    public static int win1(int[] arr) {
        int first = f(arr, 0, arr.length - 1);
        int second = g(arr, 0, arr.length - 1);
        return Math.max(first, second);
    }

    // 先手决策
    // 返回对于数组arr L...R范围上最大的分数
    public static int f(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];
        }

        // 两种决策
        // 拿了最左侧的牌，然后作为后手去L+1...R范围上获取最大的
        int p1 = arr[L] + g(arr, L + 1, R);
        // 拿了最右侧的牌，然后作为后手去L...R-1f范围上获取最大的
        int p2 = arr[R] + g(arr, L, R - 1);
        return Math.max(p1, p2);
    }

    // 后手决策
    // 返回对于数组arr L...R范围上最大的分数
    public static int g(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }
        // 两种决策
        // 如果先手拿了最左侧的牌 我接下来作为先手在L+1...R范围上获取最大的分数
        int p1 = f(arr, L +1, R);
        // 如果先手拿了最右侧的牌，我接下来作为先生在L..R-1范围上获取最大的分数
        int p2 = f(arr, L, R - 1);
        // 由于先手和后手都绝顶聪明，所以作为后手能拿到的“最好”分数是先手经过计算得到的
        return Math.min(p1, p2);
    }

    /**
     *
     * 代入具体的例子展开递归，发现有重复调用，所以可以优化
     */

    public static int dp(int[] arr) {
        int N = arr.length;
        int[][] first = new int[N][N];
        int[][] second = new int[N][N];

        /*base case*/
        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                if (i == j) {
//                    first[i][j] = arr[i];
//                }
//            }
            first[i][i] = arr[i];
        }
        /*通过画图发现可以从底往上 从左往右遍历 先填first 再填second*/
        for (int i = N - 2; i >= 0; i--) {
            for (int j = i + 1; j < N; j++) {
                /*填first表*/
                int p1 = arr[i] + second[i + 1][j];
                int p2 = arr[j] + second[i][j - 1];
                first[i][j] = Math.max(p1, p2);
                /*填second表*/
                int p3 = first[i +1][j];
                int p4 = first[i][j - 1];
                second[i][j] =  Math.min(p3, p4);
            }
        }
        return Math.max(first[0][N - 1], second[0][N - 1]);
    }

    public static void main(String[] args) {
        int[] arr = { 5, 7, 4, 5, 8, 1, 75,6, 0, 3, 4, 6, 1, 7 };
        System.out.println(win1(arr));
        System.out.println(dp(arr));
    }
}
