package com.ligen.mymybatis;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReflectionUtil {

    public static void setPropToBean(Object bean, String propName, Object value) {

        try {
            Field field = bean.getClass().getDeclaredField(propName);
            field.setAccessible(true);
            field.set(bean, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setPropToBeanFromResultSet(Object bean, ResultSet resultSet) throws SQLException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (f.getType().getSimpleName().equals("String")) {
                setPropToBean(bean, f.getName(), resultSet.getString(f.getName()));
            } else if (f.getType().getSimpleName().equals("Integer")) {
                setPropToBean(bean, f.getName(), resultSet.getInt(f.getName()));
            } else if (f.getType().getSimpleName().equals("Long")) {
                setPropToBean(bean, f.getName(), resultSet.getLong(f.getName()));
            }
        }
    }
}
