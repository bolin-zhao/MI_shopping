package cn.bolin.service.impl;

import cn.bolin.dao.CartDao;
import cn.bolin.dao.OrderDao;
import cn.bolin.dao.OrderDetailDao;
import cn.bolin.dao.impl.CartDaoImpl;
import cn.bolin.dao.impl.OrderDaoImpl;
import cn.bolin.dao.impl.OrderDetailImpl;
import cn.bolin.domain.Order;
import cn.bolin.domain.OrderDetail;
import cn.bolin.service.OrderService;
import cn.bolin.utils.DataSourceUtils;

import java.util.List;

/**
 * Create By Bolin on
 */
public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = new OrderDaoImpl();
    @Override
    public void saveOrder(Order order, List<OrderDetail> orderDetails) {
        try {
            // 1. 开启事务
            DataSourceUtils.startTransaction();
            // 2. 调用dao向order表添加数据

            orderDao.add(order);
            // 3. 向订单详情表添加数据
            OrderDetailDao orderDetailDao = new OrderDetailImpl();
            for (OrderDetail od : orderDetails) {
                orderDetailDao.add(od);
            }
            // 4. 清空购物车
            CartDao cartDao = new CartDaoImpl();
            cartDao.clearCartById(order.getUid());
            // 5. 提交事务
            DataSourceUtils.commit();
        } catch (Exception e) {
            e.printStackTrace();
            // 6. 回滚
            try {
                DataSourceUtils.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }finally {
            // 7. 关闭事务
            try {
                DataSourceUtils.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void updateStatus(String oid, String status) {
        orderDao.updateStatus(oid,status);
    }
}
