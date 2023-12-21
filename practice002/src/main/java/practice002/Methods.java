package practice002;

import java.util.ArrayList;
import java.util.Scanner;

public class Methods {

    static void linearSearch(ArrayList<String> list) {
        String w = null;
        while (w == null) { 
            System.out.println(
                "Please enter the characters to search. : ");
            Scanner sc = new Scanner(System.in);
            try {
                w = sc.next();
            } catch (Exception e) {
                System.out.println("");
                w = null;
            }
        }
        list.add(w);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(w) && i == list.size() - 1) {
                System.out.printf(
                    "%s could not be found.\n",
                     w);
                } else if (list.get(i).equals(w)) {
                    System.out.printf(
                        "%s was in %d th place.\n",
                        w,
                        i + 1);
                        break;
                }
            }
            System.out.println("");
        }
        

    static void bubbleSort(ArrayList<String> list) {
       
    }

    static void insertionSort(ArrayList<String> list) {

    }

    static void shellSort(ArrayList<String> list) {

    }

    static void quickSort(ArrayList<String> list) {

    }
}
