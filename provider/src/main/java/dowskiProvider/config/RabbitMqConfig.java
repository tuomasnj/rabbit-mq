package dowskiProvider.config;

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
 * Topic模式
 * Direct的进阶模式。交换机接收到消息时，会根据routingKey的值匹配对应的队列
 * * 表示一个单词  #表示任意个数量的单词
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

    private static final String EXCHANGE_NAME = "topic.exchange";
    private static final String TOPIC_QUEUE1 = "topic.queue_1";
    private static final String TOPIC_QUEUE2 = "topic.queue_2";

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
    public TopicExchange topicExchange(){
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue queue1(){
        return new Queue(TOPIC_QUEUE1,true, false, false);
    }

    @Bean
    public Queue queue2(){
        return new Queue(TOPIC_QUEUE2, true, false, false);
    }

    @Bean
    public Binding bindingQueue1(@Qualifier("topicExchange") TopicExchange topicExchange, @Qualifier("queue1")Queue queue1){
        //设置queue1的key为"marketing.*",可以接收所有marketing相关的消息
        return BindingBuilder.bind(queue1).to(topicExchange).with("marketing.*");
    }

    @Bean
    public Binding bindingQueue2(@Qualifier("topicExchange")TopicExchange topicExchange, @Qualifier("queue2") Queue queue2){
        //设置queue1的key为"*.user",可以接收所有user相关的消息
        return BindingBuilder.bind(queue2).to(topicExchange).with("*.user");
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
