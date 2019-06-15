package redis.conf;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * LettuceConnectionFactory 配置文件
 *
 * @author lizhengguang_vendor
 * @date 2019-1-14 20:01:52
 */
@Configuration
public class RedisConfig {

  @Value("${redis.redis-host}")
  private String redisHost;

  @Value("${redis.master-name}")
  private String masterName;

  @Value("${redis.redis-cluster}")
  private String redisCluster;

  @Value("${redis.redis-password}")
  private String password;

  private Gson gson = new Gson();
  private Logger logger = LoggerFactory.getLogger(RedisConfig.class);

  /**
   * StringRedisTemplate redis模板
   *
   * @return StringRedisTemplate
   */
  @Bean
  @ConditionalOnMissingBean(name = "redisTemplate")
  public StringRedisTemplate redisTemplate() {
    StringRedisTemplate redisTemplate = new StringRedisTemplate(connectionFactory());
    redisTemplate.setEnableTransactionSupport(true);
    return redisTemplate;
  }

  /**
   * Redis Connection Factory LettuceConnectionFactory
   *
   * @return LettuceConnectionFactory
   */
  @Bean
  public LettuceConnectionFactory connectionFactory() {

    logger.info("LettuceConnectionFactory is OK ! {}", redisHost);
    if (Boolean.parseBoolean(redisCluster)) {
      RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration();
      sentinelConfiguration.master(masterName);
      String[] hosts = redisHost.split(",");
      /** redis集群配置 */
      for (String host : hosts) {
        String[] config = host.split(":");
        sentinelConfiguration.sentinel(config[0], Integer.parseInt(config[1]));
      }
      sentinelConfiguration.setPassword(RedisPassword.of(password));
      logger.info("集群版，当前的redis的配置为：{}", gson.toJson(sentinelConfiguration));
      return new LettuceConnectionFactory(sentinelConfiguration);
    } else {
      String[] config = redisHost.split(":");
      logger.info("单机版，当前的redis的配置为：{}", config);
      return new LettuceConnectionFactory(
          new RedisStandaloneConfiguration(config[0], Integer.parseInt(config[1])));
    }
  }
}
