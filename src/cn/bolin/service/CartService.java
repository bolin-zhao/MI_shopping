package cn.bolin.service;

import cn.bolin.domain.Cart;

import java.util.List;

/**
 * Create By Bolin on
 */
public interface CartService {

    Cart findByUidAndPid(Integer uid, int pid);

    void add(Cart cart1);

    void update(Cart cart);

    List<Cart> findByUid(Integer id);

    void deleteByUidAndGid(Integer uid, int pid);


    void clearCartById(Integer id);
}
