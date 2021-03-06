package jm233333.dsv.visualized;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

/**
 * Class {@code VDSInstantiation} stores the information required to instantiate a {@link VDS} with the specified constructor.
 */
public class VDSInstantiation {

    private String name;
    private String className;
    private Class<?> classType;
    private Constructor constructor;
    private ArrayList<Object> argumentList;

    /**
     * Creates an empty {@code VDSInstantiation}.
     */
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
