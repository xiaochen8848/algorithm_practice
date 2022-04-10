package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/3/30 - 03 - 30 - 18:09
 * @Description: week1
 * @version: 1.0
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 打印一个字符串的全排列
 * 从左到右模型  对于任意一个index位置 决策有(str.length - index - 1)种
 * 对于当前index位置，该节点下的任意一个决策是从剩下的字符中选出一个放在index位置
 * 然后带着该决策下剩余的字符走下去
 */
public class PrintAllPermutations {
    public static List<String> permutation1(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        ArrayList<String> ans = new ArrayList<>();
        char[] chars = s.toCharArray();
        ArrayList<Character> rest = new ArrayList<>();
        for (char aChar : chars) {
            rest.add(aChar);
        }
        process1(rest, ans, "");
        return ans;
    }

    public static void process1(List<Character> rest, List<String> ans, String path) {
        if (rest.size() == 0) {
            ans.add(path);
            return;
        }

        for (int i = 0; i < rest.size(); i++) {
            // 当前决策选择字符character放在index位置然后带着剩下的字符走下去
            char c = rest.get(i);
            rest.remove(i);
            process1(rest,  ans, path + c);
            // 记得恢复现场
            rest.add(i, c);
        }

    }

    // 直接在原字符串上进行操作
    public static List<String> permutation2(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        ArrayList<String> ans = new ArrayList<>();
        char[] chars = s.toCharArray();

        process2(chars, 0, ans);
        return ans;
    }

    public static void process2(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(new String(str));
            return;
        }

        for (int i = index; i < str.length; i++) {
            swap(str, i, index);
            process2(str, index + 1, ans);

            swap(str, i, index);
        }
    }

    public static List<String> permutation3(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        ArrayList<String> ans = new ArrayList<>();
        char[] chars = s.toCharArray();

        process3(chars, 0, ans);
        return ans;
    }

    /**
     *当前位置是index，会从index...str.length-1位置上选择一个位置进行交换
     * 如果index...str.length - 1位置上有相同字符的时候，会导致有相同的决策
     */

    public static void process3(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(new String(str));
            return;
        }
        boolean[] visit = new boolean[256];
        for (int i = index; i < str.length; i++) {
            if (!visit[str[i]]) {
                visit[str[i]] = true;
                swap(str, i, index);
                process3(str, index + 1, ans);
                swap(str, i, index);
            }

        }
    }


    public static void swap(char[] str, int i, int j) {
        char temp = str[i];
        str[i] = str[j];
        str[j] = temp;
    }

    public static void main(String[] args) {
        String s = "121";
        System.out.println(permutation2(s).toString());
        System.out.println(permutation3(s).toString());
        System.out.println(permutation1(s).toString());
    }
}
