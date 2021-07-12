package com.itheima.case2.web;

import com.itheima.case2.pojo.User;
import com.itheima.case2.service.UserServiceImpl;
import com.itheima.case2.utils.AppJwtUtil;
import com.itheima.case2.utils.BaseController;
import com.itheima.case2.utils.MD5Utils;
import com.itheima.case2.vo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        //1.获取请求参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //对明文密码进行加密 TODO: 添加用户时，密码也需要加密
        password = MD5Utils.encode(password);

        //3.调用业务层的登录方法
        UserServiceImpl service = new UserServiceImpl();
        User user = service.login(username, password);

        //4.判断是否查询出结果 TODO:
        if (user != null) {
//            BaseController.printResult(response,new Result(true,"登录成功！", null));
            // 生成token
            int userId = Integer.parseInt(user.getId());
            //根据用户id使用AppJwtUtil工具类生成token
            String token = AppJwtUtil.getToken(userId);
            BaseController.printResult(response,new Result(true,"登录成功！", token));
        } else {
//            BaseController.printResult(response, new Result(false, "用户名或密码错误！"));
            response.getWriter().print(false);
        }
    }
}
