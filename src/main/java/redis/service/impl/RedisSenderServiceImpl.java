package redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import redis.service.RedisSenderService;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import static java.lang.Thread.sleep;

/**
 * RedisSender
 *
 * @author wangxinxin
 * @date 2019-6-15 11:40:01
 */
@Service
public class RedisSenderServiceImpl implements RedisSenderService {

  /** StringRedisTemplate */
  @Autowired private StringRedisTemplate redisTemplate;

  @Value("${redis.channel}")
  private String channel;
  @Override
  public void sendMessage() {
      while(true) {
          try{
            sleep(1000);
          }catch (Exception e){
          }
          long receiveTime = System.currentTimeMillis();
          SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
          GregorianCalendar gc = new GregorianCalendar();
          gc.setTimeInMillis(receiveTime);
          String historyInfo = "CommandLineRunner  " + dateformat.format(gc.getTime());
          redisTemplate.convertAndSend(channel, historyInfo);
      }
  }



//    @Scheduled(fixedRate = 2000)
//    public void scheduleSend() {
//        long time = System.currentTimeMillis();
//        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        GregorianCalendar gc = new GregorianCalendar();
//        gc.setTimeInMillis(time);
//        String msg = "scheduleSend  "+ dateformat.format(gc.getTime());
//        redisTemplate.convertAndSend(channel, msg);
//    }
}
