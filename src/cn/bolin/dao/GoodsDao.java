package cn.bolin.dao;

import cn.bolin.domain.Goods;

import java.util.List;

/**
 * Create By Bolin on
 */
public interface GoodsDao {
    long getCount(String condition);

    List<Goods> findPageByWhere(int pageNum, int pageSize, String condition);

    Goods findByGid(int gid);
}
