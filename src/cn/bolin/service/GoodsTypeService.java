package cn.bolin.service;

import cn.bolin.domain.GoodsType;

import java.util.List;

/**
 * Create By Bolin on
 */
public interface GoodsTypeService {
    List<GoodsType> findByLevel(int level);

    GoodsType findByTypeId(Integer typeid);
}
