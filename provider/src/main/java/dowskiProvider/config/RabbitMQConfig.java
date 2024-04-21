package dowskiProvider.config;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean(name = "specificConnectionFactory")
    public ConnectionFactory specificConnectionFactory() {
        // 配置特定的ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        // 设置连接参数
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("dowski");
        factory.setPassword("dowski");
        return factory;
    }
}
