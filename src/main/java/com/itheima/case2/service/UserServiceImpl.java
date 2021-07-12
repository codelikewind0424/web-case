package com.itheima.case2.service;

import com.itheima.case2.dao.UserMapper;
import com.itheima.case2.pojo.User;
import com.itheima.case2.utils.SqlSessionUtil;
import com.itheima.case2.vo.PageBean;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class UserServiceImpl {
    public User login(String username, String password) {
        SqlSession sqlSession = SqlSessionUtil.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.login(username,password);
        sqlSession.close();
        return user;
    }

    //实现分页查询功能的业务层---主体思想---封装
    //将web层传递过来的数据和其它处理得到的数据封装到PageBean对象中
    public PageBean<User> findAllUsersByPage(int curPage, int pageSize) {
        //创建PageBean实例对象
        PageBean<User> pb = new PageBean<>();
        //封装数据
        SqlSession session = null;
        try {
            pb.setCurPage(curPage);
            pb.setPageSize(pageSize);
            int startIndex = pb.getStartIndex();
            //PageBean对象中还差list:每页的数据，  count：数据表中总的条数
            //mybatis那一套
            //获取sqlSession对象
            session = SqlSessionUtil.getSession();
            //获取接口代理对象
            UserMapper mapper = session.getMapper(UserMapper.class);
            //通过接口代理对象调用接口中的方法
            //调用分页查询的方法
            List<User> list = mapper.findAllUsersByPage(startIndex,pageSize);
            //封装到PageBean对象中
            pb.setList(list);
            //调用计算数据总的条数的方法
            int count = mapper.getAllUserCount();
            //封装到对象中
            pb.setCount(count);
        } finally {
            if (session != null){
                session.close();
            }

        }
        return pb;

    }
}
