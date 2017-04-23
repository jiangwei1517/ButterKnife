package com.jiangwei.annotation.bindviewf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author:  jiangwei18 on 17/4/19 20:45
 * email:  jiangwei18@baidu.com
 * Hi:   jwill金牛
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface BindViewf {
    int value();
}
