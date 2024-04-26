package dowskiProvider;

import dowskiProvider.config.MessageSender;
import dowskiProvider.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProviderTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageSender messageSender;

    @Test
    public void testMessageSender(){
        Student s = new Student("marketing.user", "3211145663200", 0);
        messageSender.sendMsg(s);
    }
}
