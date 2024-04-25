package dowskiProvider.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static final String routingKey = "key1";

    public static final String exchange = "direct.exchange";

    public void sendMsg(Object object){
        rabbitTemplate.convertAndSend(exchange, routingKey, object);
    }
}
