package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/3 - 04 - 03 - 11:12
 * @Description: week1
 * @version: 1.0
 */

/**
 * 使用二维数组N*N，表示N个人之间的关系，当
 * arr[i][j]=1即表示i和j互相认识，也即arr[j][i]=1，
 * 如果不等于1也即表示互相不认识。当
 * arr[i][i]==1时表示自己认识自己。
 * 也即这个二维数组时对称的。
 *
 * 最后返回朋友圈(集合)的个数
 */
public class FriendCircles {
    public int findCircleNum(int[][] isConnected) {
        int N = isConnected.length;
        Union union = new Union(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {  // 因为是对称的
                if (isConnected[i][j] == 1) {
                    union.union(i, j);
                }
            }
        }
        return union.getSets();
    }

    static class Union {
        private int[] parents;
        private int[] size;
        // 之所以还需要这个变量，是因为当合并两个集合的时候，我们并没有把size数组中那个交小的集合代表节点删掉
        private int sets;
        private int[] help;

        public Union(int N) {
            parents = new int[N];
            size = new int[N];
            help = new int[N];
            sets = N;
            for (int i = 0; i < N; i++) {
                parents[i] = i;
                size[i] = 1;
            }
        }

        public int findFather(int a) {
            int i = 0;
            while (a != parents[a]) {
                help[i++] = a;
                a = parents[a];
            }

            while (i > 0) {
                parents[help[--i]] = a;
           }
            return a;
        }

        public void union(int a, int b) {
            int f1 = findFather(a);
            int f2 = findFather(b);
            if (f1 != f2) {
                int big = size[f1] >= size[f2] ? f1 : f2;
                int small = big == f1 ? f2 : f1;
                //调整parents size sets
                parents[small] = big;
                size[big] += size[small];
                sets--;
            }
        }

        public int getSets() {
            return sets;
        }
    }
}
