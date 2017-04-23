package com.jiangwei.processor.id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * author: jiangwei18 on 17/4/18 21:30
 */

public class BindViewInfo {
    private static final String BIND = "BindId";
    private String mPackageName;
    private String mClassName;
    private TypeElement mTypeElement;
    private ArrayList<VariableElement> variableElements = new ArrayList<>();

    public Map<VariableElement, Integer> ids = new HashMap<>();

    public void addVariableElement(VariableElement v) {
        if (!variableElements.contains(v)) {
            variableElements.add(v);
        }
    }

    public TypeElement getTypeElement() {
        return mTypeElement;
    }

    public void setTypeElement(TypeElement typeElement) {
        this.mTypeElement = typeElement;
    }

    public BindViewInfo(String packageName, String className) {
        mPackageName = packageName;
        mClassName = className;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String PackageName) {
        this.mPackageName = PackageName;
    }

    public String getClassName() {
        return mClassName;
    }

    public String getProxyClassName() {
        return mClassName + "$$" + BIND;
    }

    public void setClassName(String ClassName) {
        this.mClassName = ClassName;
    }
}
