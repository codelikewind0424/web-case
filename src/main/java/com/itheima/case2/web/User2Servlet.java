package com.itheima.case2.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

//http://localhost:8080/user?methodName=findAllUsers
@WebServlet("/user2")
public class User2Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取要执行的方法名
        String methodName = request.getParameter("methodName");
        //2.判断当前请求是哪个方法
       /* if("findAllUsers".equals(methodName)){
            //说明此时执行查询所有用户方法
            findAllUsers(request,response);
        }else if("updateUserById".equals(methodName)){
            //说明此时执行修改用户方法
            updateUserById(request,response);
        }else if("addUser".equals(methodName)){
            //说明此时执行添加用户方法
            addUser(request,response);
        }else if("deleteUserById".equals(methodName)){
            //说明此时执行删除用户方法
            deleteUserById(request,response);
        }*/

       /*
            使用反射技术解决上述过多if语句判断的代码问题
            回顾反射：
                1.获取当前类的Class对象
                2.使用Class对象调用方法获取要执行的方法
                3.执行方法
        */
       /*
            1.获取当前类的Class对象
            回顾：
            在java中this表示调用当前方法的类的对象，举例：
                在User2Servlet的doGet方法中this就是User2Servlet类的对象
                如果在RoleServlet的doGet方法中this就是RoleServlet类的对象
                如果在PermissionServlet的doGet方法中this就是PermissionServle类的对象
        */
        Class clazz = this.getClass();
        /*
             2.使用Class对象调用方法获取要执行的方法:
                 public Method getMethod(String name, Class<?>... parameterTypes)
                                            参数：
                                                name:要获取的方法名 例如：deleteUserById addUser updateUserById findAllUsers
                                                parameterTypes：要执行方法的形参字节码文件对象类型
         */
        try {
            Method m = clazz.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            /*
                 3.执行方法
                 使用Method类中的方法invoke执行User2Servlet RoleServlet等类中的具体的方法：deleteUserById addUser updateUserById findAllUsers
                 public Object invoke(Object obj, Object... args)
                                    参数：
                                        obj:要执行的方法所依赖的对象 ，这里是this
                                        args：要执行方法的形参 request response
             */
            m.invoke(this, request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUserById(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("deleteUserById...");
    }

    public void addUser(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("addUser...");
    }

    public void updateUserById(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("updateUserById...");
    }

    public void findAllUsers(HttpServletRequest request, HttpServletResponse response) {
        //在该方法体中处理请求和响应
        System.out.println("findAllUsers...");
    }
}
