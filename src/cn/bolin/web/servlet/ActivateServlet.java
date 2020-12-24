package cn.bolin.web.servlet;

import cn.bolin.service.UserService;
import cn.bolin.service.impl.UserServiceImpl;
import cn.bolin.utils.Base64Utils;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create By Bolin on 12.23
 */
public class ActivateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActivateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String c=request.getParameter("c");
        String code=Base64Utils.decode(c);

        System.out.println("激活码："+code);

        UserService userService=new UserServiceImpl();
        int b =userService.active(code);
        if(b==1) {
            request.setAttribute("msg", "激活成功!!!");
        }else {
            request.setAttribute("msg", "激活失败!!!");
        }
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
