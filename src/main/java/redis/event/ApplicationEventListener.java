package redis.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * @Author: wangxinxin
 * @DATE: 2019/6/15 23:00
 * Spring 事件监听
 */
@Component
public class ApplicationEventListener implements ApplicationListener {
    private final Logger logger = LoggerFactory.getLogger(ApplicationEventListener.class);
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ContextClosedEvent){
            logger.info(event.getClass().getSimpleName()+" 事件已发生！");
        }else if(event instanceof ContextRefreshedEvent){
            logger.info(event.getClass().getSimpleName()+" 事件已发生！");
        }else if(event instanceof ContextStartedEvent){
            logger.info(event.getClass().getSimpleName()+" 事件已发生！");
        }else if(event instanceof ContextStoppedEvent){
            logger.info(event.getClass().getSimpleName()+" 事件已发生！");
        }else if(event instanceof ServletWebServerInitializedEvent){
            logger.info(event.getClass().getSimpleName()+" 事件已发生！");
        }else if(event instanceof ApplicationStartedEvent){
            logger.info(event.getClass().getSimpleName()+" 事件已发生！");
        }else if(event instanceof ApplicationFailedEvent){
            logger.info(event.getClass().getSimpleName()+" 事件已发生！");
        }else{
            logger.info("有其它事件发生:"+event.getClass().getName());
        }
    }
}
