package cn.bolin.dao.impl;

import cn.bolin.dao.GoodsTypeDao;
import cn.bolin.domain.GoodsType;
import cn.bolin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Create By Bolin on
 */
public class GoodsTypeDaoImpl implements GoodsTypeDao {
    // 根据商品查询该级别商品
    @Override
    public List<GoodsType> findByLevel(int level){
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
           return qr.query("select * from tb_goods_type where level=?", new BeanListHandler<GoodsType>(GoodsType.class), level);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据商品level查询失败", e);
        }
    }

    // 根据id查询商品id
    @Override
    public GoodsType findByTypeId(Integer typeid) {
        QueryRunner QR = new QueryRunner(DataSourceUtils.getDataSource());
        try {
           return QR.query("select * from tb_goods_type where id=?", new BeanHandler<GoodsType>(GoodsType.class), typeid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据id查询商品类型失败", e);
        }
    }
}
