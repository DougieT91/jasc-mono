package com.tawandr.utils.object.attributes;

import com.tawandr.utils.object.exceptions.DtoCreatorException;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;

import static com.tawandr.utils.object.StringUtils.isEmpty;
import static com.tawandr.utils.object.StringUtils.toPascalCase;

public class Attribute {
    private Integer position;
    private String typeName;
    private String simpleTypeName;
    private String name;
    private String simpleName;
    private String getter;
    private String setter;

    public Attribute() {
    }

    public Attribute(Parameter parameter) {
        this.name=parameter.getName();
        this.simpleName=parameter.getName();
        this.typeName = parameter.getType().getTypeName();
        this.simpleTypeName = parameter.getType().getSimpleName();
    }

    public Attribute(Field field) {
        this.name=field.getName();
        this.simpleName=field.getName();
        this.typeName = field.getType().getTypeName();
        this.simpleTypeName = field.getType().getSimpleName();
    }



    public void createGetterAndSetter() {
        if(isEmpty(simpleName)){
            throw new DtoCreatorException("[ATTRIBUTE NAME] Not Found");
        }
        if(isEmpty(simpleTypeName)){
            throw new DtoCreatorException("[ATTRIBUTE TYPE] Not Found");
        }
        getter = new StringBuilder()
                .append("public ")
                .append(simpleTypeName)
                .append(" get")
                .append(toPascalCase(simpleName))
                .append("{\n")
                .append("\treturn ")
                .append(simpleName)
                .append(";\n}")
                .toString();

        setter = new StringBuilder()
                .append("public ")
                .append(simpleTypeName)
                .append(" set")
                .append(toPascalCase(simpleName))
                .append("{\n")
                .append("\treturn ")
                .append(simpleName)
                .append(";\n}")
                .toString();
    }

    @Override
    public String toString() {
        return "\n\tAttribute{" +
                ", \n\t\tsimpleTypeName='" + simpleTypeName + '\'' +
                ", \n\t\tsimpleName='" + simpleName + '\'' +
                "\n\t}";
    }

    public String toStringFull() {
        return "\n\tAttribute{" +
                " \n\t\tposition=" + position +
                ", \n\t\ttypeName='" + typeName + '\'' +
                ", \n\t\tsimpleTypeName='" + simpleTypeName + '\'' +
                ", \n\t\tname='" + name + '\'' +
                ", \n\t\tsimpleName='" + simpleName + '\'' +
                ", \n\t\tgetter=\n'" + getter + '\'' +
                ", \n\t\tsetter=\n'" + setter + '\'' +
                "\n\t}";
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSimpleTypeName() {
        return simpleTypeName;
    }

    public void setSimpleTypeName(String simpleTypeName) {
        this.simpleTypeName = simpleTypeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getSetter() {
        return setter;
    }

    public void setSetter(String setter) {
        this.setter = setter;
    }
}
