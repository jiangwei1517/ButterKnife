package com.jiangwei.processor.id;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

/**
 * author: jiangwei18 on 17/4/18 20:16 email: jiangwei18@baidu.com Hi: jwill金牛
 */

public abstract class BaseBindIdProcessor extends AbstractProcessor {
    protected Map<String, BindViewInfo> mBindMap = new HashMap<>();

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new HashSet<>();
        annotationTypes.add(getAnnotationClass().getCanonicalName());
        return annotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        mBindMap.clear();
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(getAnnotationClass());
        for (Element element : elements) {
            if (element.getKind() != ElementKind.FIELD) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "annotation should be used on field",
                        element);
                return true;
            } else {
                VariableElement variableElement = (VariableElement) element;
                if (variableElement.getModifiers().contains(Modifier.PRIVATE)) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            "variable shouldn't be used on private", element);
                    return true;
                }
                PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(variableElement);
                String packageName = packageElement.getQualifiedName().toString();
                TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
                String className = getClassName(typeElement, packageName);
                // 考虑到内部类ViewHolder,用包名和类名组合在一起进行区分
                String pAndcName = packageName + className;

                BindViewInfo bindViewInfo = mBindMap.get(pAndcName);
                if (bindViewInfo == null) {
                    bindViewInfo = new BindViewInfo(packageName, className);
                    bindViewInfo.setTypeElement(typeElement);
                    mBindMap.put(pAndcName, bindViewInfo);
                }
                bindViewInfo.addVariableElement(variableElement);
                int id = getAnnotationValue(variableElement);
                bindViewInfo.ids.put(variableElement, id);
            }
        }
        for (String key : mBindMap.keySet()) {
            BindViewInfo info = mBindMap.get(key);
            TypeSpec.Builder builder =
                    TypeSpec.classBuilder(info.getClassName() + getTag()).addModifiers(Modifier.PUBLIC);
            if (info.getClassName().contains("$")) {
                // 内部类ViewHolder
                MethodSpec.Builder builderMethod = MethodSpec.methodBuilder("bindViewForViewHolder");
                builderMethod.addParameter(TypeName.OBJECT, "view").addParameter(TypeName.OBJECT, "viewHolder")
                        .addModifiers(Modifier.PUBLIC).beginControlFlow("if (view instanceof android.view.View)")
                        .addStatement("android.view.View a = (android.view.View)view");
                builderMethod.addStatement("$L holder = ($L)viewHolder", info.getTypeElement().asType().toString(),
                        info.getTypeElement().asType().toString());
                for (VariableElement v : info.ids.keySet()) {
                    int value = info.ids.get(v);
                    builderMethod.addStatement("holder.$L = ($L)a.findViewById($L)", v.getSimpleName(),
                            v.asType().toString(), value);
                }
                builderMethod.endControlFlow().returns(TypeName.VOID);
                builder.addMethod(builderMethod.build());
            } else {
                buildJavaFile(info, builder);
            }
            JavaFile javaFile = JavaFile.builder(getPackageName(), builder.build()).build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public abstract void buildJavaFile(BindViewInfo info, TypeSpec.Builder builder);

    public abstract String getPackageName();

    public abstract String getTag();

    public abstract Class<? extends Annotation> getAnnotationClass();

    public abstract int getAnnotationValue(VariableElement variableElement);

    public String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }
}
