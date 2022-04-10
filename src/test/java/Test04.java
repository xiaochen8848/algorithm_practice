import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/2 - 04 - 02 - 12:53
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class Test04 {
    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        queue.add("a");
        queue.add("b");
        queue.add(null);
        queue.add("c");
        queue.add("d");
        while (!queue.isEmpty()) {
            System.out.println(queue.poll());
        }
        queue.poll();
    }
}
