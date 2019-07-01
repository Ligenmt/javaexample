package com.ligen.mymybatis.mapper;


import com.ligen.mymybatis.entity.User;

import java.util.List;

public interface UserMapper {

    User selectByPrimaryKey(Long id);

    List<User> selectAll();
}
