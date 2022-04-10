import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/3 - 04 - 03 - 10:40
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */

/*采用map实现并查集*/
public class Test05 {

    // 包装项
    static class Node<V> {
        V value;
        public Node(V value) {
            this.value = value;
        }
    }

    public class UnionFind<V> {
        HashMap<V, Node<V>> nodes;
        HashMap<Node<V>, Node<V>> parents;
        HashMap<Node<V>, Integer> sizeMap;

        public UnionFind(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();

            for (V value : values) {
                Node node = new Node(value);
                nodes.put(value, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        // 找到节点数据value所在集合的代表节点
        public  Node findFather(V value) {
            Stack<Node> stack = new Stack<>();
            Node cur = nodes.get(value);
            while (cur != parents.get(cur)) {
                stack.push(cur);
                cur = parents.get(cur);
            }
            // 小优化
            while (!stack.isEmpty()) {
                parents.put(stack.pop(), cur);
            }
            return cur;
        }

        // 判断两个节点是否在同一个集合
        public boolean isSameSet(V a, V b) {
            return findFather(a) == findFather(b);
        }

        // 合并两个集合
        public void union(V a, V b) {
            Node node1 = findFather(a);
            Node node2 = findFather(b);
            if (node1 != node2) {
                Node big = sizeMap.get(node1) >= sizeMap.get(node2) ? node1 : node2;
                Node small = big == node1 ? node2 : node1;

                // 调整parents和sizeMap
                parents.put(small, big);
                sizeMap.put(big, sizeMap.get(big) + sizeMap.get(small));
                sizeMap.remove(small);
            }
        }
    }
}
