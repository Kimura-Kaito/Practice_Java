package practice002;

import java.util.ArrayList;

/**
 * 検索と並び替えに関するメソッドをまとめたクラス
 */
public class Methods {

    /**
     * 線形探索メソッド
     * @param original リスト
     */
    static void linearSearch(ArrayList<String> original) {
        var list = new ArrayList<String>(original);
        String w = BasicMethods.searchWord();
        list.add(w);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(w) && i == list.size() - 1) {
                System.out.printf(
                    "%s は見つかりませんでした。\n",
                     w);
                } else if (list.get(i).equals(w)) {
                    System.out.printf(
                        "%s を %d 番目に見つけました。\n",
                        w,
                        i + 1);
                        break;
                }
            }
            System.out.println("");
        }
        
    /**
     * バブルソートメソッド
     * @param original リスト
     */
    static void bubbleSort(ArrayList<String> original) {
        var list = new ArrayList<String>(original);
        boolean sortFlag = true;
        int count = 0;
        while (sortFlag) {
            sortFlag = false;
            System.out.printf("%2d回目 : %s\n", count, list);
            for (int i = 0; i < list.size() - 1; i++) {
                var front = list.get(i).toCharArray();
                var back = list.get(i + 1).toCharArray(); 
                if (front[0] > back[0]) {
                    list.add(i, list.get(i + 1));
                    list.remove(i + 2);
                    sortFlag = true;
                }
            }
            count++;
       }
       System.out.println("バブルソートが完了しました。");
    }

    /**
     * 挿入ソートメソッド
     * @param original リスト
     */
    static void insertionSort(ArrayList<String> original) {
        ArrayList<String> list = insertionBase(original);
        System.out.printf("結　果 : %s\n", list);
        System.out.println("挿入ソートが完了しました。");
    }

    /**
     * 挿入ソートのアルゴリズムメソッド
     * @param original リスト
     * @return ソートしたリスト
     */
    static ArrayList<String> insertionBase(ArrayList<String> original) {
        var list = new ArrayList<String>(original);
        for (int i = 1; i < list.size(); i++) {
            System.out.printf("%2d個目 : %s\n", i, list);
            for (int j = 0; j < i; j++) {
                var front = list.get(j).toCharArray();
                var back = list.get(i).toCharArray(); 
                if (front[0] > back[0]) {;
                    list.add(j, list.get(i));
                    list.remove(i + 1);
                }
            }
        }
        return list;
    }

    /**
     * シェルソートメソッド
     * @param original リスト
     */
    static void shellSort(ArrayList<String> original) {
        var list = new ArrayList<String>(original);
        int h = list.size() / 2;
        System.out.printf("　　元 : %s\n", list);
        while (h > 0) {
            for (int i = 0; i < h; i++) {
                var poolList = new ArrayList<String>();
                for (int j = i; j < list.size(); j += h) {
                    poolList.add(list.get(j));
                }
                poolList = insertionBase(poolList);
                int count = 0;
                for (int j = i; j < list.size(); j += h) {
                    list.set(j, poolList.get(count));
                    count++;
                }
            }
            System.out.printf("h = %2d : %s\n", h, list);
            h /= 2;
        }
        System.out.println("シェルソートが完了しました。");
    }

    /**
     * クイックソートメソッド
     * @param original リスト
     */
    static void quickSort(ArrayList<String> original) {
        System.out.printf("元データ : %s\n", original);
        quickBase(original);
        System.out.println("クイックソートが完了しました。");
    }

    /**
     * 分割統治アルゴリズムメソッド
     * @param original リスト
     * @return ソートしたリスト
     */
    static ArrayList<String> quickBase(ArrayList<String> original) {
        var pivot = original.get(0);
        var divideF = new ArrayList<String>();
        var divideB = new ArrayList<String>();
        var ret = new ArrayList<String>();
        for (int i = 1; i < original.size(); i++) {
            var pivotC = pivot.toCharArray();
            var iC = original.get(i).toCharArray(); 
            if (pivotC[0] > iC[0]) {
                divideF.add(original.get(i));
            } else {
                divideB.add(original.get(i));
            }

        }
        if (divideF.size() != 0) {
            System.out.printf("%s 未満 : %s\n", pivot, divideF);
            divideF = quickBase(divideF);
        }
        if (divideB.size() != 0) {
            System.out.printf("%s 以上 : %s\n", pivot, divideB);
            divideB = quickBase(divideB);
        }
        ret.addAll(divideF);
        ret.add(pivot);
        ret.addAll(divideB);
        System.out.printf("分割統治完了 : %s\n", ret);
        return ret;
    }
}
