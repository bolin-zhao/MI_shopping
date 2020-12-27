package cn.bolin.dao.impl;

import cn.bolin.dao.OrderDao;
import cn.bolin.domain.Order;
import cn.bolin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;


/**
 * Create By Bolin on
 */
public class OrderDaoImpl implements OrderDao {
    @Override
    public void add(Order order) {
        QueryRunner qr=new QueryRunner();
        Object[] params={order.getId(),order.getUid(),order.getMoney(),order.getStatus(),order.getTime(),order.getAid()};
        try {
            qr.update(DataSourceUtils.getConnection(), "insert into tb_order(id,uid,money,status,time,aid) values(?,?,?,?,?,?)",params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加订单失败", e);
        }
    }

    @Override
    public void updateStatus(String oid, String status) {
        QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
        try {
            qr.update( "update tb_order set status=? where id=?",status,oid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("更新订单失败", e);
        }
    }
}
