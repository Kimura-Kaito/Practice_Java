/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package practice001;

import java.util.ArrayList;

/**
 *
 * @author kimura-kaito
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello user!");
        System.out.println(
            "Create a list with 10 elements.");
        ArrayList<String> madelist = BasicMethod.makeList();
        boolean roop = true;
        while (roop) {
            System.out.println(
                "Please select [Search] or [Sort] to perform.");
            int n = BasicMethod.selectMethod();
            switch (n) {
                case 1 -> Methods.linearSort(madelist);
                case 2 -> Methods.bubbleSort(madelist);
                case 3 -> Methods.insertionSort(madelist);
                case 4 -> Methods.shellSort(madelist);
                case 5 -> Methods.quickSort(madelist);
                default -> roop = false;
            }
        }
        System.out.println("see you again!");
    }

    

    
}
