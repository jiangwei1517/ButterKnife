package com.jiangwei.annotation.bindviewa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: jiangwei18 on 17/4/18 20:14 email: jiangwei18@baidu.com Hi: jwill金牛
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface BindView {
    int value();
}
