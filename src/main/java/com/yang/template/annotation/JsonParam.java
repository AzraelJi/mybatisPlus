package com.yang.template.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;


/**
 * @author jjyy
 * @since 2019-09-05
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonParam {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    boolean required() default true;

}
