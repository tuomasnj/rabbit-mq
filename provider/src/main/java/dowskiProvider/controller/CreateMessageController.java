package dowskiProvider.controller;
import dowskiProvider.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RequestMapping
@RestController
@Slf4j
public class CreateMessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    @GetMapping("/send")
    public void testSimpleQueue(){
        String queueName = "simple.queue";
        String message = "hello, spring-amqp!";
        rabbitTemplate.convertAndSend(queueName, message);
        log.info("异步消息发送成功");
    }

    @GetMapping("/sendTest")
    public void dowskiDirectQueue(){
        Student student = new Student();
        student.setAge(10);
        student.setName("呙明贤");
        student.setIdCard("324568744125025666");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format= simpleDateFormat.format(new Date());
        rabbitTemplate.convertAndSend(directExchange.getName(), "",student);
        log.info("异步消息发送成功" + format);
    }
}
