package com.fangdd.tp.core.annotation;

import com.fangdd.tp.enums.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户注解，有用户身份限制
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Account {
    RoleEnum value() default RoleEnum.USER;
}
