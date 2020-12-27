package cn.bolin.service.impl;

import cn.bolin.dao.CartDao;
import cn.bolin.domain.Cart;
import cn.bolin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.util.List;

/**
 * Create By Bolin on 12.26
 */
public class CartDaoImpl implements CartDao {
    @Override
    public Cart findByUidAndPid(Integer uid, int pid) {
        return null;
    }

    @Override
    public void add(Cart cart1) {

    }

    @Override
    public void update(Cart cart) {

    }

    @Override
    public List<Cart> findByUid(Integer id) {
        return null;
    }

    @Override
    public void deleteByUidAndGid(Integer uid, int pid) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
    }

    @Override
    public void clearCartById(Integer uid) {
        QueryRunner qr = new QueryRunner();
        try {
            qr.update(DataSourceUtils.getConnection(), "delete from tb_cart where id=?",uid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("根据id删除失败");
        }
    }
}
