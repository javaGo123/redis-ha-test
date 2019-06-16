package redis.event;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.ProtocolHandler;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import redis.App;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangxinxin
 * @DATE: 2019/6/15 23:00
 * 优雅关闭 Spring Boot tomcat
 */

@Component
public class GracefulShutdownTomcat implements TomcatConnectorCustomizer, ApplicationListener<ContextClosedEvent> {
    private final Logger logger = LoggerFactory.getLogger(GracefulShutdownTomcat.class);
    private volatile Connector connector;

    @Value("${shutdown.waitTime}")
    private Long waitTime;

    @Value("${shutdown.maxWaitTime}")
    private Long maxWaitTime;

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info(event.getClass().getSimpleName()+" 事件已发生！");
        if (this.connector == null) {
            logger.info("Tomcat connector has'nt been ready!");
        }
        ProtocolHandler protocolHandler = this.connector.getProtocolHandler();
        try {
            Executor executor = protocolHandler.getExecutor();
            protocolHandler.stop();//执行关闭流程，此关闭流程中不会关闭外部指定的executer
            //非tomcat内置的executor，建议单独关闭
            if (executor instanceof ThreadPoolExecutor) {
                try {
                    ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                    int i = 0;
                    while (i < maxWaitTime && !threadPoolExecutor.isShutdown()) {
                        threadPoolExecutor.shutdown();
                        if (!threadPoolExecutor.awaitTermination(waitTime, TimeUnit.MILLISECONDS)) {
                            i += waitTime;
                            logger.info("Tomcat thread pool will be shutdown,total waiting-time=[" + i + "ms]");
                        }
                    }
                    if (!threadPoolExecutor.isShutdown()) {
                        threadPoolExecutor.shutdownNow();
                        if (!threadPoolExecutor.awaitTermination(waitTime, TimeUnit.MILLISECONDS)) {
                            logger.error("Tomcat thread pool did not terminate,shutdown failed!");
                        }
                    } else {
                        logger.info("Tomcat thread pool has been shutdown successfully!");
                    }
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            logger.error("Tomcat shutdown error!",e);
        }finally {
            SpringApplication.run(App.class, "");
        }
    }

}
