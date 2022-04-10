package week2;

import java.util.HashMap;
import java.util.UnknownFormatConversionException;
import java.util.concurrent.Callable;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/4 - 04 - 04 - 14:51
 * @Description: week2
 * @version: 1.0
 */

// 前缀树实现
public class TrieTree {

    // 节点类型采用数组结构
    static class Node01 {
        private int pass;
        private int end;
        private Node01[] next;

        public Node01() {
            pass = 0;
            end = 0;
            next = new Node01[26];  // 假设字符都是小写字母或者大写字母
        }
    }

    static class Trie1 {
        private Node01 root;

        public Trie1() {
            root = new Node01();
        }

        // 插入字符串
        public void insert(String str) {
            if (str == null || str.length() == 0)
                return;
            Node01 node = root;
            root.pass++;
            char[] chars = str.toCharArray();
            for (char c : chars) {
                int path = c - 'a';
                if (node.next[path] == null)
                    node.next[path] = new Node01();
                node = node.next[path];
                node.pass++;
            }
            node.end++;
        }

        // 查询字符串的个数
        public int search(String str) {
            if (str == null || str.length() == 0)
                return 0;
            char[] chars = str.toCharArray();
            Node01 node = root;
            for (char c : chars) {
                int path = c - 'a';
                if (node.next[path] == null)
                    return 0;
                node = node.next[path];
            }
            return node.end;
        }

        // 删除一个字符串
        public void delete(String str) {
            if (str == null || str.length() == 0 || search(str) == 0)
                return;
            Node01 node = root;
            char[] chars = str.toCharArray();
            node.pass--;
            for (char c : chars) {
                int path = c - 'a';
                if (--node.next[path].pass == 0) {
                    node.next[path] = null;
                    return;
                }
                node = node.next[path];
            }
            node.end--;
        }

        // 查询有多少个字符串是以str为前缀的
        public int prefixNumber(String str) {
            if (str == null || str.length() == 0)
                return 0;
            Node01 node = root;
            char[] chars = str.toCharArray();
            for (char c : chars) {
                int path = c - 'a';
                if (node.next[path] == null)
                    return 0;
                node = node.next[path];
            }
            return node.pass;
        }
    }

    // 节点类型结构采用map
    static class Node02 {
        private int pass;
        private int end;
        private HashMap<Integer, Node02> map;

        public Node02() {
            pass = 0;
            end = 0;
            map = new HashMap<>();
        }
    }

    public static class Trie3 {
        private Node02 root;

        public Trie3() {
           root = new Node02();
        }

        public void insert(String str) {
            if (str == null || str.length() == 0)
                return;
            Node02 node = root;
            root.pass++;
            char[] chars = str.toCharArray();
            for (char c : chars) {
                Node02 next = node.map.get((int) c);
                if (next == null) {  // 当时直接写 node.map.put((int) c, new Node02()); //导致空指针异常 next
                    next =  new Node02();
                    node.map.put((int) c, next);

                }
                next.pass++;
                node = next;
            }
            node.end++;
        }

        public int search(String str) {
            if (str == null || str.length() == 0)
                return 0;
            Node02 node = root;
            char[] chars = str.toCharArray();
            for (char c : chars) {
                if (!node.map.containsKey((int) c))
                    return 0;
                node = node.map.get((int) c);
            }
            return node.end;
        }

        public void delete(String str) {
            if (str == null || str.length() == 0 || search(str) == 0)
                return;
            Node02 node = root;
            node.pass--;
            char[] chars = str.toCharArray();
            for (char c : chars) {
                if (--node.map.get((int) c).pass == 0) {
                    node.map.remove((int) c);  // 当时惯性思维直接node.map.put((int) c, null)，导致search方法空指针异常
                    return;
                }
                node = node.map.get((int) c);
            }
            node.end--;
        }

