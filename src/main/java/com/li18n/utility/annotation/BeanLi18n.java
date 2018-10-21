package com.li18n.utility.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(value = { java.lang.annotation.ElementType.FIELD })
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface BeanLi18n {
    String Li18n() ;
}
