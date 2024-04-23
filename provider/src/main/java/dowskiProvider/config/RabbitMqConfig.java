package dowskiProvider.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * Fanout模式
 * 交换机会将接收到的所有消息广播到所有绑定在该交换机上的队列。
 * 所有的队列都会存储被广播的消息用于被消费者接收
 */
public class RabbitMqConfig {
    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    private static final String EXCHANGE_NAME = "fanout.exchange";
    private static final String QUEUE_NAME_1 = "dowski.queue_1";
    private static final String QUEUE_NAME_2 = "dowski.queue_2";

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(this.host);
        connectionFactory.setPort(this.port);
        connectionFactory.setUsername(this.userName);
        connectionFactory.setPassword(this.password);
        connectionFactory.setVirtualHost(this.virtualHost);
        return connectionFactory;
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean(name = "queue1")
    public Queue queue1(){
        return new Queue(QUEUE_NAME_1,true, false, false);
    }

    @Bean(name = "queue2")
    public Queue queue2(){
        return new Queue(QUEUE_NAME_2, true, false, false);
    }

    @Bean
    public Binding binding1(FanoutExchange fanoutExchange,@Qualifier("queue1") Queue queue1){
        return BindingBuilder.bind(queue1).to(fanoutExchange);
    }

    @Bean
    public Binding binding2(FanoutExchange fanoutExchange,@Qualifier("queue2") Queue queue2){
        return BindingBuilder.bind(queue2).to(fanoutExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
