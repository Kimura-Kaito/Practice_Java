package practice002;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 標準入力に関するメソッド
 */
public class BasicMethods {

    /**
     * 標準入力をArrayList<String>にまとめるメソッド
     * @return 入力をまとめたリスト
     */
    static ArrayList<String> makeList() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            String w = null;
            while (w == null) { 
                System.out.printf(
                    "文字を入力してください。%d番目 : ", i);
                Scanner sc = new Scanner(System.in);
                try {
                    w = sc.next();
                } catch (Exception e) {
                    w = null;
                }
            }
            list.add(w);
        }
        return list;
    }

    /**
     * 標準入力で実行するメソッドをを選択させるメソッド
     * @return メソッド番号
     */
    static int selectMethod() {
        var method = List.of("linear search",
                             "bubble sort",
                             "insertion sort",
                             "shell sort",
                             "quick sort",
                             "quit");
        int n = -1;
        for (int i = 0; i < method.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, method.get(i));
        }
        while (n == -1) { 
            System.out.print("実行番号 : ");
            Scanner sc = new Scanner(System.in);
            try {
                n = sc.nextInt();
                if (n < 1 || n > method.size()) {
                    throw new Exception();
                }
            } catch (Exception e) {
                System.out.println("正しい数字を入力してください。");
                n = -1;
            }
        }
        return n;
    }

    /**
     * 検索する文字を標準入力から取得するメソッド
     * @return 検索する文字
     */
    static String searchWord() {
        String w = null;
        while (w == null) { 
            System.out.print(
                "検索する文字を入力してください。 : ");
            Scanner sc = new Scanner(System.in);
            try {
                w = sc.next();
            } catch (Exception e) {
                System.out.println("");
                w = null;
            }
        }
        return w;
    }
}
