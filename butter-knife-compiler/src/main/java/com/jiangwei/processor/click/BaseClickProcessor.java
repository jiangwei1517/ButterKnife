package com.jiangwei.processor.click;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * author: jiangwei18 on 17/4/19 15:34 email: jiangwei18@baidu.com Hi: jwill金牛
 */
public abstract class BaseClickProcessor extends AbstractProcessor {

    protected Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(getAnnotationClass().getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(getAnnotationClass());
        for (Element element : elements) {
            if (element.getKind() != ElementKind.METHOD) {
                mMessager.printMessage(Diagnostic.Kind.ERROR, String.format("%s should be used on Method", getTag()),
                        element);
                return true;
            } else {
                buildJavaFile(element);
            }
        }
        return true;
    }

    public abstract void buildJavaFile(Element element);

    public abstract Class<? extends Annotation> getAnnotationClass();

    public abstract String getTag();

    public abstract String getPackageName();

    public abstract String getClassName();
}
