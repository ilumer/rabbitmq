package chapter1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by ilumer
 * Date : 2018/7/22
 * Time : 上午2:48
 */
public class CompetingReiver {
    private final static String QUEUE_NAME = "event_queue";
    private final static Logger LOGGER = LoggerFactory.getLogger(Sender.class);
    private Connection connection = null;
    private Channel channel = null;

    public void initialize(){
        try{
            ConnectionFactory factory  = new ConnectionFactory();
            factory.setHost("localhost");
            connection = factory.newConnection();
            channel = connection.createChannel();
        }catch (IOException e){
            LOGGER.error(e.getMessage(), e);
        }
    }

    public String receive(){
        if (channel == null){
            initialize();
        }
        String message = null;
        try{
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            QueueingConsumer consumer = new QueueingConsumer(channel);
            channel.basicConsume(QUEUE_NAME, true,consumer);
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            message = new String(delivery.getBody());
            LOGGER.info("message revived:"+message);
            return message;
        }catch (IOException | InterruptedException ex){
            LOGGER.error(ex.getMessage(),ex);
        }
        return message;
    }

    public void destroy(){
        if (connection != null){
            try{
                connection.close();
            }catch (IOException ex){
                LOGGER.warn(ex.getMessage(),ex);
            }
        }
    }
}
