package com.jiangwei.annotation;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * author: jiangwei18 on 17/4/17 11:38 email: jiangwei18@baidu.com Hi: jwill金牛
 */

public class ProcessHandler implements InvocationHandler {
    private Map<String, Method> methodMaps = new HashMap<>();
    private WeakReference<Object> mHandlerRef;

    public ProcessHandler(Object handler) {
        mHandlerRef = new WeakReference<>(handler);
    }

    public Object getHandler() {
        if (mHandlerRef != null) {
            return mHandlerRef.get();
        }
        return null;
    }

    public void setHandler(Object handler) {
        mHandlerRef = new WeakReference<>(handler);
    }

    public void addMethod(String methodName, Method m) {
        if (!methodMaps.containsKey(methodName)) {
            methodMaps.put(methodName, m);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (mHandlerRef != null) {
            Method realMethod = methodMaps.get(method.getName());
            if (realMethod != null) {
                return realMethod.invoke(getHandler(), args);
            } else {
                throw new IllegalStateException("method doesn't exists in map");
            }
        }
        return null;
    }
}
