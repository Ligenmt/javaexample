package com.ligen.mymybatis.session;


import com.ligen.mymybatis.binding.MapperProxy;
import com.ligen.mymybatis.config.Configuration;
import com.ligen.mymybatis.config.MapperStatement;
import com.ligen.mymybatis.executor.DefaultExecutor;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 对外暴露增删改查接口
 * 对内将请求转发给executor
 */
public class DefaultSqlSession implements SqlSession {

    private Configuration conf;

    private DefaultExecutor executor;

    public DefaultSqlSession(Configuration conf) {
        this.conf = conf;
        //所有的sql底层操作封装在Executor中
        executor = new DefaultExecutor(conf);
    }

    @Override
    public <T> T selectOne(String statement, Object parameter) {
        List<Object> selectList = this.selectList(statement, parameter);
        if (selectList == null || selectList.size() == 0) {
            return null;
        }
        if (selectList.size() == 1) {
            return (T) selectList.get(0);
        } else {
            throw new RuntimeException("Too many results!");
        }
    }

    @Override
    public <E> List<E> selectList(String statement, Object parameter) {
        MapperStatement mapperStatement = conf.getStatementMap().get(statement);
        return executor.query(mapperStatement, parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type) {
        MapperProxy mp = new MapperProxy(this);
        return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, mp);
    }
}
