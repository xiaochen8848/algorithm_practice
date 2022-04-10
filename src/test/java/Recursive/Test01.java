package Recursive;

import javax.sound.midi.Soundbank;
import java.util.Stack;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/5 - 04 - 05 - 9:28
 * @Description: Recursive
 * @version: 1.0
 */
public class Test01 {
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }

    public static void process(Node head) {
        if (head == null)
            return;
        System.out.print(head.value + " ");  // 进入任何个任务时都打印
        process(head.left);  // 左子树执行完了回来时打印process
        System.out.print("process");
        process(head.right);
    }

    public static void f(Node head) {
        if (head != null) {
            Stack<Node> stack = new Stack<>();
            stack.push(head);
            while (!stack.isEmpty()) {
                head = stack.pop();
                System.out.print(head.value + " ");
                if (head.right != null)
                    stack.push(head.right);
                if (head.left != null)
                    stack.push(head.left);

            }
        }
    }
    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.left.right = new Node(5);
        head.left.right.left = new Node(6);
        head.left.right.right = new Node(7);
        process(head);
        System.out.println();
        f(head);
    }
}
