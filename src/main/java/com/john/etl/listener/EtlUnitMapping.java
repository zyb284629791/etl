package com.john.etl.listener;

import com.john.etl.units.EntityEtlUnit;
import com.john.etl.units.EtlException;
import com.john.etl.units.EtlUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description 模仿spring mvc中requestmappinghandlermapping处理controller的方式来处理etlunit
 * @Author: Yb.Z
 * @Date: 2018/11/26.22:34
 * @Version：1.0
 */
@Component
public class EtlUnitMapping implements ApplicationListener<ContextRefreshedEvent> {

    private Map<String, Class<EntityEtlUnit>> etlUnits;
    private ApplicationContext applicationContext;
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 从容器中获取所有EntityEtlUnit，判断是否存在EtlUnit注解，若存在注解则加入EtlUnit容器中
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.applicationContext = event.getApplicationContext();
        // init etlUnits
        if (this.applicationContext != null) {
            this.etlUnits = new ConcurrentHashMap<>();
            logger.debug("init etlUnits map");
            this.initEtlUnits();
        }
    }

    private void initEtlUnits() throws EtlException {
        // 此方法找到的beanName是注解内value对应的值
        String[] candidateBeans = applicationContext.getBeanNamesForType(EntityEtlUnit.class);
        for (String beanName : candidateBeans) {
            Class<EntityEtlUnit> beanType = (Class<EntityEtlUnit>) applicationContext.getType(beanName);
            if (isEtlUnit(beanType)) {
                // this.etlUnits.put(getTableName(beanType), beanType);
                // 由于上面得到的beanName已经是注解内value的值，故此处不需要在查找一次了。
                this.etlUnits.put(beanName, beanType);
            }
        }
    }

    /**
     *
     * @param beanType
     * @return
     */
    @Deprecated
    private String getTableName(Class<EntityEtlUnit> beanType) {
        EtlUnit annotation = beanType.getAnnotation(EtlUnit.class);
        String tableName = annotation.tableName();
        if (StringUtils.isEmpty(tableName)) {
            logger.error("%s清洗器错误，未设置tableName",beanType);
            throw new EtlException(String.format("%s清洗器初始化错误，未设置tableName", beanType));
        }
        return tableName;
    }

    private boolean isEtlUnit(Class beanType) {
        return beanType.isAnnotationPresent(EtlUnit.class);
    }

    /**
     * 通过表名查找清洗器
     * @param tableName
     * @return
     */
    public Class<EntityEtlUnit> getEtlUnitByTableName(String tableName){
        if (StringUtils.isEmpty(tableName)) {
            throw new EtlException("tableName must be not null");
        } else {
            Class<EntityEtlUnit> etlUnitClass = this.etlUnits.get(tableName);
            if (ObjectUtils.isEmpty(etlUnitClass)) {
                // :todo 没有配置etl的表暂时报错
                throw new EtlException("该表暂不存在对应的etl，请联系管理员" + tableName);
            } else {
                return etlUnitClass;
            }
        }
    }
}
