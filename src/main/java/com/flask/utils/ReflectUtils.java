package com.flask.utils;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description: 反射工具-ReflectUtil
 * <p>
 * getFields()只能获取public的字段，包括父类的
 * getDeclaredFields()只能获取自己声明的各种字段，包括public，protected，private。
 * isAssignableFrom方法，这个方法是用来判断两个类的之间的关联关系，也可以说是一个类是否可以被强制转换为另外一个实例对象
 *
 * @author csy
 * @version 1.0.0
 * @since 2020/11/23
 */
public class ReflectUtils {

    /**
     * 执行方法
     */
    public static <T> T invoke(Object obj, Method method, Object... args) {
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Object[] objects = new Object[parameterTypes.length];
        if (null != args) {

        }
        return null;
    }

    /**
     * 设置字段值
     *
     * @param obj
     * @param field
     * @param resultSet
     */
    public static void setFieldValue(Object obj, Field field, ResultSet resultSet) {
        Assert.notNull(field, "Field in not exist !");
        //  ResultSetMetaData metaData = resultSet.getMetaData();  //获取列集
        //  int columnCount = metaData.getColumnCount(); //获取列的数量
        //  for (int i = 0; i < columnCount; i++) { //循环列
        //      String columnName = metaData.getColumnName(i+1); //通过序号获取列名,起始值为1
        //       String columnValue = resultSet.getString(columnName);  //通过列名获取值.如果列值为空,columnValue为null,不是字符型
        //  }
        String name = field.getName();

        field.setAccessible(true);
        try {
            if (int.class == field.getType()) {
                field.set(obj, resultSet.getInt(name));
            } else  {
                field.set(obj, resultSet.getString(name));
            }
        } catch (IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
    }
}
