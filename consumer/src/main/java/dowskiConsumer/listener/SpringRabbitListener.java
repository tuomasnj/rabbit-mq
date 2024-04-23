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
    @RabbitListener(queues = {"dowski.queue_1"}, concurrency = "3")
    public void handleMessage1(Student student){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format= simpleDateFormat.format(new Date());
        log.info(format + "====1111===="+ student);
    }

    @RabbitListener(queues = {"dowski.queue_2"}, concurrency = "3")
    public void handleMessage2(Student student){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format= simpleDateFormat.format(new Date());
        log.info(format + "====2222===="+ student);
    }

    @RabbitListener(queues = "simple.queue")
    public void handleSimpleQueue(String msg){
        log.error(msg);
    }
}
