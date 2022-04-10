package week2;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/8 - 04 - 08 - 18:07
 * @Description: week2
 * @version: 1.0
 */

/**
 * 问题描述：有三根柱子，分别是左，中，右，假定有n个圆盘，大小依次从1~n变大，
 * 它一开始全部都在左柱子上，现在需要把它全部移动到右柱子上，
 * 而且规定小圆盘必须放在大圆盘的上面，每次只能移动一个圆盘，
 * 问要完成这n个圆盘的移动，需要几步？
 */


public class Hanoi {
    /**
     * 分析：n个盘子，需要从左边移动到右边，且每次只能移动一个盘子，小盘子必须放在大盘子上面
     * 实现一个递归函数leftToRight(int n)
     */

    // 实现把n个圆盘从左边移动到右边
    public static void leftToRight(int n) {
        if (n == 1) {
            System.out.println("1 from left to right");
            return;
        }

        /**
         * 当不只有1个圆盘时，可以把上面n-1个圆盘先从左边移动到中间
         * 再把第n个圆盘从左边移动到右边
         * 然后把中间那n-1个圆盘从中间移动到右边
         */

        leftToMid(n - 1);
        System.out.println(n + " from left to right");
        midToRight(n - 1);
    }


    // 完成n个圆盘从中间移动到右边
    private static void midToRight(int n) {
        if (n == 1) {
            System.out.println("1 from mid to right");
            return;
        }
        midToLeft(n - 1);
        System.out.println(n + " from mid to right");
        leftToRight(n - 1);
    }

    // 完成n个圆盘从左边移动到中间
    private static void leftToMid(int n) {
        if (n == 1) {
            System.out.println("1 from left to mid");
            return;
        }

        /**
         * 当不只有1个圆盘时，把上面n-1个圆盘从左边移动到右边
         * 再把第n个圆盘从左边移动到中间
         * 最后把n-1个圆盘从右边移动到中间
         */

        leftToRight(n-1);
        System.out.println(n + " from left to mid");
        rightToMid(n - 1);
    }

    // 完成n个圆盘从右边移动到中间
    private static void rightToMid(int n) {
        if (n == 1) {
            System.out.println("1 from right to mid");
            return;
        }
        /**
         * 当不只有一个圆盘时，把上面n-1个圆盘从右边移动到左边
         * 再把第n个圆盘从右边移动到中间
         * 最后再把n-1个圆盘从左边移动到中间
         */
        rightToLeft(n - 1);
        System.out.println(n + " from right to mid");
        leftToMid(n - 1);
    }

    // 完成n个圆盘从右边移动到左边
    private static void rightToLeft(int n) {
        if (n == 1) {
            System.out.println("1 from right to left");
            return;
        }

        /**
         * 当不只有1个圆盘时，把上面的n-1个圆盘从右边移动到中间
         * 再把第n个圆盘从右边移动到左边
         * 最后把那n-1个圆盘从中间移回到左边
         */

        rightToMid(n - 1);
        System.out.println(n + " from right to left");
        midToLeft(n - 1);
    }

    // 完成n个圆盘从中间移动到左边
    private static void midToLeft(int n) {
        if (n == 1) {
            System.out.println("1 from mid to left");
            return;
        }
        /**
         * 当不只有1个圆盘时，先把上面n-1个圆盘从中间移动到右边
         * 再把第n个圆盘从中间移动到左边
         * 最后把那n-1个圆盘从右边移动到中间
         */
        midToRight(n - 1);
        System.out.println(n + "from mid to left");
        rightToMid(n - 1);
    }

    //======================================================================================
    public static void process(int n, String from, String to, String other) {
        if (n == 1) {
            System.out.println("1 from " + from + " to " + to);
            return;
        }
        process(n - 1, from, other, to);
        System.out.println(n + " from " + from + " to " + to);
        process(n - 1, other, to, from);
    }

    public static void main(String[] args) {
        leftToRight(3);
        System.out.println();
        process(3, "left", "right", "mid");
    }
}
