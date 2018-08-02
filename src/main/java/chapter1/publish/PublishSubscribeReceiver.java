package chapter1.publish;

import chapter1.Sender;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by ilumer
 * Date : 2018/8/2
 * Time : 下午11:40
 */
public class PublishSubscribeReceiver {
    private final static String EXCHANGE_NAME = "pubsub_exchange";
    private final static Logger LOGGER = LoggerFactory.getLogger(PublishSubscribeReceiver.class);
    private Channel channel = null;
    private Connection connection = null;

    public void initialize(){
        try{
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();
        }catch (IOException ex){
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public String receive(String queue){
        if (channel == null){
            initialize();
        }
        String message = null;
        try{
            // exchange queue 没有声明durable broker重启后会丢失
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.queueDeclare(queue, false, false, false, null);
            // channel 将QUEUE 与 EXCHANGE 绑定 因为是fanout exchange 所以不需要routingKey
            channel.queueBind(queue,EXCHANGE_NAME , "");
            // 通过channel 获取一个消息的消费者
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(queue,true ,consumer);
            // 从channel中获取消息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            message = new String(delivery.getBody());
            LOGGER.info("Message received: " + message);
            return message;
        }catch (IOException | InterruptedException ex){
            LOGGER.error(ex.getMessage(), ex);
        }
        return message;
    }

    public void destory() {
        try {
            if (connection != null){
                connection.close();
            }
        }catch (IOException ex){
            LOGGER.warn(ex.getMessage(), ex);
        }
    }
}
