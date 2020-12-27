package cn.bolin.web.servlet;

import cn.bolin.service.OrderService;
import cn.bolin.service.impl.OrderServiceImpl;
import cn.bolin.utils.WeiXinResult;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create By Bolin on 12.27
 */
@WebServlet(name = "WxCallBackServlet",value = "/wxcallback")
public class WxCallBackServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受微信回调的数据
        String json = request.getParameter("result");
        WeiXinResult result = JSON.parseObject(json, WeiXinResult.class);
        String result_code = result.getResult().getResult_code();
        if(result_code.equals("SUCCESS")){
            //判断重定向
            if(result.getType()==0){
                request.setAttribute("msg", "您的订单号为:"+result.getResult().getOut_trade_no()+",金额为:"+result.getResult().getCash_fee()+"已经支付成功,等待发货~~");

            }else {
                //socket
                response.getWriter().write("success");
            }
            OrderService orderService=new OrderServiceImpl();
            orderService.updateStatus(result.getResult().getOut_trade_no(), "2");

        }else{
            System.out.println("支付失败");
            request.setAttribute("msg", "您的订单号为:"+result.getResult().getOut_trade_no()+"支付失败");

        }
        request.getRequestDispatcher("/message.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
