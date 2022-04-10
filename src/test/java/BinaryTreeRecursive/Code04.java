package BinaryTreeRecursive;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/6 - 04 - 06 - 9:11
 * @Description: BinaryTreeRecursive
 * @version: 1.0
 */

/**
 * 判断一棵树是不是满二叉树
 * 目标：任意节点为头的整棵树是不是满二插树
 * 第一步：列可能性
 * (1)左树是满二叉树
 * (2)右树是满二叉树
 * (3)左树的高度和右树的高度相等
 * 第二步：向左树和右树索要的信息
 * 左树：
 *      是否是满二叉树
 *      高度
 * 右树：
 *      是否是满二叉树
 *      高度
 * 求并集得：
 * info{
 *     是否是满二叉树
 *     高度
 * }
 *
 * 第三步：完成 Info process(Node head);
 */
public class Code04 {
    static class Node {
        int value;
        Node left;
        Node right;

        public Node(int value) {
            this.value = value;
        }
    }

    static class Info {
        boolean isFull;
        int height;
        public Info(boolean isFull, int height) {
            this.isFull = isFull;
            this.height = height;
        }
    }
    public static boolean isFull(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isFull;
    }

    // 我的
    public static Info process(Node head) {
        if (head == null) {
            return new Info(true, 0);
        }

        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        boolean isFull = false;
        if (leftInfo.isFull && rightInfo.isFull && leftInfo.height == rightInfo.height) {
            isFull = true;
        }
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        return new Info(isFull, height);
    }


    // 左神 的
    public static boolean isFull1(Node head) {
        if (head == null) {
            return true;
        }
        int height = h(head);
        int nodes = n(head);
        return (1 << height) - 1 == nodes;
    }
    public static int n(Node head) {
        if (head == null) {
            return 0;
        }
        return n(head.left) + n(head.right) + 1;
    }

    public static int h(Node head) {
        if (head == null) {
            return 0;
        }
        return Math.max(h(head.left), h(head.right)) + 1;
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
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isFull1(head) != isFull(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
