package com.ligen.mymybatis.binding;


import com.ligen.mymybatis.session.SqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MapperProxy implements InvocationHandler {

    private SqlSession sqlSession;

    public MapperProxy(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Class<?> returnType = method.getReturnType();
        String sourceId = method.getDeclaringClass().getName() + "." + method.getName();
        List argList = new ArrayList();
        if (args != null) {
            argList = Arrays.asList(args);
        }
        if (Collection.class.isAssignableFrom(returnType)) {
            return sqlSession.selectList(sourceId, argList);
        } else {
            return sqlSession.selectOne(sourceId, argList);
        }
    }
}
