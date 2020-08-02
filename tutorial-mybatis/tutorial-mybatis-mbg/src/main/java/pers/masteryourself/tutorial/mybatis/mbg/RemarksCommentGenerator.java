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
     * 自定义实体类属性注释
     *
     * @param field              {@link Field}
     * @param introspectedTable  {@link IntrospectedTable}
     * @param introspectedColumn {@link IntrospectedColumn}
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
            field.addJavaDocLine(" */");
        }
    }

    /**
     * 设置实体类 getter 注释
     *
     * @param method             {@link Method}
     * @param introspectedTable  {@link IntrospectedTable}
     * @param introspectedColumn {@link IntrospectedColumn}
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // do nothing
    }

    /**
     * 设置实体类 setter 注释
     *
     * @param method             {@link Method}
     * @param introspectedTable  {@link IntrospectedTable}
     * @param introspectedColumn {@link IntrospectedColumn}
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // do nothing
    }

    /**
     * 去掉 mapper 接口注释
     *
     * @param method            {@link Method}
     * @param introspectedTable {@link IntrospectedTable}
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        // do nothing
    }

    /**
     * 去掉 xml 接口注释
     *
     * @param xmlElement {@link XmlElement}
     */
    @Override
    public void addComment(XmlElement xmlElement) {
        // do nothing
    }

}
