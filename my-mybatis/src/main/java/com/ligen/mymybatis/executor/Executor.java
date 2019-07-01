package com.ligen.mymybatis.executor;


import com.ligen.mymybatis.config.MapperStatement;

import java.util.List;

public interface Executor {

    <E> List<E> query(MapperStatement ms, Object parameter);
}
