package cn.bolin.dao;

import cn.bolin.domain.Cart;

import java.util.List;

/**
 * Create By Bolin on
 */
public interface CartDao {
    Cart findByUidAndPid(Integer uid, int pid);

    void add(Cart cart1);

    void update(Cart cart);

    List<Cart> findByUid(Integer id);

    void deleteByUidAndGid(Integer uid, int pid);

    void clearCartById(Integer uid);
}
