package com.zooms.dean.common.mybatis;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;

/**
 * project：dean-cloud
 *
 * @author linfeng @ nondo
 * @date 2018/4/19
 */
public class MapperMethodAnnotationPlugin extends PluginAdapter {

    private FullyQualifiedJavaType tableName;
    private FullyQualifiedJavaType mapperAnnotation;
    private FullyQualifiedJavaType returnType;
    private FullyQualifiedJavaType dataTableInterface;
    private FullyQualifiedJavaType overrideImportedType;

    public MapperMethodAnnotationPlugin() {
        this.tableName = new FullyQualifiedJavaType("com.nondo.dean.common.mybatis.Table");
        this.mapperAnnotation = new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper");
        this.returnType = new FullyQualifiedJavaType("java.lang.String");
        this.dataTableInterface = new FullyQualifiedJavaType("com.nondo.dean.common.mybatis.DataTable");
        this.overrideImportedType = new FullyQualifiedJavaType("java.lang.annotation");
    }

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        Method method = new Method("getTableName");
        method.addBodyLine("return \"" + introspectedTable.getTableConfiguration().getTableName() + "\";");
        method.setReturnType(returnType);
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addAnnotation("@Override");

        topLevelClass.addImportedType(overrideImportedType);
        topLevelClass.addImportedType(dataTableInterface);
        topLevelClass.addMethod(method);
        topLevelClass.addSuperInterface(dataTableInterface);

        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        interfaze.addImportedType(tableName);
        interfaze.addImportedType(mapperAnnotation);
        interfaze.addAnnotation("@Table(\"" + introspectedTable.getTableConfiguration().getTableName() + "\")");
        interfaze.addAnnotation("@Mapper");

        return true;
    }
}
