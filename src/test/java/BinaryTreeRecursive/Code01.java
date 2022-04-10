package BinaryTreeRecursive;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/5 - 04 - 05 - 20:42
 * @Description: BinaryTreeRecursive
 * @version: 1.0
 */

/**
 * 判断一个二叉树是否是完全二叉树
 * 完全二叉树定义：
 * (1)如果一个节点只有右孩子没有左孩子，则一定不是完全二叉树
 * (2)如果一个节点左右孩子不全，则剩下的节点必须都是叶子节点
 *
 * 二叉树递归套路解决：
 * 第一步：可能性？
 * (1)X左树是平衡二叉树
 * (2)X右树是平衡二叉树
 * (3)|X左高-X右高| < 2
 * 则三个条件都满足，则以X为头节点的二叉树是一个完全二叉树
 * 第二步：需要向左树和右数什么信息？
 * X左：是否是平衡二叉树？ 高度
 * X右：是否是平衡二叉树？ 高度
 * 对这两个信息求并集得到：
 * Info{
 *     boolean isBalance;
 *     int height;
 * }
 * (如果想顺利的在递归里把这件事情完成的话，X也面临同样的信息要求，因为递归要求所有入参和过程是统一的要求)
 *第三步：开始憋递归函数  Info process(Node head);
 *
 */
public class Code01 {


    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    static class Info {
        boolean isBalance;
        int height;

        public Info(boolean isBalance, int height) {
            this.isBalance =  isBalance;
            this.height = height;
        }
    }

    // 主函数
    static boolean isBalance(Node head) {
        return process(head).isBalance;
    }

    // 递归函数
    public static Info process(Node head) {
        // 1. 当head是空的时候，我该返回什么？返回一个Info？那Info的值设置成什么？
        if (head == null) {
            return new Info(true, 0); // 当为空的时候，可以认为是一棵平衡二叉树，高度可以认为是0
        }

        // 2.process 是个黑盒子了
        // 我向我的左树所要Info,向我的右树索要Info
        Info infoLeft = process(head.left);
        Info infoRight = process(head.right);

        // 3. 同时我也得生成我的Info，这样才能连贯起来
        // boolean isBalance = ?; int height = ?;
        // 最后如果这两个信息能得到，我返回我的Info return new Info(isBalance, height);

        // 我如何得到我的height？
        int height = Math.max(infoLeft.height, infoRight.height) + 1;

        // 我如何得到我的isBalance ? 我先假设是平衡的，再根据左右树的信息是否违反条件
        /* 我们的条件是：
             (1)X左树是平衡二叉树
             (2)X右树是平衡二叉树
             (3)|X左高-X右高| < 2
        */
        boolean isBalance = true;
        if (!infoLeft.isBalance)
            isBalance = false;
        if (!infoRight.isBalance)
            isBalance = false;
        if (Math.abs(infoLeft.height - infoRight.height) > 1)
            isBalance = false;

        // 返回x自己的info
        return new Info(isBalance, height);
    }
}
