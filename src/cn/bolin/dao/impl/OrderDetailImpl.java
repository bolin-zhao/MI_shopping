package cn.bolin.dao.impl;

import cn.bolin.dao.OrderDetailDao;
import cn.bolin.domain.OrderDetail;
import cn.bolin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;


/**
 * Create By Bolin on
 */
public class OrderDetailImpl implements OrderDetailDao {
    @Override
    public void add(OrderDetail od) {
        // 事务要调用自己定义的连接数据库方法
        QueryRunner qr = new QueryRunner();
        Object [] params = {od.getOid(),od.getPid(),od.getNum(),od.getMoney()};
        String sql = "insert into tb_orderdetail(Oid,pid,num,money) values(?,?,?,?)";
        try {
            qr.update(DataSourceUtils.getConnection(), sql,params);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("添加订单详情失败", e);
        }
    }
}
