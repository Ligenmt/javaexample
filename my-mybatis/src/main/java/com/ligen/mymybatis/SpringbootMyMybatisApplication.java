package com.ligen.mymybatis;


import com.ligen.mymybatis.entity.User;
import com.ligen.mymybatis.mapper.UserMapper;
import com.ligen.mymybatis.session.SqlSession;
import com.ligen.mymybatis.session.SqlSessionFactory;

public class SpringbootMyMybatisApplication {


    public static void main(String[] args) throws InterruptedException {
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //通过动态代理获取代理后接口
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.selectByPrimaryKey(1L);
        System.out.println(user);
    }

}
