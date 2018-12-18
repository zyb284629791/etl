package com.john.etl.units;

import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Target(ElementType.TYPE)
@Scope("prototype")
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface EtlUnit {

    @AliasFor("value")
    String tableName() default "";

    @AliasFor("tableName")
    String value() default "";
}
