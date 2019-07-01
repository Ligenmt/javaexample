package com.ligen.mymybatis.executor;


import com.ligen.mymybatis.ReflectionUtil;
import com.ligen.mymybatis.config.Configuration;
import com.ligen.mymybatis.config.MapperStatement;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DefaultExecutor implements Executor {

    private final Configuration conf;

    public DefaultExecutor(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public <E> List<E> query(MapperStatement ms, Object parameter) {
        List<E> resultList = new ArrayList<>();
        try {
            Class.forName(conf.getJdbcDriver());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            conn = DriverManager.getConnection(conf.getJdbcUrl(), conf.getJdbcUsername(), conf.getJdbcPassword());
            preparedStatement = conn.prepareStatement(ms.getSql());
            //将方法参数映射到占位符上
            parameterize(preparedStatement, parameter);
            //jdbc执行sql
            resultSet = preparedStatement.executeQuery();
            //将结果反射到list中
            handleResultSet(resultSet, resultList, ms.getResultType());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                preparedStatement.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    private void parameterize(PreparedStatement preparedStatement, Object parameter) throws SQLException {

        if (Collection.class.isAssignableFrom(parameter.getClass())) {
            Object[] objects = ((Collection) parameter).toArray();
            for (int i=0; i<objects.length; i++) {
                Object p = objects[i];
                if (p instanceof Integer) {
                    preparedStatement.setInt(i+1, (Integer) p);
                } else if (p instanceof String) {
                    preparedStatement.setString(i+1, (String) p);
                } else if (p instanceof Long) {
                    preparedStatement.setLong(i+1, (Long) p);
                }
            }
        }
    }

    //读取ResultSet中数据，填充到model中
    private <E> void handleResultSet(ResultSet resultSet, List<E> ret, String resultType) {
        Class<E> clazz = null;
        try {
            clazz = (Class<E>) Class.forName(resultType);
            while (resultSet.next()) {
                Object entity = clazz.newInstance();
                ReflectionUtil.setPropToBeanFromResultSet(entity, resultSet);
                ret.add((E) entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
