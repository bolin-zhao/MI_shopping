package cn.bolin.service.impl;

import cn.bolin.dao.CartDao;
import cn.bolin.dao.impl.CartDaoImpl;
import cn.bolin.domain.Cart;
import cn.bolin.domain.Goods;
import cn.bolin.service.CartService;
import cn.bolin.service.GoodsService;

import java.util.List;

/**
 * Create By Bolin on
 */
public class CartServiceImpl implements CartService {
    CartDao cartDao = new CartDaoImpl();
    @Override
    public Cart findByUidAndPid(Integer uid, int pid) {
        return cartDao.findByUidAndPid(uid,pid);
    }

    @Override
    public void add(Cart cart1) {
        cartDao.add(cart1);
    }

    @Override
    public void update(Cart cart) {
        cartDao.update(cart);
    }

    @Override
    public List<Cart> findByUid(Integer uid) {
        List<Cart> cartList = cartDao.findByUid(uid);
        // 找到goods对象
        GoodsService goodsService = new GoodsServiceImpl();

        for (Cart cart : cartList) {
            Goods goods = goodsService.findByGid(cart.getPid());
            cart.setGoods(goods);
        }
        return cartList;
    }

    @Override
    public void deleteByUidAndGid(Integer uid, int pid) {
        cartDao.deleteByUidAndGid(uid,pid);
    }

    @Override
    public void clearCartById(Integer id) {
        cartDao.clearCartById(id);
    }


}
