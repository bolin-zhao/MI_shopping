package cn.bolin.web.servlet;

import cn.bolin.domain.*;
import cn.bolin.service.AddressService;
import cn.bolin.service.CartService;
import cn.bolin.service.OrderService;
import cn.bolin.service.impl.AddressServiceImpl;
import cn.bolin.service.impl.CartServiceImpl;
import cn.bolin.service.impl.OrderServiceImpl;
import cn.bolin.utils.RandomUtils;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create By Bolin on 12.26
 */
@WebServlet("/orderservlet")
public class OrderServlet extends BaseServlet{
    // 结账跳转到订单列表 orderservlet?method=getOrderView
    public String getOrderView(HttpServletRequest request, HttpServletResponse response){
        // 1. 判断购物车是否为空
        // 1.1 根据当前用户,获取用户id
        User user = (User) request.getSession().getAttribute("user");
        Integer id = user.getId();
        // 2. 根据用户id获取购物车商品列表
        CartService cartService = new CartServiceImpl();
        List<Cart> cartList = cartService.findByUid(id);
        // 3. 获取地址列表
        AddressService addressService = new AddressServiceImpl();
        List<Address> addressList = addressService.findByUid(id);
        // System.out.println("购物车:"+ cartList + " 地址列表:" +addressList);

        // 4. 把查询到的购物车和地址信息传递到order页
        request.setAttribute("carts", cartList);
        request.setAttribute("addList", addressList);

        // 5. 跳转页面
        return "/order.jsp";


    }

    // 提交订单 orderservlet?method=addOrder&aid=1
    // 1. 订单表插入一条数据
    // 2. 订单详情表插入N条数据
    // 3. 删除购物车数据
    public String addOrder(HttpServletRequest request, HttpServletResponse response){
        // 1.判断用户是否登录,并获取用户id
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return "/login.jsp";
        }
        Integer id = user.getId();
        // 2. 获取页面传递的aid参数
        String aid = request.getParameter("aid");
        // 3. 先获取要买的商品(购物车里)
        CartService cartService = new CartServiceImpl();
        List<Cart> carts = cartService.findByUid(id);

        if (carts == null || carts.size()==0){
            request.setAttribute("msg", "购物车为空,请选择商品!");
            return "/message.jsp ";
        }

        // 4. 创建一个订单号
        String oid = RandomUtils.createOrderId();
        // 5. 创建订单详情
        List <OrderDetail> orderDetails = new ArrayList<>();
        BigDecimal sum = new BigDecimal(0);
        for (Cart cart : carts) {
            OrderDetail orderDetail = new OrderDetail(null,oid,cart.getPid(),cart.getNum(),cart.getMoney());
            orderDetails.add(orderDetail);
            sum = sum.add(cart.getMoney());
        }
        // 6. 创建订单
        Order order = new Order(oid,id,sum,"1",new Date(),Integer.parseInt(aid));

        OrderService orderService = new OrderServiceImpl();
        try {
            orderService.saveOrder(order,orderDetails);
            request.setAttribute("order", order);
            return "/orderSuccess.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "订单提交失败:"+e.getMessage());
            return "/message.jsp";
        }
    }
}
