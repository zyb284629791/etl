package com.john.etl.listener;


import com.john.etl.properties.EtlConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @Description 监听容器启动
 * @Author: Yb.Z
 * @Date: 2018/11/26.22:34
 * @Version：1.0
 */
@Component
public class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(StartUpListener.class);
    private ApplicationContext applicationContext = null;

    @Autowired
    private EtlConfigProperties etlConfigProperties;

    /**
     * 1、容器启动后获取所有etlunit，通过tablename来映射一个map。
     * 2、在mission消费的时候通过tablename字段匹配etlunit
     * 3、将mission封装后加如线程池
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        applicationContext = event.getApplicationContext();

    }


}
