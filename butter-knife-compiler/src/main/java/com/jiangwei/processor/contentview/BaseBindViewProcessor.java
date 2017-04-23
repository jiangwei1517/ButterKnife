package com.jiangwei.processor.contentview;

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
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * author: jiangwei18 on 17/4/20 12:24 email: jiangwei18@baidu.com Hi: jwill金牛
 */
public abstract class BaseBindViewProcessor extends AbstractProcessor {

    protected Messager mMessager;
    protected Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mElementUtils = processingEnvironment.getElementUtils();
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
            if (element.getKind() != ElementKind.CLASS) {
                mMessager.printMessage(Diagnostic.Kind.ERROR,
                        String.format("%s should be used on class", getAnnotationClass().getSimpleName()), element);
                return true;
            } else {
                String packageName = getPackageName();
                String className = getClassName();
                int value = getAnnotationValue(element);
                buildJavaFile(element, packageName, className, value);
            }
        }
        return true;
    }

    public abstract Class<? extends Annotation> getAnnotationClass();

    public abstract int getAnnotationValue(Element element);

    public abstract String getPackageName();

    public abstract String getClassName();

    public abstract void buildJavaFile(Element element, String packageName, String className, int value);
}
