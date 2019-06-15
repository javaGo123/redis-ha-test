package redis.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.service.RedisReceiverService;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * MessageReceiver
 *
 * @author wangxinxin
 * @date 2019-6-15 11:47:20
 */
@Service
public class RedisReceiverServiceImpl implements RedisReceiverService {

  private Logger logger = LoggerFactory.getLogger(RedisReceiverServiceImpl.class);

  /** 消息接收方法 */
  @Override
  public void receiveMessage(String message){
    long receiveTime = System.currentTimeMillis();
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTimeInMillis(receiveTime);
    logger.info("message receivedTime {}", dateformat.format(gc.getTime()));
    logger.info("receive message：{}", message);
  }

}
