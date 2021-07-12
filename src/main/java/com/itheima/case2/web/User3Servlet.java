package com.itheima.case2.web;

import com.itheima.case2.pojo.User;
import com.itheima.case2.service.UserServiceImpl;
import com.itheima.case2.utils.BaseController;
import com.itheima.case2.vo.PageBean;
import com.itheima.case2.vo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

//http://localhost:8080/user?methodName=findAllUsers
@WebServlet("/user")
public class User3Servlet extends BaseServlet {
    //删除
    public void deleteUserById(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("deleteUserById...");
    }
    //添加
    public void addUser(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("addUser...");
    }
    //修改
    public void updateUserById(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("updateUserById...");
    }
    //查询
    public void findAllUsers(HttpServletRequest request, HttpServletResponse response) {
        //在该方法体中处理请求和响应
        System.out.println("findAllUsers...");
    }

    //分页查询
    //其实就需要BaseServlet和User3Servlet即可，剩下的几个关于用户的servlet都是为了演示怎么通过反射简化代码的书写
    //改写我们以前实现一个功能就需要写一个servelt，这样我们给每一个角色模块创建一个servlet就行了
    public void findAllUsersByPage(HttpServletRequest request, HttpServletResponse response) {

        //获取方法名参数
        String methodName = request.getParameter("methodName");
        //获取当前页数
        String curPage = request.getParameter("curPage");
        //获取每一页的数据量
        String pageSize = request.getParameter("pageSize");

        //创建service层对象
        UserServiceImpl userService = new UserServiceImpl();
        //使用service层对象，调用分页查询的方法，传递当前页面和每一页的数据量
        PageBean<User> pb = userService.findAllUsersByPage(Integer.parseInt(curPage), Integer.parseInt(pageSize));
        //将获取的数据响应给浏览器
        //4.响应数据给前端
        //BaseController.printResult(response,new Result(true,"分页查询成功",pb));"
        try {
            BaseController.printResult(response,new Result(true,"分页查询成功",pb));
        } catch (IOException e) {
            e.printStackTrace();
            try {
                //查询失败，响应给前端
                BaseController.printResult(response,new Result(false,"分页查询失败"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


    }
}
