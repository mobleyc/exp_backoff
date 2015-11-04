package cpm;


public class App {

    public static void main(String[] args) {
        ExponentialBackoff backoff = new ExponentialBackoff(1000, 6, 15000);

        System.out.println(backoff.getSleepTimeMs());
        System.out.println(backoff.getSleepTimeMs());
        System.out.println(backoff.getSleepTimeMs());
        System.out.println(backoff.getSleepTimeMs());
        System.out.println(backoff.getSleepTimeMs());
        System.out.println(backoff.getSleepTimeMs());
    }
}
