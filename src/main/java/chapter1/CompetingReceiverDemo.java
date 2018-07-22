package chapter1;

/**
 * Created by ilumer
 * Date : 2018/7/22
 * Time : 上午3:46
 */
public class CompetingReceiverDemo {
    public static void main(String[] args) throws InterruptedException{
        final CompetingReiver reiver = new CompetingReiver();
        reiver.initialize();
        final CompetingReiver reiver1 = new CompetingReiver();
        reiver1.initialize();

        Thread t1 = new Thread(()-> System.out.println(reiver.receive()));
        Thread t2 = new Thread(()-> System.out.println(reiver1.receive()));

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        reiver.destroy();
        reiver1.destroy();
    }
}
