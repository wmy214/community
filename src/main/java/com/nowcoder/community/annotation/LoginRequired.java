package com.nowcoder.community.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//这个注解可以写在方法上
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME) //注解生效的时间
public @interface LoginRequired {
}
