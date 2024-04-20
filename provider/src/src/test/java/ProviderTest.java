import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProviderTest.class)
public class ProviderTest {
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

        //创建channel
        Channel channel = connection.createChannel();
    }
}
