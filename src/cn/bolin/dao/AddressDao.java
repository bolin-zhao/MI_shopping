package cn.bolin.dao;

import cn.bolin.domain.Address;

import java.util.List;

/**
 * Create By Bolin on
 */
public interface AddressDao {
    List <Address> findByUid(Integer uid);

    void add(Address address);

    void setDefault(int uid, int aid);

    void delete(Integer aid);

    void updateAddress(Address address);
}