        public int prefixNumber(String str) {
            if (str == null || str.length() == 0)
                return 0;
            Node02 node = root;
            char[] chars = str.toCharArray();
            for (char c : chars) {
                Node02 next = node.map.get((int) c);
                if (next == null)
                    return 0;
                node = next;
            }
            return node.pass;
        }
    }

    public static void main(String[] args) {
        int testTimes = 1000000;
        Trie1 trie1 = new Trie1();
        Trie2 trie2 = new Trie2();
        Trie3 trie3 = new Trie3();
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            String str = generateRandomString();
            double p = Math.random();
            if (p < 0.25) {
                trie1.insert(str);
                trie2.insert(str);
                trie3.insert(str);
            } else if (p < 0.5) {
                trie1.delete(str);
                trie2.delete(str);
                trie3.delete(str);
            } else if (p < 0.75) {
                int search1 = trie1.search(str);
                int search2 = trie2.search(str);
                int search3 = trie3.search(str);
                if (search1 != search3 || search2 != search3) {
                    System.out.println("search出错了");
                    break;
                }
            } else {
                int prefix1 = trie1.prefixNumber(str);
                int prefix2 = trie2.prefixNumber(str);
                int prefix3 = trie3.prefixNumber(str);
                if (prefix1 != prefix3 || prefix2 != prefix3) {
                    System.out.println("prefix出错了");
                    break;
                }
            }
        }
        System.out.println("测试结束");
    }

    public static String generateRandomString() {
        char[] str = new char[(int) (Math.random() * 26)];
        for (int i = 0; i < str.length; i++) {
            str[i] = (char) (Math.random() * 26 + 'a');
        }
        return String.valueOf(str);
    }

    /*左神代码*/
    public static class Trie2 {
        private Node01 root;

        public Trie2() {
            root = new Node01();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }
            char[] str = word.toCharArray();
            Node01 node = root;
            node.pass++; // 头节点pass++
            int path = 0;
            for (int i = 0; i < str.length; i++) { // 从左往右遍历字符
                path = str[i] - 'a'; // 由字符，对应成走向哪条路
                if (node.next[path] == null) { // path 方向上的路不存在 新建一个
                    node.next[path] = new Node01();
                }
                node = node.next[path]; // node来到这个节点
                node.pass++;
            }
            node.end++; //
        }

        // 大逻辑:对要删除的word沿途p--操作，然后判断p==0?如果等于0的话就不再往后查了，直接让该结点的next=null返回
        public void delete(String word) {
            if (search(word) != 0) {
                char[] chs = word.toCharArray();
                Node01 node = root;
                node.pass--;
                int path = 0;
                for (int i = 0; i < chs.length; i++) {
                    path = chs[i] - 'a';
                    if (--node.next[path].pass == 0) {
                        node.next[path] = null;  // 因为一个子节点的pass<= 父节点的pass 如果一个节点的pass是0
                        // 说明该节点是尾节点或者该节点只有一个分支 直接让该节点变为null即可
                        return;
                    }
                    node = node.next[path];
                }
                node.end--;
            }
        }

        // word这个单词之前加入过几次 顺着头节点根据word一直往下找，如果能一直来到word结尾字符对应的那个节点，
        // 就返回该尾节点的end值，如果中途没有路可走，直接返回0
        public int search(String word) {
            if (word == null || word.length() == 0) {
                return 0;
            }
            char[] chs = word.toCharArray();
            Node01 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.next[index] == null) {
                    return 0;
                }
                node = node.next[index];
            }
            return node.end;
        }

        // 所有加入的字符串中，有几个是以pre这个字符串作为前缀的
        // 和search方法一样，只不过最后到达pre尾字符对应的节点后返回的是该节点的pass值
        public int prefixNumber(String pre) {
            if (pre == null || pre.length() == 0) {
                return 0;
            }
            char[] chs = pre.toCharArray();
            Node01 node = root;
            int index = 0;
            for (int i = 0; i < chs.length; i++) {
                index = chs[i] - 'a';
                if (node.next[index] == null) {
                    return 0;
                }
                node = node.next[index];
            }
            return node.pass;
        }
    }
}
