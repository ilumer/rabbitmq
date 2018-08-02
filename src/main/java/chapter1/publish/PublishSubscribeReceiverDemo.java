package chapter1.publish;

/**
 * Created by ilumer
 * Date : 2018/8/3
 * Time : 上午12:31
 */
public class PublishSubscribeReceiverDemo {
    public static void main(String[] args) throws Exception {
        final PublishSubscribeReceiver receiver1 = new PublishSubscribeReceiver();
        final PublishSubscribeReceiver receiver2 = new PublishSubscribeReceiver();
        receiver1.initialize();
        receiver2.initialize();
        Thread thread = new Thread(()-> receiver1.receive("pubsub_queue1"));
        Thread thread1 = new Thread(()-> receiver2.receive("pubsub_queue2"));
        thread.start();
        thread1.start();
        thread.join();
        thread1.join();
        receiver1.destory();
        receiver2.destory();
    }
}
