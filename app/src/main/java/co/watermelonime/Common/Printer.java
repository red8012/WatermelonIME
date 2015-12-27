package co.watermelonime.Common;

public class Printer {
    public static void p(String s, int... numbers){
        System.out.print(s + " ");
        for (int i: numbers) {
            System.out.print(i);
            System.out.print(", ");
        }
        System.out.println();
    }
}
