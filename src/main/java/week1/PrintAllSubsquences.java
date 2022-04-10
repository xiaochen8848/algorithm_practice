package week1;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/3/30 - 03 - 30 - 18:01
 * @Description: week1
 * @version: 1.0
 */


import java.util.ArrayList;
import java.util.List;

/**
 * 打印一个字符串的所有子序列
 * 从左到右的模型 对于每个index位置，有两种决策：要/不要
 * 当index==str.length时，一个最小分支完成
 */
public class PrintAllSubsquences {

    public static List<String> subs(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }

        ArrayList<String> ans = new ArrayList<>();
        process(s.toCharArray(), 0, "", ans);
        return ans;
    }

    public static void process(char[] str, int index, String path, List<String> ans) {
        if (index == str.length) {
            ans.add(path);
            return;
        }

        // 决策：选择当前index位置的字符
        process(str, index +1, path + str[index], ans);
        // 决策：不选择当前index位置的字符
        process(str, index + 1, path, ans);
    }

    public static void main(String[] args) {
        String s = "abc";
        System.out.println(subs(s).toString());
    }

}
