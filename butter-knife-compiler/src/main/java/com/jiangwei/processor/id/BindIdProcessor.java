package com.jiangwei.processor.id;

import java.lang.annotation.Annotation;

import javax.annotation.processing.Processor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import com.google.auto.service.AutoService;
import com.jiangwei.annotation.bindviewa.BindId;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * author: jiangwei18 on 17/4/18 20:16
 */
@AutoService(Processor.class)
public class BindIdProcessor extends BaseBindIdProcessor {

    @Override
    public void buildJavaFile(BindViewInfo info, TypeSpec.Builder builder) {
        MethodSpec.Builder builderMethod = MethodSpec.methodBuilder("bindViewForActivity");
        builderMethod.addParameter(TypeName.OBJECT, "object").addModifiers(Modifier.PUBLIC)
                .beginControlFlow("if (object instanceof android.app.Activity)").addStatement("$L a = ($L)object",
                        info.getTypeElement().asType().toString(), info.getTypeElement().asType().toString());
        for (VariableElement v : info.ids.keySet()) {
            int value = info.ids.get(v);
            builderMethod.addStatement("a.$L = ($L)a.findViewById($L)", v.getSimpleName(), v.asType().toString(),
                    value);
        }
        builderMethod.endControlFlow().returns(TypeName.VOID);
        builder.addMethod(builderMethod.build());
    }

    @Override
    public String getPackageName() {
        return "com.butterknife.luffy.bindid";
    }

    @Override
    public String getTag() {
        return "$$BindId";
    }

    @Override
    public Class<? extends Annotation> getAnnotationClass() {
        return BindId.class;
    }

    @Override
    public int getAnnotationValue(VariableElement variableElement) {
        BindId a = variableElement.getAnnotation(BindId.class);
        return a.value();
    }

    public String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }
}
