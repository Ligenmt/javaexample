package com.ligen.mapper.test;

import org.apache.ibatis.annotations.*;


@Mapper
public interface TestUserMapper {

    @Select("select count(*) from user")
    long getCount();

}
