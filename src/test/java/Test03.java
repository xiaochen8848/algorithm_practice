import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * @Auther: chenLiuPing
 * @Date: 2022/4/2 - 04 - 02 - 12:24
 * @Description: PACKAGE_NAME
 * @version: 1.0
 */
public class Test03 {
    static class Info {
        int data;
        public Info(int data) {
            this.data = data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Info info = (Info) o;
            return data == info.data;
        }

        @Override
        public int hashCode() {
            return Objects.hash(data);
        }
    }
    @Test
    public void test() {
        HashMap<Info, Integer> map = new HashMap<>();
        map.put(new Info(10), 20);
        map.put(new Info(11), 21);
        map.put(new Info(10), 22);
        System.out.println(map.remove(new Info(10)));
        for (Info info : map.keySet()) {
            System.out.println(map.get(info));
        }

    }
}
