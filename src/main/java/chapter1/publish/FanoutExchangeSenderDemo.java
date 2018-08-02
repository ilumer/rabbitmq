package chapter1.publish;

import chapter1.Sender;

/**
 * Created by ilumer
 * Date : 2018/8/3
 * Time : 上午12:16
 */
public class FanoutExchangeSenderDemo {
    private static final String FANOUT_EXCHANGE_TYPE = "fanout";

    public static void sendToFanoutExchange(String exchange){
        Sender sender = new Sender();
        sender.initialize();
        sender.send(exchange, FANOUT_EXCHANGE_TYPE, "Test message.");
        sender.destroy();
    }

    public static void main(String[] args) {
        sendToFanoutExchange("pubsub_exchange");
    }
}
