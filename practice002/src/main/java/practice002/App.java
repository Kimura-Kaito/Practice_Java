package practice002;

import java.util.ArrayList;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
        System.out.println("Hello user!");
        System.out.println(
            "Create a list with 10 elements.");
        ArrayList<String> madelist = BasicMethods.makeList();
        boolean roop = true;
        while (roop) {
            System.out.println(
                "Please select [Search] or [Sort] to perform.");
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
        System.out.println("see you again!");
    }
}
