package com.ligen.mapper;

import com.ligen.model.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by ligen on 2018/5/8.
 */
@Mapper
public interface AuthenticationMapper {

    @Select("select * from admin_user where name=#{name}")
    AdminUser selectUser(@Param("name") String name);

    @Select("select * from admin_role_user where user_id=#{userId}")
    AdminUser selectUserRole(@Param("userId") long name);

    @Select("select count(*) from admin_role_user where user_id=#{userId} and role_id in (select role_id from admin_permission_role where permission_id = (SELECT id from admin_permission where name = #{apiName}))")
    int checkRole(@Param("userId") int userId, @Param("apiName") String apiName);
}
