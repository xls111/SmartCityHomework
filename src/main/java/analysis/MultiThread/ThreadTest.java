package analysis.MultiThread;

import java.io.IOException;

public class ThreadTest {
    public static void main(String[] args) throws IOException {
        Thread thread1 = new Thread(new ThreadOperation("Aspect"));
        Thread thread2 = new Thread(new ThreadOperation("FlowDirection"));
        Thread thread3 = new Thread(new ThreadOperation("HoleFilling"));
        Thread thread4 = new Thread(new ThreadOperation("Slope"));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
}
