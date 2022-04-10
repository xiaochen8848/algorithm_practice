package week1;

import java.util.Arrays;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/3/31 - 03 - 31 - 12:05
 * @Description: week1
 * @version: 1.0
 */

/*归并排序*/
public class MergeSort {
    public static void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        process(arr, 0, arr.length - 1);
    }

    public static void process(int[] arr, int L, int R) {
        if (L == R) {
            return;
        }

        int mid = L + ((R - L) >> 1);
        /*先使左半部分有序*/
        process(arr, L, mid);
        /*使右半部分有序*/
        process(arr, mid + 1, R);
        /*再使左半部分和右半部分整体有序*/
        merge(arr, L, mid, R);
    }

    private static void merge(int[] arr, int l, int mid, int r) {
        int[] help = new int[r - l + 1];
        int index = 0;
        int p1 = l;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= r) {
            help[index++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }

        while (p1 <= mid) {
            help[index++] = arr[p1++];
        }

        while (p2 <= r) {
            help[index++] = arr[p2++];
        }

        for (int i = 0; i < help.length; i++) {
            arr[l + i] = help[i];
        }
    }

    public static void main(String[] args) {
        int[] arr = {2, 4, 10, 36, 1, 14, 25};
        mergeSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
