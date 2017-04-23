package com.jiangwei.processor.id;

import java.lang.annotation.Annotation;

import javax.annotation.processing.Processor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

import com.google.auto.service.AutoService;
import com.jiangwei.annotation.bindviewf.BindIdf;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * author: jiangwei18 on 17/4/18 20:16 email: jiangwei18@baidu.com Hi: jwill金牛
 */
@AutoService(Processor.class)
public class BindIdfProcessor extends BaseBindIdProcessor {

    @Override
    public void buildJavaFile(BindViewInfo info, TypeSpec.Builder builder) {
        MethodSpec.Builder builderMethod = MethodSpec.methodBuilder("bindViewForFragment");
        builderMethod.addParameter(TypeName.OBJECT, "view").addParameter(TypeName.OBJECT, "fragment")
                .addModifiers(Modifier.PUBLIC)
                .beginControlFlow(
                        "if (view instanceof android.view.View&&(fragment instanceof android.support.v4.app.Fragment||fragment instanceof android.app.Fragment))")
                .addStatement("$L f = ($L)fragment", info.getTypeElement().asType().toString(),
                        info.getTypeElement().asType().toString())
                .addStatement("android.view.View v = (android.view.View)view");
        for (VariableElement v : info.ids.keySet()) {
            int value = info.ids.get(v);
            builderMethod.addStatement("f.$L = ($L)v.findViewById($L)", v.getSimpleName(), v.asType().toString(),
                    value);
        }
        builderMethod.endControlFlow().returns(TypeName.VOID);
        builder.addMethod(builderMethod.build());
    }

    @Override
    public String getPackageName() {
        return "com.butterknife.luffy.bindidf";
    }

    @Override
    public String getTag() {
        return "$$BindIdf";
    }

    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return BindIdf.class;
    }

    @Override
    public int getAnnotationValue(VariableElement variableElement) {
        BindIdf a = variableElement.getAnnotation(BindIdf.class);
        return a.value();
    }
}
