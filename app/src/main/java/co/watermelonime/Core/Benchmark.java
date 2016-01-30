package co.watermelonime.Core;

public class Benchmark {
    public static void run() {
        long t;
        for(int i = 0; i < 100; i++){
            t = System.nanoTime();
            iter();
            System.out.println(System.nanoTime() - t);
        }
    }

    public static void iter() {

    }
}
