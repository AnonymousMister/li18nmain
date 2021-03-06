package com.li18n.utility.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

//创建注解,标识该model的id字段
@Target(value = { java.lang.annotation.ElementType.FIELD })
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface ID {

    /**
     * li18nkey的前缀
     * @return
     */
    String Li18n() default "";

    String name() default "Id";

}