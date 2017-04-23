package com.jiangwei.processor.contentview;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.annotation.processing.Processor;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

import com.google.auto.service.AutoService;
import com.jiangwei.annotation.bindviewa.BindView;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * author: jiangwei18 on 17/4/19 14:33 email: jiangwei18@baidu.com Hi: jwill金牛
 */
@AutoService(Processor.class)
public class BindViewProcessor extends BaseBindViewProcessor {

    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return BindView.class;
    }

    @Override
    public int getAnnotationValue(Element element) {
        BindView annotation = element.getAnnotation(BindView.class);
        return annotation.value();
    }

    @Override
    public String getPackageName() {
        return "com.butterknife.luffy.bindview";
    }

    @Override
    public String getClassName() {
        return "BindContentView";
    }

    @Override
    public void buildJavaFile(Element element, String packageName, String className, int value) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(className + "$$BindView").addModifiers(Modifier.PUBLIC);
        MethodSpec.Builder builderMethod = MethodSpec.methodBuilder("setContentView");
        builderMethod.addParameter(TypeName.OBJECT, "activity").addModifiers(Modifier.PUBLIC).returns(TypeName.VOID)
                .beginControlFlow("if (activity instanceof android.app.Activity)")
                .addStatement("$L a = ($L)activity", element.asType().toString(), element.asType().toString())
                .addStatement("a.setContentView($L)", value).endControlFlow();
        builder.addMethod(builderMethod.build());
        JavaFile javaFile = JavaFile.builder(packageName, builder.build()).build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
