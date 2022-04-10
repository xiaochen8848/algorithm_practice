package BinaryTreeRecursive;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/5 - 04 - 05 - 21:16
 * @Description: BinaryTreeRecursive
 * @version: 1.0
 */

import java.awt.*;

/**
 * 判断一个二叉树是否是完全二叉树
 * <p>
 * 目标：任意一个节点x为头，它是不是搜索二叉树?
 * 第一步：列可能性(是指如果我能向我的左树要某些信息，向我的右树要某些信息的情况下去组织可能性)，满足哪些可能性 以X为头节点的树才是搜索二叉树
 * (1):X的左树是搜索二叉树
 * (2):X的右树是搜索二叉树
 * (3):X的左树上节点的最大值max<X
 * (4):X的右树上节点的最小值min>X
 * 满足这4个可能性，以X为头节点的树才是搜索二叉树,有一个违反就不是了
 * 第二步：Info
 * 对于左树：
 * 是否是搜索二叉树?
 * max
 * 对于右树：
 * 是否是搜索二叉树?
 * min
 * 求并集得：(递归对所有节点一视同仁)
 * Info{
 * 是否是搜索二叉树?
 * max
 * min
 *
 * 第三步：完成递归 Info process(Node head);
 * }
 */
public class Code02 {


    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    static class Info {
        boolean isBST;
        int max;
        int min;
        public Info(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }

    public static boolean isBST(Node head) {
        if (head == null)
            return true;
        return process(head).isBST;
    }

    // 递归
    public static Info process(Node head) {
        //1. base case
        if (head == null) { // 当为空的时候，不知道该如何设置Info时，就直接返回null，在上游对这个null进行处理
            return null;
        }

        // 2. 分别向左树和右树索要信息，然后根据拿到的信息去加工自己的信息，最后返回
        Info infoLeft = process(head.left);
        Info infoRight = process(head.right);
//        boolean isBST = ?;
//        int max = ?;
//        int min = ?;
//        return new Info(isBST, max, min);

        // 先给max这个信息设置好初值，然后看怎么违反
        int max = head.value;
        if (infoLeft != null)
            max = Math.max(infoLeft.max, max);
        if (infoRight != null)
            max = Math.max(infoRight.max, max);
        // 先给这个min设置好初值，然后看怎么违反
        int min = head.value;
        if (infoLeft != null)
            min = Math.min(infoLeft.min, min);
        if (infoRight != null)
            min = Math.min(infoRight.min, min);
        // 先给这个isBST设置好初值，然后看怎么违反
        boolean isBST = true;
        /**
         *  * (1):X的左树是搜索二叉树
         *  * (2):X的右树是搜索二叉树
         *  * (3):X的左树上节点的最大值max<X
         *  * (4):X的右树上节点的最小值min>X
         */
        if (infoLeft != null && !infoLeft.isBST)
            isBST = false;
        if (infoRight != null && !infoRight.isBST)
            isBST = false;
        if (infoLeft != null && infoLeft.max >= head.value)
            isBST = false;
        if (infoRight != null && infoRight.min <= head.value)
            isBST = false;
        return new Info(isBST, max, min);






    }
}
