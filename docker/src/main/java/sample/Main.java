package sample;

public class Main {
    public static void main(String[] args) {
        for (int i=0;i<100;i++) {
            System.out.println("Main.main "+i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignore) {
            }
        }
    }
}
