package week2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/8 - 04 - 08 - 19:24
 * @Description: week2
 * @version: 1.0
 */

// 打印一个字符串的全部排列
public class PrintAllPermutations {


    public static void printAllPermutations(String s) {
        if (s == null || s.length() == 0) {
            return;
        }

        char[] str = s.toCharArray();
        ArrayList<Character> rest = new ArrayList<>();
        for (char c : str) {
            rest.add(c);
        }
        process(rest, "");

    }

    public static void printAllPermutations1(String s) {
        if (s == null || s.length() == 0) {
            return;
        }

        char[] str = s.toCharArray();
        ArrayList<Character> rest = new ArrayList<>();
        for (char c : str) {
            rest.add(c);
        }
        process1(rest, "");

    }

    public static void process(List<Character> rest, String path) {
        if (rest.size() == 0) {
            System.out.println(path);
            return;
        }

        for (int i = 0; i < rest.size(); i++) {
            Character character = rest.get(i);
            rest.remove(i);
            process(rest, path + character);
            rest.add(i, character);
        }
    }

    public static void process1(ArrayList<Character> rest, String path) {
        if (rest.size() == 0) {
            System.out.println(path);
            return;
        }

        for (Character character : rest) {
            ArrayList<Character> list = (ArrayList<Character>)rest.clone();
            list.remove(character);
            process1(list, path + character);
        }
    }

    // 直接在原数组做决策
    public static void process2(char[] str, int index, String path) {
        if (index == str.length) {
            System.out.println(path);
            return;
        }

        for (int i = index; i < str.length; i++) {
            // 把当前index要做的决策换到index位置，子节点会从index+1开始，这样父节点选了的决策，子节点就不会碰到
            // 同理，当前子节点走完之后，要换回去，因为自己节点的父节点这个决策还没有选
            swap(str, i, index);
            process2(str, index + 1, path + str[index]);
            swap(str, i, index);
        }
    }


    public static void swap(char[] str, int i, int j) {
        char temp = str[i];
        str[i] = str[j];
        str[j] = temp;
    }


    public static void main(String[] args) {
        String s  ="abc";
        printAllPermutations(s);
        System.out.println("============================");
        printAllPermutations1(s);
        System.out.println("============================");
        char[] str = s.toCharArray();
        process2(str, 0, "");

    }
}
