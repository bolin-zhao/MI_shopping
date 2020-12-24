package cn.bolin.service.impl;

import cn.bolin.dao.GoodsTypeDao;
import cn.bolin.dao.impl.GoodsTypeDaoImpl;
import cn.bolin.domain.GoodsType;
import cn.bolin.service.GoodsTypeService;

import java.util.List;

/**
 * Create By Bolin on
 */
public class GoodsTypeServiceImpl implements GoodsTypeService {    // 根据商品级别查询商品
    GoodsTypeDao goodsTypeDao = new GoodsTypeDaoImpl();
    @Override
    public List<GoodsType> findByLevel(int level) {

        return goodsTypeDao.findByLevel(level);
    }

    @Override
    public GoodsType findByTypeId(Integer typeid) {
        return goodsTypeDao.findByTypeId(typeid);
    }
}
