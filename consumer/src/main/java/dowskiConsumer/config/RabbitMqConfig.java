package dowskiConsumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * Direct模式
 * 交换机会接收到消息以及消息所对应的routingKey
 * 在转发消息时会根据队列Queue配置的key，将消息按照routingKey和key匹配的原则把message转发到队列中（队列必须要是和交换机绑定的）。
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

    private static final String EXCHANGE_NAME = "direct.exchange";
    private static final String QUEUE_NAME_1 = "direct.queue_1";
    private static final String QUEUE_NAME_2 = "direct.queue_2";

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
    public DirectExchange directExchange(){
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue1(){
        return new Queue(QUEUE_NAME_1,true, false, false);
    }

    @Bean
    public Queue queue2(){
        return new Queue(QUEUE_NAME_2, true, false, false);
    }

    @Bean
    public Binding bindingQueue1Key1(@Qualifier("directExchange") DirectExchange directExchange,@Qualifier("queue1") Queue queue1){
        //设置queue1的key为"key1"和"key2"
        return BindingBuilder.bind(queue1).to(directExchange).with("key1");
    }

    @Bean
    public Binding bindingQueue1Key2(@Qualifier("directExchange") DirectExchange directExchange,@Qualifier("queue1") Queue queue1){
        //设置queue1的key为"key1"和"key2"
        return BindingBuilder.bind(queue1).to(directExchange).with("key2");
    }

    @Bean
    public Binding bindingQueue2Key1(@Qualifier("directExchange") DirectExchange directExchange,@Qualifier("queue2") Queue queue2){
        //设置queue2的key为"key1"和"key3"
        return BindingBuilder.bind(queue2).to(directExchange).with("key1");
    }

    @Bean
    public Binding bindingQueue2Key3(@Qualifier("directExchange") DirectExchange directExchange,@Qualifier("queue2") Queue queue2){
        //设置queue2的key为"key1"和"key3"
        return BindingBuilder.bind(queue2).to(directExchange).with("key3");
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

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }
}
