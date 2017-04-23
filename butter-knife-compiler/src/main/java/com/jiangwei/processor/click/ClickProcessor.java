package com.jiangwei.processor.click;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.annotation.processing.Processor;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import com.google.auto.service.AutoService;
import com.jiangwei.annotation.ProcessHandler;
import com.jiangwei.annotation.bindviewa.BindClick;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * author: jiangwei18 on 17/4/19 15:34 email: jiangwei18@baidu.com Hi: jwill金牛
 */
@AutoService(Processor.class)
public class ClickProcessor extends BaseClickProcessor {

    @Override
    public void buildJavaFile(Element element) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(getClassName() + "$$BindClick").addModifiers(Modifier.PUBLIC);
        MethodSpec.Builder builderMethod = MethodSpec.methodBuilder("bindClick");
        builderMethod.addParameter(TypeName.OBJECT, "activity").addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
                .beginControlFlow("if (activity instanceof android.app.Activity)")
                .addStatement("$L a = ($L)activity", element.getEnclosingElement().asType().toString(),
                        element.getEnclosingElement().asType().toString())
                .addStatement("$T methods = a.getClass().getDeclaredMethods()", Method[].class)
                .beginControlFlow("for (Method m : methods)").addStatement("m.setAccessible(true)")
                .addStatement("$T clickTag = m.getAnnotation($T.class)", BindClick.class, BindClick.class)
                .beginControlFlow("if (clickTag != null) ")
                .addStatement("int[] ids = clickTag.value()")
                .addStatement("$T processHandler = new $T(activity)", ProcessHandler.class, ProcessHandler.class)
                .addStatement("processHandler.addMethod($S, m);", "onClick")
                .addStatement(
                        "Object proxyInstance = $T.newProxyInstance(android.view.View.OnClickListener.class.getClassLoader(),\n"
                                + "new Class<?>[] { android.view.View.OnClickListener.class }, processHandler);",
                        Proxy.class)
                .beginControlFlow("for(int id : ids)").addStatement("android.view.View view = a.findViewById(id);")
                .beginControlFlow("try")
                .addStatement(
                        "$T setOnClickListener = view.getClass().getMethod($S, android.view.View.OnClickListener.class)",
                        Method.class, "setOnClickListener")
                .addStatement("setOnClickListener.invoke(view, proxyInstance)").endControlFlow()
                .beginControlFlow("catch(Exception e)").addStatement("e.printStackTrace()").endControlFlow()
                .endControlFlow().endControlFlow().endControlFlow().endControlFlow();
        builder.addMethod(builderMethod.build());
        JavaFile javaFile = JavaFile.builder(getPackageName(), builder.build()).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return BindClick.class;
    }

    @Override
    public String getTag() {
        return "@BindClick";
    }

    @Override
    public String getPackageName() {
        return "com.butterknife.luffy.bindclick";
    }

    @Override
    public String getClassName() {
        return "BindViewClick";
    }
}
