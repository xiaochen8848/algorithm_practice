package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/1 - 04 - 01 - 14:23
 * @Description: week1
 * @version: 1.0
 */

import java.beans.PropertyEditor;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 给定一个字符串str，给定一个字符串类型的数组arr，出现的字符都是小写英文
 * arr每一个字符串，代表一张贴纸，你可以把单个字符剪开使用，目的是拼出str来
 * 返回需要至少多少张贴纸可以完成这个任务（所有决策中最优）
 * 例子：str= "babac"，arr = {"ba","c","abcd"}ba + ba + c  3  abcd + abcd 2  abcd+ba 2所以返回2
 */
public class StickersToSpellWord {
    public static int number(String s, String[] arr) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int ans = process(s, arr);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    // 对于字符串str 从数组arr中选出最少的贴纸
    public static int process(String str, String[] arr) {
        if (str.length() == 0) {
            return 0;
        }
        // 对于当前节点，可以做的决策就是arr中所有能消掉str中字符的
        int min = Integer.MAX_VALUE;
        for (String s : arr) {
            String rest = pick(str, s);
            if (rest.length() != str.length()) {
                min = Math.min(min, process(rest, arr));
            }
        }
        return min == Integer.MIN_VALUE ? min : min + 1;
    }

    private static String pick(String str, String s) {
        char[] chars = s.toCharArray();
        char[] chars1 = str.toCharArray();
        LinkedList<Character> list = new LinkedList<>();
        for (char c : chars1) {
            list.add(c);
        }
        for (Character aChar : chars) {
            list.remove(aChar);
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Character character : list) {
            stringBuilder.append(character);
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        String str = "babac";
        String[] arr = {"ba","c","abcd"};
        System.out.println(number(str, arr));
    }
}
