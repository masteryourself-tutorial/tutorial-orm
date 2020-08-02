package pers.masteryourself.tutorial.mybatis.mbg;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

/**
 * <p>description : RemarksCommentGenerator
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/8/2 17:13
 */
public class RemarksCommentGenerator extends DefaultCommentGenerator {

    /**
     * 定义一个是否使用修改后的模式的标识
     */
    private boolean suppressAllComments = true;

    /**
     * 设置实体类 属性注释
     *
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
                field.addJavaDocLine("/**");
                field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
                field.addJavaDocLine(" */");
            }
        } else {
            super.addFieldComment(field, introspectedTable, introspectedColumn);
        }
    }

    /**
     * 设置实体类 getter注释
     *
     * @param method
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
                //method.addJavaDocLine("//获取:" + introspectedColumn.getRemarks());
            }
        } else {
            super.addGetterComment(method, introspectedTable, introspectedColumn);
        }
    }

    /**
     * 设置实体类 setter注释
     *
     * @param method
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
                //method.addJavaDocLine("//设置:" + introspectedColumn.getRemarks());
            }
        } else {
            super.addSetterComment(method, introspectedTable, introspectedColumn);
        }
    }

    /**
     * 去掉mapper原始注释
     *
     * @param method
     * @param introspectedTable
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        } else {
            super.addGeneralMethodComment(method, introspectedTable);
        }
    }

    /**
     * 去掉mapping原始注释
     *
     * @param xmlElement
     */
    @Override
    public void addComment(XmlElement xmlElement) {
        if (suppressAllComments) {
            return;
        } else {
            super.addComment(xmlElement);
        }
    }

}
