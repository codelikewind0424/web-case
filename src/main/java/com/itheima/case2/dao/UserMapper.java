package com.itheima.case2.dao;

import com.itheima.case2.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    //登录功能
    @Select("SELECT * FROM t_user WHERE username=#{username} AND password=#{password}")
    User login(@Param("username") String username, @Param("password")String password);

    //分页查询--多表查询：建议使用XML映射配置文件实现
    List<User> findAllUsersByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    //查询t_user表中数据总的条数
    @Select("select count(*) from t_user")
    int getAllUserCount();
}
