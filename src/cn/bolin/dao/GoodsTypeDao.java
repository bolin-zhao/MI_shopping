package cn.bolin.dao;


import cn.bolin.domain.GoodsType;

import java.util.List;

/**
 * Create By Bolin on
 */
public interface GoodsTypeDao {
     List<GoodsType> findByLevel(int level) ;

     GoodsType findByTypeId(Integer typeid);

}
