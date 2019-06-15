package redis.conf;

import redis.service.RedisSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author wangxinxin
 */
@Slf4j
@Component
public class InitConfig implements CommandLineRunner {

    @Autowired
    private RedisSenderService service;

    @Override
    public void run(String... args) throws Exception {
        service.sendMessage();
    }
}
