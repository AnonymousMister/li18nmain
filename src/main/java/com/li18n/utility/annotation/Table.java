package com.li18n.utility.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

//创建注解,标识该model的table名
@Target(value = { java.lang.annotation.ElementType.TYPE })
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Table {
    String name() default "";
}
