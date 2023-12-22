package practice002;

import java.util.ArrayList;

public class App {
    public static void main( String[] args ) {
        System.out.println("こんにちは！");
        System.out.println(
            "10個の要素を入力してください。");
        ArrayList<String> madelist = BasicMethods.makeList();
        boolean roop = true;
        while (roop) {
            System.out.println(
                "実行する「検索」または「並び替え」を選んでください。");
            int n = BasicMethods.selectMethod();
            switch (n) {
                case 1 -> Methods.linearSearch(madelist);
                case 2 -> Methods.bubbleSort(madelist);
                case 3 -> Methods.insertionSort(madelist);
                case 4 -> Methods.shellSort(madelist);
                case 5 -> Methods.quickSort(madelist);
                default -> roop = false;
            }
        }
        System.out.println("さようなら。");
    }
}
