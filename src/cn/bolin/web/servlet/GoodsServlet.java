package cn.bolin.web.servlet;

import cn.bolin.domain.Goods;
import cn.bolin.domain.PageBean;
import cn.bolin.service.GoodsService;
import cn.bolin.service.impl.GoodsServiceImpl;
import cn.bolin.utils.StringUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create By Bolin on 12.24
 */

@WebServlet("/goodsservlet")
public class GoodsServlet extends BaseServlet {
    // 获取商品列表
    public String getGoodsListByTypeId(HttpServletRequest request, HttpServletResponse response) {

        String typeId = request.getParameter("typeId");
        String _pageNum = request.getParameter("pageNum");
        String _pageSize = request.getParameter("pageSize");
        int pageNum = 1;
        int pageSize = 8;
        if (!StringUtils.isEmpty(_pageNum)) {
            pageNum = Integer.parseInt(_pageNum);
            if (pageNum < 1) {
                pageNum = 1;
            }
        }
        if (!StringUtils.isEmpty(_pageSize)) {
            pageSize = Integer.parseInt(_pageSize);
            if (pageSize < 1) {
                pageSize = 8;
            }
        }
        System.out.println(pageNum + "..." + pageSize);
        GoodsService goodsService = new GoodsServiceImpl();
        String condition = "";
        if (typeId != null && typeId.trim().length() != 0) {
            condition = "typeid=" + typeId;
        }
        try {
            PageBean<Goods> pageBean = goodsService.findPageByWhere(pageNum, pageSize, condition);  // typeId=1;
            request.setAttribute("pageBean", pageBean);
            request.setAttribute("typeId", typeId);
            return "/goodsList.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/index.jsp";
        }

    }

    // 获取商品详情
    public String getGoodsById(HttpServletRequest request, HttpServletResponse response){
        // 获取页面传递的商品id
        String gid = request.getParameter("id");
        GoodsService goodsService = new GoodsServiceImpl();
        Goods goods = goodsService.findByGid(Integer.parseInt(gid));
        request.setAttribute("goods", goods);
        return "/goodsDetail.jsp";
    }
}
