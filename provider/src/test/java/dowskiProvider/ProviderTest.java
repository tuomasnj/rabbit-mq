package dowskiProvider;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import dowskiProvider.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProviderTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("queue1")
    private Queue queue1;

    @Autowired
    private FanoutExchange exchange;

    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        //1、建立mq的连接
        ConnectionFactory factory = new ConnectionFactory();

        //配置连接参数
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/dowski");
        factory.setUsername("dowski");
        factory.setPassword("dowski");

        //创建连接
        Connection connection = factory.newConnection();

        //2、创建channel
        Channel channel = connection.createChannel();

        //3、创建队列
        String queueName = "simple.queue";
        channel.queueDeclare(queueName, false, false, false, null);

        //发送消息
        String message = "Hello, rabbit-mq!";
        channel.basicPublish("", queueName, null, message.getBytes());
        System.out.println("发送消息成功：【" + message + "】");

        //5、关闭连接
        channel.close();
        connection.close();
    }

    @Test
    public void sendMsg(){
        for (int i = 0; i <= 80; i ++) {
            rabbitTemplate.convertAndSend("simple.queue", "hello,rabbit..." + i);
            log.info("hello,rabbit..." + i);
        }
    }

    @Test
    /**
     * 测试并发性能
     */
    public void sendMultiMsg(){
        Student student = new Student();
        student.setAge(10);
        student.setName("呙明贤");
        student.setIdCard("324568744125025666");
        for (int i = 0; i <= 100; i ++) {
            rabbitTemplate.convertAndSend(exchange.getName(), "", student);
            log.info("hello,rabbit..." + i);
        }
    }
}
