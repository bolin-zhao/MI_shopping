package cn.bolin.service.impl;

import cn.bolin.dao.AddressDao;
import cn.bolin.dao.impl.AddressDaoImpl;
import cn.bolin.domain.Address;
import cn.bolin.service.AddressService;

import java.util.List;

/**
 * Create By Bolin on
 */
public class AddressServiceImpl implements AddressService {
    AddressDao addressDao = new AddressDaoImpl();
    @Override
    public List<Address> findByUid(Integer uid) {
        return addressDao.findByUid(uid);
    }

    // 添加地址
    @Override
    public void add(Address address) {
        addressDao.add(address);
    }

    @Override
    public void setDefault(int uid, int aid) {
        addressDao.setDefault(uid,aid);
    }

    @Override
    public void delete(Integer aid) {
        addressDao.delete(aid);
    }

    @Override
    public void updateAddress(Address address) {
        addressDao.updateAddress(address);
    }

}
