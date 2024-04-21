package dowskiConsumer.listener;

import dowskiConsumer.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import cn.hutool.json.JSONObject;

@Component
@Slf4j
public class SpringRabbitListener {
    @RabbitListener(queues = {"simple.queue"})
    public void listenSimpleQueue(String message){
        log.info("++++++开始监听simple.queue队列++++++");
        log.info("接收到了msg,{}", message);
    }

    @RabbitListener(queues = "simple.queue1")
    public void listenSimpleQueue11(Student student) throws InterruptedException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format= simpleDateFormat.format(new Date());
        log.info(format + "========"+ student);
    }
}
