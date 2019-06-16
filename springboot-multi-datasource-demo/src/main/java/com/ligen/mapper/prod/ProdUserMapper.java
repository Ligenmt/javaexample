package com.ligen.mapper.prod;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface ProdUserMapper {

    @Select("select count(*) from user")
    long getCount();

}
