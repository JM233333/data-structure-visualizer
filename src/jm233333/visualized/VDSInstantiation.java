package jm233333.visualized;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class VDSInstantiation {

    private String name;
    private String className;
    private Class<?> classType;
    private Constructor constructor;
    private ArrayList<Object> argumentList;

    public VDSInstantiation() {
        name = "";
        className = "";
        classType = null;
        constructor = null;
        argumentList = new ArrayList<>();
    }

    public void setName(final String name) {
        this.name = name;
    }
    public final String getName() {
        return name;
    }

    public void setClassName(final String className) {
        this.className = className;
    }
    public final String getClassName() {
        return className;
    }

    public void setClassType(final Class<?> classType) {
        this.classType = classType;
    }
    public final Class<?> getClassType() {
        return classType;
    }

    public void setConstructor(final Constructor constructor) {
        this.constructor = constructor;
    }
    public final Constructor getConstructor() {
        return constructor;
    }

    public final ArrayList<Object> getArgumentList() {
        return argumentList;
    }

}
