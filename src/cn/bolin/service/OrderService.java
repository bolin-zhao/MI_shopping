package cn.bolin.service;

import cn.bolin.domain.Order;
import cn.bolin.domain.OrderDetail;

import java.util.List;

/**
 * Create By Bolin on
 */
public interface OrderService {

    void saveOrder(Order order, List<OrderDetail> orderDetails);

    void updateStatus(String r6_order, String s);
}
