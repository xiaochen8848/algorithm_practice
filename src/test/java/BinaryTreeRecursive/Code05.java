package BinaryTreeRecursive;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/6 - 04 - 06 - 9:45
 * @Description: BinaryTreeRecursive
 * @version: 1.0
 */

import com.sun.org.apache.xml.internal.dtm.ref.DTMAxisIterNodeList;

import java.util.ArrayList;

/**
 * 给定一棵二叉树的头节点head，
 * 返回这颗二叉树中最大的二叉搜索子树的大小
 *  目标：求以X为头的二叉树的搜索二叉树子树的maxSize
 *
 *  第一步：列可能性  X无关(X不做头)  X有关(X做头)
 *  X无关：
 *  (1):左树上的maxSize
 *  (2):右树上的maxsize
 *  X有关：也就是X为头的整棵树都是搜索二叉树
 *  (1):左树是否是搜索二叉树?
 *  (2):右树是否是搜索二叉树?
 *  (3):左树的最大值<X
 *  (4):右树的最小值>X
 *  (5):左树的size和右树的size+1
 *
 *第二步：
 * Info{
 *     maxSize;
 *     是否是搜索二叉树? // 可以省略  if maxSize==size 则true
 *     max
 *     min
 *     整棵树的size
 * }
 *发现可以省略掉一个信息：
 * 如果我整棵树的size和最大搜索二叉子树的maxSize相等，说明我整棵是是搜索二叉树
 *
 **/


public class Code05 {
    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    static class Info {
        int maxBSTSubtreeSize;
        int max;
        int min;
        int allSize;

        public Info(int maxBSTSubtreeSize, int max, int min, int allSize) {
            this.maxBSTSubtreeSize = maxBSTSubtreeSize;
            this.max = max;
            this.min = min;
            this.allSize = allSize;
        }
    }

    public static int maxSubBSTSize2(Node head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxBSTSubtreeSize;
    }

    public static Info process(Node head) {
        if (head == null) { // 当head为null时，不好设置info，就直接返回null
            return null;
        }
        // 拿到左树和右树的Info，然后组装成自己的Info返回
        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        /**
         *    Info {
         *         int maxBSTSubtreeSize;
         *         int max;
         *         int min;
         *         int allSize;
         *     }
         */
        // 先组装和题意无关的信息 和题意有关的信息就一个  maxBSTSubtreeSize
        int max = head.value;
        int min = head.value;
        int allSize = 1;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
            allSize += leftInfo.allSize;
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
            allSize += rightInfo.allSize;
        }
        // 在组装maxBSTSubtreeSize
        /**
         *  X无关：
         *  *  (1):左树上的maxSize
         *  *  (2):右树上的maxsize
         *  *  X有关：也就是X为头的整棵树都是搜索二叉树
         *  *  (1):左树是否是搜索二叉树?
         *  *  (2):右树是否是搜索二叉树?
         *  *  (3):左树的最大值<X
         *  *  (4):右树的最小值>X
         *  *  (5):左树的size和右树的size+1
         */
        int p1 = leftInfo == null ? 0 : leftInfo.maxBSTSubtreeSize;
        int p2 = rightInfo == null ? 0 : rightInfo.maxBSTSubtreeSize;
        boolean left = leftInfo == null || leftInfo.allSize == leftInfo.maxBSTSubtreeSize
                && leftInfo.max < head.value;
        boolean right = rightInfo == null || rightInfo.allSize == rightInfo.maxBSTSubtreeSize
                && rightInfo.min > head.value;
        int p3 = left && right ? (leftInfo != null ? leftInfo.allSize : 0)
                + (rightInfo != null ? rightInfo.allSize : 0) + 1 : 0;

        return new Info(Math.max(p1, Math.max(p2, p3)),max, min, allSize);
    }





    // 左神
    public static int maxSubBSTSize1(Node head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(maxSubBSTSize1(head.left), maxSubBSTSize1(head.right));
    }
    public static int getBSTSize(Node head) {
        if (head == null) {
            return 0;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return 0;
            }
        }
        return arr.size();
    }
    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }


    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (maxSubBSTSize1(head) != maxSubBSTSize2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }

}



/**
 * 思想基础：一棵树上的任意节点，可以向自己的左树和右树要任何信息。
 * 步骤一：讨论以X为头节点的树，讨论答案的可能性：
 * 1. 答案和X有关
 *  也就是以X为头节点的整棵树都必须符合目标，那么我需要分别向右树和左树要什么信息
 *  同时这些信息符合什么条件，才能符合我的目标？
 * 2. 答案和X无关
 *  也就是答案不在以X为头节点的整棵树上，可能在X的左树上，也可能在X的右树上
 * 步骤二：
 * 答案的可能性讨论完之后，确定左树需要提供什么信息，右树需要提供什么信息，
 * 两者求全集就是我任意节点需要准备的Info，因为是递归。有时候全集求出来的信息可以省略掉某个信息，因为根据其它的几个信息可以推出它
 * 步骤三：
 * 完成递归函数
 *
 * Info process(Node head){
 *  // 1.当节点为null的时候，分析Info好不好设置，如果好设置就直接设置，如果不好设置直接返回null，在上游对其进行处理
 *     if (head == null){
 *         return ?
 *     }
 *  // 2. 向左树和右树分别索要Info，然后组装成自己的info最后返回
 *  Info leftInfo = process(head.left);
 *  Info rightInfo = process(head.right);
 *
 *  // 3.根据左树和右树的info，先组装和目标无关的信息(一般我们的info只有一个信息是答案，其它否是辅助)
 *  ...
 *  // 4.根据我们步骤1列出的所有可能性，完成关键信息的组装
 *  ...
 *  // 5.返回自己节点的Info
 *  return new Info(...);
 * }
 */