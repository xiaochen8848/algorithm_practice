package test01;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/5 - 04 - 05 - 18:10
 * @Description: test01
 * @version: 1.0
 */
public class Test03 {
    public class GoogleInterviewQuestion12 {
        public int[] maxSlidingWindow(int[] nums, int k) {
            if (nums.length == 0 || k == 0) {
                return new int[0];
            }
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
    }
}
