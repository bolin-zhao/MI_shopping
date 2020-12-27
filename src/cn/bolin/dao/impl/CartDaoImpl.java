package cn.bolin.dao.impl;

import cn.bolin.dao.CartDao;
import cn.bolin.domain.Cart;
import cn.bolin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Create By Bolin on
 */
public class CartDaoImpl implements CartDao {

    // 执行查询购物车
    @Override
    public Cart findByUidAndPid(Integer uid, int pid) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_cart where id=? and pid=?", new BeanHandler<Cart>(Cart.class), uid,pid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据uid/pid查找购物车失败!",e);
        }
    }

    // 添加购物车
    @Override
    public void add(Cart cart1) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        Object [] params = {cart1.getId(),cart1.getPid(),cart1.getNum(),cart1.getMoney()};
        try {
            qr.update("insert into tb_cart values(?,?,?,?) ", params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("添加购物车失败!", e);
        }
    }

    // 更新购物车
    @Override
    public void update(Cart cart) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        Object [] params = {cart.getNum(),cart.getMoney(),cart.getId(),cart.getPid()};
        try {
            qr.update("update tb_cart set Num=?,money=? where id=? and pid=?", params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更新购物车失败!", e);
        }
    }

    // 查看购物车
    @Override
    public List<Cart> findByUid(Integer id) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_cart where id =?", new BeanListHandler<Cart>(Cart.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据id查看购物车失败!", e);
        }
    }

    // 根据用户id/商品pid删除购物车商品
    @Override
    public void deleteByUidAndGid(Integer uid, int pid) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            qr.update("delete from tb_cart where id=? and pid=?", uid,pid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据用户id/商品pid删除购物车商品失败!",e);
        }
    }

    // 根据id清空购物车
    @Override
    public void clearCartById(Integer id) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            qr.update("delete from tb_cart where id=?", id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据用户id清空购物车商品失败!",e);
        }
    }

}
