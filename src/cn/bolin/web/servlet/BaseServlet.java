package cn.bolin.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Author: bolin
 *
 *
 */

public class BaseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       //   ͨ通过判断用户的方法来执行对应的操作
        String methodName = request.getParameter("method");
        try {
            Method method = this.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            String url = (String) method.invoke(this,request,response);//this.method;

            //  判断url是否为空 不等0  说明有数据
            if (url!=null&&url.trim().length()!=0){

                //  根据返回值判断转发或者是重定向，添加前缀名 redirect  或者forward
                if (url.startsWith("redirect:")){
                    response.sendRedirect(request.getContextPath()+url.split(":")[1]);
                }else {
                    request.getRequestDispatcher(url).forward(request,response);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
