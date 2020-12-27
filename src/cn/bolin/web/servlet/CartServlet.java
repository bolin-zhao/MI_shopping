package cn.bolin.web.servlet;

import cn.bolin.domain.Cart;
import cn.bolin.domain.Goods;
import cn.bolin.service.CartService;
import cn.bolin.domain.User;
import cn.bolin.service.GoodsService;
import cn.bolin.service.impl.CartServiceImpl;
import cn.bolin.service.impl.GoodsServiceImpl;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 * Create By Bolin on 12.25
 */
@WebServlet("/cartservlet")
public class CartServlet extends BaseServlet{
    // 添加购物车
    public String addCart(HttpServletRequest request, HttpServletResponse response){
        // 1.判断是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "/login.jsp";
        }
        // 2.已登录, 添加购物车
        // 获取页面传递参数
        String gid = request.getParameter("goodsId"); // 商品id
        String number = request.getParameter("number");// 购买数量

        // 3.判断买统同一个商品的时候 是添加 还是更新? 通过谁? 通过用户id和商品id查找cart对象
        // 获取对象CartServiceImpl对象
        CartService cartService = new CartServiceImpl();
       Cart cart = cartService.findByUidAndPid(user.getId(),Integer.parseInt(gid));

       // 类型转换
        int pid = Integer.parseInt(gid);
        int num = Integer.parseInt(number);

        // 查找单价 通过 tb_goods 表中的price属性查找, 找到goods对象,取price属性
        GoodsService goodsService = new GoodsServiceImpl();
        Goods goods = goodsService.findByGid(pid);
        BigDecimal price = goods.getPrice();

        // 4.判断接收到的cart对象
        if (cart == null){
            // 添加数据
            Cart cart1 = new Cart(user.getId(),pid,num,price.multiply(new BigDecimal(num)));
            cartService.add(cart1);
        }else {
            // 更新数据 更新 数量/价格
            cart.setNum(cart.getNum()+num);
            cart.setMoney(price.multiply(new BigDecimal(cart.getNum())));

            // 更新
            cartService.update(cart);
        }

        return "/cartSuccess.jsp";
    }

    // 查看购车 cartservlet?method=getCart
    public String getCart(HttpServletRequest request, HttpServletResponse response){
        // 1. 判断是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "/login.jsp";
        }
        // 2.已登录, 查看购物车
        // 根据用户id查询购物车
        Integer uid = user.getId();
        // 获取购物车对象集合
        CartService cartService = new CartServiceImpl();
        List<Cart> cartList = cartService.findByUid(uid);
        // 返回到页面上:  ${carts}
        request.setAttribute("carts", cartList);
        return "/cart.jsp";
    }

    // 修改购物车 cartservlet?method=addCartAjax&goodsId="+pid+"&number=1
    public String addCartAjax(HttpServletRequest request, HttpServletResponse response){
        // 1. 判断是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "/login.jsp";
        }
        // 2. 已登录, 接收页面传递的数据参数
        String goodsId = request.getParameter("goodsId"); // 商品id
        String number = request.getParameter("number"); // 商品数量
        Integer uid = user.getId(); // 用户id
        int num = Integer.parseInt(number);
        // 3.根据商品id和用户id 获取购物车对象,判断购物车是否为空
        CartService cartService = new CartServiceImpl();
        Cart cart = cartService.findByUidAndPid(user.getId(), Integer.parseInt(goodsId));
        if (cart != null){
            // 判断number是否为0, 如果为0,删除购物车商品
            if ("0".equals(number)){
                // 根据用户id和商品id删除商品

                cartService.deleteByUidAndGid(uid,Integer.parseInt(goodsId));
            }
            // number不为0 修改购物车商品数量和价格
            // 得到商品单价 = 原来的价格/数量

            BigDecimal price = cart.getMoney().divide(new BigDecimal(cart.getNum()));

            // 更新数量 = 原数量 + 页面获取参数(正负1)
            cart.setNum(cart.getNum() + num);
            // 更新金额
            cart.setMoney(price.multiply(new BigDecimal(cart.getNum())));
            // 正式更新,调用已写好的update方法
            cartService.update(cart);
        }
        return "/cart.jsp";
    }

    // 清空购物车 cartservlet?method=clearCartAjax
    public String clearCartAjax(HttpServletRequest request, HttpServletResponse response){
        // 根据用户id判断购物车里是否还有商品
        // 1. 判断是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "/login.jsp";
        }
        Integer id = user.getId();
        // 根据id删除购物车
        CartService cartService = new CartServiceImpl();
        cartService.clearCartById(id);
        return "/cart.jsp";
    }


}
