package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/3 - 04 - 03 - 12:01
 * @Description: week1
 * @version: 1.0
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 给定一个二维数组matrix，里面的值不是1就是0，
 * 上、下、左、右相邻的1认为是一片岛，返回matrix中岛的数量
 * https://leetcode.com/problems/number-of-islands/
 */
public class NumberOfIslands {
    // 使用感染的方法实现
    public static int numIslands3(char[][] board) {
        int isLand = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '1') {
                    isLand++;
                    infect(board, i, j);
                }
            }
        }
        return isLand;
    }

    // 从(i, j)位置开始 把上下左右相邻的一片1变成0
    private static void infect(char[][] board, int i, int j) {
        if (i < 0 || i == board.length || j < 0 || j == board[0].length || board[i][j] != '1')
            return;
        board[i][j] = '0';
        infect(board, i, j - 1);
        infect(board, i - 1, j);
        infect(board, i, j + 1);
        infect(board, i + 1, j);
    }



    public static int numIslands1(char[][] board) {
        // 样本是board中那些位置上的字符是1的，由于是基本数据类型且有重复的，所以需要包装一下
        // list存储所有的样本
        List<Dot> list = new ArrayList<>();
        // 样本的映射，为了取出样本
        Dot[][] dots = new Dot[board.length][board[0].length];
        // 1.取出样本
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '1') {
                    Dot dot = new Dot();
                    dots[i][j] = dot;
                    list.add(dot);
                }
            }
        }
        Union1<Dot> union1 = new Union1<>(list);
        // 2.完成对样本的合并 如果一个位置的左边和上边的字符都是1就合并
        // 先完成对边界的合并  0列没有左边  0行没有上边
        for (int i = 1; i < board.length; i++) {
            if (board[i][0] == '1' && board[i - 1][0] == '1')
                union1.union1(dots[i][0], dots[i - 1][0]);
        }
        for (int i = 1; i < board[0].length; i++) {
            if (board[0][i] == '1' && board[0][i - 1] == '1')
                union1.union1(dots[0][i], dots[0][i - 1]);
        }
        // 完成普遍位置的合并
        for (int i = 1; i < board.length; i++) {
            for (int j = 1; j < board[0].length; j++) {
                if (board[i][j] == '1' && board[i][j - 1] == '1')
                    union1.union1(dots[i][j], dots[i][j - 1]);
                if (board[i][j] == '1' && board[i - 1][j] == '1')
                    union1.union1(dots[i][j], dots[i - 1][j]);
            }
        }
        return union1.sets();
    }
    static class Dot {

    }
    static class Union1<T> {
        private HashMap<T, T> parents;
        private HashMap<T, Integer> sizeMap;


        public Union1(List<T> list) {
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (T t : list) {
                parents.put(t, t);
                sizeMap.put(t, 1);
            }
        }

        public T findFather1(T t) {
            Stack<T> stack = new Stack<>();
            while (t != parents.get(t)) {
                stack.push(t);
                t = parents.get(t);
            }
            while (!stack.isEmpty()) {
                parents.put(stack.pop(), t);
            }
            return t;
        }

        public void union1(T t1, T t2) {
            T f1 = findFather1(t1);
            T f2 = findFather1(t2);
            if (f1 != f2) {
                T big = sizeMap.get(f1) >= sizeMap.get(f2) ? f1 : f2;
                T small = big == f1 ? f2 : f1;
                parents.put(small, big);
                sizeMap.put(big, sizeMap.get(big) + sizeMap.get(small));
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }
    }

    // 采用一维数组构建并查集实现
    public static int numIslands2(char[][] board) {
        Union2 union2 = new Union2(board);
        // 先处理边界上的合并
        for (int i = 1; i < board.length; i++) {
            if (board[i][0] == '1' && board[i - 1][0] == '1')
                union2.union2(i, 0, i - 1, 0);
        }
        for (int i = 1; i < board[0].length; i++) {
            if (board[0][i] == '1' && board[0][i - 1] == '1')
                union2.union2(0, i, 0, i - 1);
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '1') {
                    if (board[i][j - 1] == '1')
                        union2.union2(i, j, i, j - 1);
                    if (board[i - 1][j] == '1')
                        union2.union2(i, j, i - 1, j);
                }
            }
        }
        return union2.sets;
    }

    static class Union2 {
        private int[] parents;
        private int[] size;
        private int sets;
        private int row;
        private int col;
        private int[] help;

        public Union2(char[][] board) {
            row = board.length;
            col = board[0].length;
            parents = new int[row * col];
            size = new int[row * col];
            help = new int[row * col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (board[i][j] == '1') {
                        int index = indexOf(i, j);
                        parents[index] = index;
                        size[index] = 1;
                        sets++;
                    }
                }
            }
        }

        public int findFather2(int i, int j) {
            int index = indexOf(i, j);
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

        public void union2(int i, int j, int a, int b) {
            int f1 = findFather2(i, j);
            int f2 = findFather2(a, b);
            if (f1 != f2) {
               int big = size[f1] >= size[f2] ? f1 : f2;
               int small = big == f1 ? f2 : f1;
               parents[small] = big;
               size[big] += size[small];
               sets--;
            }
        }

        // (i, j)-->index
        public int indexOf(int i, int j) {
            return i * col + j;
        }
    }

}

/**
 *
 *   使用并查集解决问题：
 *   1. 先分析样本是谁，题目给出的条件是不是直接样本，如果不是还需要我们取出来放在一个集合中方便一次性初始化
 *   2. 该采用什么结构来实现，一维数组还是map
 *   3. 根据题意对样本进行合并
 *
 *
 * 对于并查集结构的设计，我们一般用一维数组或者map，
 * 无论是map还是一维数组，必须具备的两个结构:
 * (1)每一个节点和自己父节点的映射
 * (2)每一个集合中代表节点(该节点的父节点是自己)和该集合元素个数的映射
 * 1. 一维数组：要求每个样本能对应一维数组的一个下标
      int[] parents;  // parents[i] = i 初始化的时候，每个样本的父亲是自己
      int[] size; // 当i是代表元素时，size[i]有意义
      int sets;
 *
 *
 * 2. map:
      HashMap<T, T> parents;
      HashMap<T, Integer> sizeMap;
 * 对于使用map实现我们还需要注意需要不需要对样本进行包装，也就是不同的样本能否区分开来
 * 如果需要包装的话，还要相应的准备样本和自己包装对象的映射
 */
