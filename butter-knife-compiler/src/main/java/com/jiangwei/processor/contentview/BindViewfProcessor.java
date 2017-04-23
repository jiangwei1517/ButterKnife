package com.jiangwei.processor.contentview;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.annotation.processing.Processor;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import com.google.auto.service.AutoService;
import com.jiangwei.annotation.bindviewf.BindViewf;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * author: jiangwei18 on 17/4/19 14:33 email: jiangwei18@baidu.com Hi: jwill金牛
 */
@AutoService(Processor.class)
public class BindViewfProcessor extends BaseBindViewProcessor {

    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return BindViewf.class;
    }

    @Override
    public int getAnnotationValue(Element element) {
        BindViewf annotation = element.getAnnotation(BindViewf.class);
        return annotation.value();
    }

    @Override
    public String getPackageName() {
        return "com.butterknife.luffy.bindviewf";
    }

    @Override
    public String getClassName() {
        return "BindContentView";
    }

    @Override
    public void buildJavaFile(Element element, String packageName, String className, int value) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(className + "$$BindViewf").addModifiers(Modifier.PUBLIC);
        MethodSpec.Builder builderMethod = MethodSpec.methodBuilder("setContentView");
        builderMethod.addParameter(TypeName.OBJECT, "fragment").addModifiers(Modifier.PUBLIC).returns(Object.class)
                .beginControlFlow(
                        "if (fragment instanceof android.support.v4.app.Fragment||fragment instanceof android.app.Fragment)")
                .addStatement("$L a = ($L)fragment", element.asType().toString(), element.asType().toString())
                .addStatement("android.view.View view = android.view.View.inflate(a.getActivity(), $L, null)", value)
                .addStatement("return view").endControlFlow().addStatement("return null");
        builder.addMethod(builderMethod.build());
        JavaFile javaFile = JavaFile.builder(packageName, builder.build()).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
