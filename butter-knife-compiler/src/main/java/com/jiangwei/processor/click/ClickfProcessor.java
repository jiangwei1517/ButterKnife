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
import com.jiangwei.annotation.bindviewf.BindClickf;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * author: jiangwei18 on 17/4/19 15:34 email: jiangwei18@baidu.com Hi: jwill金牛
 */
@AutoService(Processor.class)
public class ClickfProcessor extends BaseClickProcessor {

    @Override
    public void buildJavaFile(Element element) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(getClassName() + "$$BindClickf").addModifiers(Modifier.PUBLIC);
        MethodSpec.Builder builderMethod = MethodSpec.methodBuilder("bindClickf");
        builderMethod.addParameter(TypeName.OBJECT, "fragment").addParameter(TypeName.OBJECT, "view")
                .addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
                .beginControlFlow(
                        "if (view instanceof android.view.View&&(fragment instanceof android.app.Fragment||fragment instanceof android.support.v4.app.Fragment))")
                .addStatement("$L f = ($L)fragment", element.getEnclosingElement().asType().toString(),
                        element.getEnclosingElement().asType().toString())
                .addStatement("android.view.View v = (android.view.View)view")
                .addStatement("$T methods = f.getClass().getDeclaredMethods()", Method[].class)
                .beginControlFlow("for (Method m : methods)").addStatement("m.setAccessible(true)")
                .addStatement("$T clickTag = m.getAnnotation($T.class)", BindClickf.class, BindClickf.class)
                .beginControlFlow("if (clickTag != null) ").addStatement("int[] ids = clickTag.value()")
                .addStatement("$T processHandler = new $T(fragment)", ProcessHandler.class, ProcessHandler.class)
                .addStatement("processHandler.addMethod($S, m);", "onClick")
                .addStatement(
                        "Object proxyInstance = $T.newProxyInstance(android.view.View.OnClickListener.class.getClassLoader(),\n"
                                + "new Class<?>[] { android.view.View.OnClickListener.class }, processHandler);",
                        Proxy.class)
                .beginControlFlow("for(int id : ids)").addStatement("android.view.View viewClick = v.findViewById(id);")
                .beginControlFlow("try")
                .addStatement(
                        "$T setOnClickListener = viewClick.getClass().getMethod($S, android.view.View.OnClickListener.class)",
                        Method.class, "setOnClickListener")
                .addStatement("setOnClickListener.invoke(viewClick, proxyInstance)").endControlFlow()
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
        return BindClickf.class;
    }

    @Override
    public String getTag() {
        return "@BindClickf";
    }

    @Override
    public String getPackageName() {
        return "com.butterknife.luffy.bindclickf";
    }

    @Override
    public String getClassName() {
        return "BindViewClickf";
    }
}
