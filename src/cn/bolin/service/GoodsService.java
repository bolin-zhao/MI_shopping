package cn.bolin.service;

import cn.bolin.domain.Goods;
import cn.bolin.domain.PageBean;

/**
 * Create By Bolin on
 */
public interface GoodsService {
    PageBean<Goods> findPageByWhere(int pageNum, int pageSize, String condition);

    Goods findByGid(int gid);
}