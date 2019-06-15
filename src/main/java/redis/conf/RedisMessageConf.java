package redis.conf;

import redis.service.impl.RedisReceiverServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author lizhengguang_vendor
 * @date 2019-1-30 17:15:12
 */
@Configuration
public class RedisMessageConf {

  @Value("${redis.channel}")
  private String channel;

  @Autowired private RedisReceiverServiceImpl messageReceiver;
  @Autowired private LettuceConnectionFactory connectionFactory;

  Logger logger = LoggerFactory.getLogger(RedisMessageConf.class);

  /**
   * redisMessageListenerContainer
   *
   * @return
   */
  @Bean
  RedisMessageListenerContainer redisMessageListenerContainer() {
    RedisMessageListenerContainer messageListenerContainer = new RedisMessageListenerContainer();
    messageListenerContainer.setConnectionFactory(connectionFactory);
    messageListenerContainer.addMessageListener(listenerAdapter(), new PatternTopic(channel));
    return messageListenerContainer;
  }

  /**
   * 消息监听器适配器，绑定消息处理器，利用反射技术调用消息处理器的业务方法
   *
   * @return MessageListenerAdapter
   */
  @Bean
  public MessageListenerAdapter listenerAdapter() {
    logger.info("MessageListenerAdapter is OK!");
    return new MessageListenerAdapter(messageReceiver, "receiveMessage");
  }
}
