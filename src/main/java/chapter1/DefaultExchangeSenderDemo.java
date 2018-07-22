package chapter1;

/**
 * Created by ilumer
 * Date : 2018/7/22
 * Time : 上午3:09
 */
public class DefaultExchangeSenderDemo {
    public static void sendToDefaultExchange(){
        Sender sender = new Sender();
        sender.initialize();
        sender.send("Test message.");
    }

    public static void main(String[] args){
        sendToDefaultExchange();
    }
}
