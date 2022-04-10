package BinaryTreeRecursive;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/6 - 04 - 06 - 8:45
 * @Description: BinaryTreeRecursive
 * @version: 1.0
 */

/**
 * 给定一棵二叉树的头节点head，任何两个节点之间都存在距离，
 * 返回整棵二叉树的最大距离
 *
 *
 * 目标：求任意节点X为头的树，整棵树的最大距离
 * 第一步：列可能性   是跟X有关:整棵树的最大距离经过X  还是跟X无关:整棵树的最大距离不经过X
 * 1. X无关
 * (1)X左树最大
 * (2)X右树最大
 * 2.X有关
 * （1）X左树最远节点 + X右树最远节点 + 1
 * X左树最远：X左树高度
 * X右树最远：X右树高度
 *
 * 第二步：对左树和右树提出的要求
 * 左树：
 *     最大距离
 *     左树高度
 * 右树：
 *      最大距离
 *      右树高度
 * 求并集得：
 * Info{
 *     最大距离
 *     高度
 * }
 * 第三步：完成  Info process(Node head);
 *
 */
public class Code03 {


    public static int maxDistance(Node head) {
        return process(head).maxDistance;
    }

    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    static class Info {
        int maxDistance;
        int height;
        public Info(int maxDistance, int height) {
            this.maxDistance = maxDistance;
            this.height = height;
        }
    }

    public static Info process(Node head) {
        // base case
        if (head == null) { // null好不好设置？ 好设置 直接设置
            return new Info(0, 0);
        }

        // 从左树和右树拿到Info，加工自己的Info，最后返回Info
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        /**
         *  * 1. X无关
         *  * (1)X左树最大
         *  * (2)X右树最大
         *  * 2.X有关
         *  * （1）X左树最远节点 + X右树最远节点 + 1
         */
        int p1 = leftInfo.maxDistance;
        int p2 = rightInfo.maxDistance;
        int p3 = leftInfo.maxDistance + rightInfo.maxDistance + 1;
        int maxDistance = Math.max(p1, Math.max(p2, p3));


        return new Info(maxDistance, height);


    }
}
