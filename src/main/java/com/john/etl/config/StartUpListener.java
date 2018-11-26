package com.john.etl.config;


import com.john.etl.unit.EntityEtlUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @Author: 张彦斌
 * @Date: 2018-11-26 14:54
 */
public class StartUpListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(StartUpListener.class);
    private ApplicationContext applicationContext = null;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        applicationContext = event.getApplicationContext();
        initEtlHandler();
    }

    public void initEtlHandler(){
        String[] EtlBean = applicationContext.getBeanNamesForType(EntityEtlUnit.class);
    }

    private boolean isHandler(Class beanType){
        return false;
    }

    private void detectHandlerMethods(){

    }
}
