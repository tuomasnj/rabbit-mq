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
    @RabbitListener(queues = {"direct.queue_1"}, concurrency = "3")
    public void handleMessage1(Student student){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format= simpleDateFormat.format(new Date());
        log.info(format + "====direct.queue_1===="+ student);
    }

    @RabbitListener(queues = {"direct.queue_2"}, concurrency = "3")
    public void handleMessage2(Student student){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format= simpleDateFormat.format(new Date());
        log.info(format + "====direct.queue_2===="+ student);
    }
}
