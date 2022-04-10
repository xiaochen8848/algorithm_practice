package week2;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/4 - 04 - 04 - 9:12
 * @Description: week2
 * @version: 1.0
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 给一个m*n的数组，每次都空降一个位置(r, c)，表示这个位置是1，求每次的岛屿数量。
 */
public class NumberOfIslandsII {
    /**
     * 分析：每次空降一个位置(r, c)，就对这个位置进行初始化
     * 然都对这个位置的上下左右位置尝试去合并，当然这个位置的上下左右位置必须是已经初始化了，才你能合并
     */

    // 采用一维数组并查集结构实现
    public static List<Integer> numIslands21(int m, int n, int[][] positions) {
        Union union = new Union(m, n);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            list.add(union.connect(positions[i][0], positions[i][1]));
        }
        return list;
    }

    static class Union {
        private int[] parents;
        private int[] size;
        private int[] help;
        private int sets;
        private int row;
        private int col;

        public Union(int m, int n) {
            row = m;
            col = n;
            int len = m * n;
            parents = new int[len];
            size = new int[len];
            help = new int[len];
        }

        public int findFather(int index) {
            int k = 0;
            while (index != parents[index]) {
                help[k++] = index;
                index = parents[index];
            }
            while (k > 0) {
                parents[help[--k]] = index;
            }
            return index;
        }

        public void union(int index, int a, int b) {
            if (a < 0 || a == row || b < 0 || b == col)
                return;
            int index2 = indexOf(a, b);
            if (size[index2] != 1)
                return;
            int f1 = findFather(index);
            int f2 = findFather(index2);
            if (f1 != f2) {
                int big = size[f1] >= size[f2] ? f1 : f2;
                int small = big == f1 ? f2 : f1;
                parents[small] = big;
                size[big] += size[small];
                sets--;
            }
        }

        public int connect(int r, int c) {
            if (r < 0 || r == row || c < 0 || c == col)
                return sets;
            int index = indexOf(r, c);
            if (size[index] == 1) // 说明之前已经被初始化
                return sets;
            parents[index] = index;
            size[index] = 1;
            sets++;

            union(index, r, c - 1);
            union(index, r - 1, c);
            union(index, r, c + 1);
            union(index, r + 1, c);
            return sets;
        }

        public int indexOf(int i, int j) {
            return i * col + j;
        }
    }

    // map结构的并查集实现 m*n很大，但是positions又很小
    // 由于每次都要去找给定样本的上下左右
    // 所以必须给每个样本包装成具有唯一性且可查找的
    public static List<Integer> numIslands22(int m, int n, int[][] positions) {
        Union2 union2 = new Union2();
        List<Integer> list = new ArrayList<>();
        for (int[] position : positions) {
            list.add(position[0], position[1]);
        }
        return list;
    }

    static class Union2 {
        private HashMap<String, String> parents;
        private HashMap<String ,Integer> sizeMap;

        public Union2() {
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
        }

        public String findFather2(String s) {
            Stack<String> stack = new Stack<>();
            while (!s.equals(parents.get(s))) {
                stack.push(s);
                s = parents.get(s);
            }
            while (!stack.isEmpty()) {
                parents.put(stack.pop(), s);
            }
            return s;
        }

        public void union2(String s1, int r, int c) {
            String s2 = r + "_" + c;
            if (!parents.containsKey(s2)) {  //只有当s2的时候才进行合并
                String f1 = findFather2(s1);
                String f2 = findFather2(s2);
                if (!f1.equals(f2)) {
                    Integer size1 = sizeMap.get(f1);
                    Integer size2 = sizeMap.get(f2);
                    String big = size1 >= size2 ? f1 : f2;
                    String small = size1 >= size2 ? f2 : f1;
                    parents.put(small, big);
                    sizeMap.put(big, size1 + size2);
                    sizeMap.remove(small);
                }
            }
        }

        public int connect(int r, int c) {
            String s1 = r + "_" + c;
            if (!parents.containsKey(s1)) { // 当s1没有 初始化的时候
                parents.put(s1, s1);
                sizeMap.put(s1, 1);
                // 尝试四个方向的合并
                union2(s1, r, c - 1);
                union2(s1, r - 1, c);
                union2(s1, r, c + 1);
                union2(s1, r + 1, c);
            }
            return sizeMap.size();
        }
    }
}
