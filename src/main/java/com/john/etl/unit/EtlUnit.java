package com.john.etl.unit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Component
@Target(ElementType.TYPE)
@Scope("prototype")
@Retention(RetentionPolicy.RUNTIME)
public @interface EtlUnit {

    String tableName() ;
}
