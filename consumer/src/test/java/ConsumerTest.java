
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootTest(classes = ConsumerTest.class)
@RunWith(SpringRunner.class)
@Slf4j
public class ConsumerTest {
    @Test
    public void testReceiveMessage() throws IOException, TimeoutException {
        log.info("进入了testReceiveMessage方法");
        //1、消费者建立连接
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/dowski");
        connectionFactory.setUsername("dowski");
        connectionFactory.setPassword("dowski");
        Connection connection = connectionFactory.newConnection();
        //2、创建channel
        Channel channel = connection.createChannel();
        //3、创建队列
        String queueName = "simple.queue";
        channel.queueDeclare(queueName, false,false, false, null);

        //4、订阅消息
        channel.basicConsume(queueName, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body){
                //处理消息
                String message = new String(body);
                System.out.println("接收到消息：【" + message + "】");
            }
        });
        System.out.println("消息等待中。。。。");
    }
}
