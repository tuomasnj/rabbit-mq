package dowskiProvider;

import dowskiProvider.config.MessageSender;
import dowskiProvider.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private MessageSender messageSender;

    @Autowired
    private DirectExchange exchange;

    /**
     * 发送给两个队列
     */
    @Test
    public void sendDirectMsg(){
        Student student = new Student();
        student.setAge(18);
        student.setName("小王");
        student.setIdCard("12235444412666325");
        rabbitTemplate.convertAndSend(exchange.getName(), "key1", student);
    }

    /**
     * 发送给queue1
     * key设置为key2
     */
    @Test
    public void sendDirectMsg1(){
        Student student = new Student();
        student.setAge(18);
        student.setName("queue1");
        student.setIdCard("12235444412666325");
        rabbitTemplate.convertAndSend(exchange.getName(), "key2", student);
    }

    /**
     * 发送给queue2
     * key设置为key3
     */
    @Test
    public void sendDirectMsg2(){
        Student student = new Student();
        student.setAge(0);
        student.setName("queue2");
        student.setIdCard("12235444412666325");
        rabbitTemplate.convertAndSend(exchange.getName(), "key3", student);
    }

    @Test
    public void testMessageSender(){
        Student s = new Student("三毛", "3211145663200", 0);
        messageSender.sendMsg(s);
    }
}
