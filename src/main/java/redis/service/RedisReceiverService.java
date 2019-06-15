package redis.service;

/**
 * @author wangxinxin
 * @date 2019-6-15 11:47:20
 */
public interface RedisReceiverService {
  /**
   * 消息接收方法
   */
  void receiveMessage(String message);

}
