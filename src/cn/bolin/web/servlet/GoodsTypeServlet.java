package cn.bolin.web.servlet;

import cn.bolin.domain.GoodsType;
import cn.bolin.service.GoodsTypeService;
import cn.bolin.service.impl.GoodsTypeServiceImpl;
import com.alibaba.fastjson.JSON;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Create By Bolin on ${DATA}
 */
@WebServlet("/goodstypeservlet")
public class GoodsTypeServlet extends BaseServlet {
    // 根据商品级别展示商品列表
    public String goodstypelist(HttpServletRequest request,HttpServletResponse response) throws IOException {

        // 2. 设置响应的数据格式
        response.setContentType("application/json;charset=utf-8");
        // 找商品级别为1的商品列表
        // 1. 创建商品列表的serviceImpl的对象 findByLevel(1)
        GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
        List<GoodsType> goodsTypeList = goodsTypeService.findByLevel(1);
        // 3. 需要把接收到list集合转为json数据格式
        String s = JSON.toJSONString(goodsTypeList);
        response.getWriter().write(s);
        return null;
    }
}

