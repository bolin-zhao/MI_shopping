package cn.bolin.service.impl;

import cn.bolin.dao.GoodsDao;
import cn.bolin.dao.impl.GoodsDaoImpl;
import cn.bolin.domain.Goods;
import cn.bolin.domain.GoodsType;
import cn.bolin.domain.PageBean;
import cn.bolin.service.GoodsService;
import cn.bolin.service.GoodsTypeService;

import java.util.List;

/**
 * Create By Bolin on
 */
public class GoodsServiceImpl implements GoodsService {
    GoodsDao goodsDao=new GoodsDaoImpl();
    @Override
    public PageBean<Goods> findPageByWhere(int pageNum, int pageSize, String condition) {
        long totalSize=goodsDao.getCount(condition);
        List<Goods> data= goodsDao.findPageByWhere(pageNum,pageSize,condition);

        PageBean<Goods> pageBean=new PageBean<>(pageNum, pageSize, totalSize , data);
        return pageBean;
    }

    @Override
    public Goods findByGid(int gid) {
      Goods goods = goodsDao.findByGid(gid);

      // 查找goodsType对象
        GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();
        GoodsType goodsType = goodsTypeService.findByTypeId(goods.getTypeid());
        goods.setGoodsType(goodsType);
        return goods;
    }
}
