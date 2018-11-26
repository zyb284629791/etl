package com.john.etl.unit;

import com.sun.javafx.binding.StringFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 模仿spring mvc中requestmappinghandlermapping处理controller的方式来处理etlunit
 * @Author: Yb.Z
 * @Date: 2018/11/26.22:34
 * @Version：1.0
 */
@Component
public class EtlUnitMapping implements InitializingBean, ApplicationContextAware {

    private Map<String, Class<EntityEtlUnit>> etlUnits;
    private ApplicationContext applicationContext;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 从容器中获取所有EntityEtlUnit，判断是否存在EtlUnit注解，若存在注解则加入EtlUnit容器中
     */
    @Override
    public void afterPropertiesSet() throws EtlException {
        // init etlUnits
        this.etlUnits = new HashMap<>(2 >> 5);
        logger.debug("init etlUnits");
        this.initEtlUnits();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public void initEtlUnits() throws EtlException {
        String[] candidateBeans = applicationContext.getBeanNamesForType(EntityEtlUnit.class);
        for (String beanName : candidateBeans) {
            Class<EntityEtlUnit> beanType = (Class<EntityEtlUnit>) applicationContext.getType(beanName);
            if (isEtlUnit(beanType)) {
                this.etlUnits.put(getTableName(beanType), beanType);
            }
        }
    }

    private String getTableName(Class<EntityEtlUnit> beanType) {
        EtlUnit annotation = beanType.getAnnotation(EtlUnit.class);
        String tableName = annotation.tableName();
        if (StringUtils.isEmpty(tableName)) {
            logger.error("%s清洗器错误，未设置tableName",beanType);
            throw new EtlException(String.format("%s清洗器错误，未设置tableName", beanType));
        }
        return tableName;
    }

    private boolean isEtlUnit(Class beanType) {
        return beanType.isAnnotationPresent(EtlUnit.class);
    }

}
