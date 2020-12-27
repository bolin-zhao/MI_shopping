package cn.bolin.dao;

import cn.bolin.domain.Order;

import java.util.List;

/**
 * Create By Bolin on
 */
public interface OrderDao {
    void add(Order order);
    void updateStatus(String oid, String status);
}
