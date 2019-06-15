package redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import redis.event.GracefulShutdownTomcat;

/**
 * RedisTestApplication
 *
 * @author wangxinxin
 * @date 2019-6-15 11:05:51
 */

@SpringBootApplication
@EnableScheduling
public class RedisTestApplication {
  public static void main(String[] args) {
    SpringApplication.run(RedisTestApplication.class, args);
  }

  @Autowired
  private GracefulShutdownTomcat gracefulShutdownTomcat;

  @Bean
  public ServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
    tomcat.addConnectorCustomizers(gracefulShutdownTomcat);
    return tomcat;
  }

}

