package topic;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/2 - 04 - 02 - 16:07
 * @Description: topic
 * @version: 1.0
 */

import java.util.*;

/**
 * /**
 *  * 给定一个整数数组和一个数字k，其中1<=k<=数组的长度，计算长度为k的每个子数组的最大值。
 *  * 例如，给定数组=[10, 5, 2, 7, 8, 7]和k=3，我们应该得到。[10, 7, 8, 8], 因为:
 *  * 10 = max(10, 5, 2)
 *  * 7 = max(5, 2, 7)
 *  * 8 = max(2, 7, 8)
 *  * 8 = max(7, 8, 7)
 *  * 在O(n)时间和O(k)空间内做到这一点。你可以就地修改输入数组，你不需要存储结果。你可以在计算时简单地将它们打印出来。
 *
 */
public class Code01 {
    // 架构的方法
    public static int[] maxSlidingWindow(int[] nums, int k) {
//        if (nums.length == 0 || k == 0) {
//            return new int[0];
//        }
        if (nums == null || k > nums.length || k <= 0)
            return null;
        int[] results = new int[nums.length - k + 1];
        Deque<Integer> slidingWindow = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            if (slidingWindow.peek() != null && (i - slidingWindow.peek() >= k)) {
                slidingWindow.removeFirst();
            }
            while (!slidingWindow.isEmpty() && nums[slidingWindow.getLast()] <= nums[i]) {
                slidingWindow.removeLast();
            }
            slidingWindow.addLast(i);
            if (i >= k - 1) {
                results[i - k + 1] = nums[slidingWindow.peek()];
            }
        }
        return results;
    }




    // 我的方法
    public static int[] kSubArrayMax(int[] arr, int k) {
        if (arr == null || k > arr.length || k <= 0)
            return null;
        int i = 0;
        int[] ans = new int[arr.length - k + 1];
       // ArrayList<Integer> list = new ArrayList<>();
        HeapGreater heapGreater = new HeapGreater(arr, k);
        for (int end = k; end < arr.length; end++) {
         //  System.out.print(heapGreater.peek().data + "  ");
            ans[i++] = heapGreater.peek().data;
            heapGreater.push(new Info(arr[end]));
        }
      //  System.out.println(heapGreater.peek().data);
        ans[i++] = heapGreater.peek().data;
        return ans;
    }

    // 踩坑 要包装一下，HashMap 如果是基本数据类型，key相同，value直接覆盖了
    static class Info {
        int data;
        public Info(int data) {
            this.data = data;
        }
    }

    static class HeapGreater {
        private ArrayList<Info> heap;
        private ArrayList<Info> heapCopy;
        private HashMap<Info, Integer> indexMap;  // 记录堆中每个节点在数组中的位置 key:节点 value：位置
        private int heapSize;
        public HeapGreater(int[] arr, int k) {
            heap = new ArrayList<>();
            heapCopy = new ArrayList<>();
            indexMap = new HashMap<>();
            for (int i = 0; i < k; i++) {
                Info info = new Info(arr[i]);
                heap.add(info);
                heapCopy.add(info);
                indexMap.put(info, i);
            }
            heapSize = k;
            for (int i = heapSize - 1; i >= 0; i--) {
                siftDown(i);
            }
        }

        public void push(Info info) {
            heap.add(info);
            heapCopy.add(info);
            indexMap.put(info, heapSize);
            siftUp(heapSize++);
        }

        public Info peek() {
            Info ans = heap.get(0);
            Info startElement = heapCopy.get(0);
            remove(startElement);
            heapCopy.remove(0);
            return ans;
        }

        /*在heap中把元素info给删除*/
        public void remove(Info info) {
            Info replace = heap.get(heapSize - 1);
            int index = indexMap.get(info);
            indexMap.remove(info);
            heap.remove(--heapSize);
            if (replace != info) {  // 当replace==i 时表名要删除的元素就是最后一个元素
                heap.set(index, replace);
                indexMap.put(replace, index);
                // index位置的元素变了，重新调整堆
                siftDown(index);
                siftUp(index);
            }

        }

        private void siftUp(int index) {
            while (heap.get(index).data > heap.get((index - 1) / 2).data) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            }
        }

        public void siftDown(int index) {
            int left = (index << 1) + 1;
            while (left < heapSize) {
                int largest = left + 1 < heapSize && heap.get(left + 1).data > heap.get(left).data ? left + 1 : left;
                if (heap.get(largest).data <= heap.get(index).data)
                    break;
                swap(largest, index);
                index = largest;
                left = (index << 1) + 1;
            }
        }

        private void swap(int i, int j) {
            Info o1 = heap.get(i);
            Info o2 = heap.get(j);
            heap.set(i, o2);
            heap.set(j, o1);
            indexMap.put(o1, j);
            indexMap.put(o2, i);
        }



    }

    // for test
    public static List<Integer> test(int[] arr, int k) {
        if (arr == null || arr.length == 0 | k > arr.length || k <= 0)
            return null;
        ArrayList<Integer> list = new ArrayList<>();
        for (int start = 0, end = k; end <= arr.length; start++, end++) {
            list.add(max(arr, start, end));
        }
        return list;
    }

    public static int max(int[] arr, int l, int r) {
        int max = Integer.MIN_VALUE;
        for (int i = l; i < r; i++) {
            max = Math.max(max, arr[i]);
        }
        return max;
    }

    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxSize)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxValue) - (int) (Math.random() * maxValue);
        }
        return arr;
    }

    public static boolean isEqual(List<Integer> list1, List<Integer> list2) {
        if (list1 == null && list2 == null) {
            return true;
        }
        if (list1 == null || list2 == null || list1.size() != list2.size())
            return false;
        for (int i = 0; i < list1.size(); i++) {
            if (!list1.get(i).equals(list2.get(i)))
                return false;
        }
        return true;
    }

    public static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1 == null && arr2 == null) {
            return true;
        }
        if (arr1 == null || arr2 == null || arr1.length != arr2.length)
            return false;
        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i])
                return false;
        }
        return true;
    }


    public static void main(String[] args) {

        int testTimes = 5000000;
        int maxSize = 50;
        int maxValue = 100;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * maxSize);
            int[] ans1 = kSubArrayMax(arr, k); // 我的
            int[] ans2  = maxSlidingWindow(arr, k); // 架构的
            if (!isEqual(ans1, ans2)) {
                System.out.println("arr: " + Arrays.toString(arr));
                System.out.println("k: " + k);
                System.out.println("架构的: " + Arrays.toString(ans2));
                System.out.println("我的:" + Arrays.toString(ans1));
                break;
            }

        }
        System.out.println("测试结束");
//        int[] arr = {10, 5, 2, 7, 8, 7, 20, 19, 1, 2, 3};
//        kSubArrayMax(arr, 3);
//        System.out.println(test(arr, 3).toString());
    }
}
