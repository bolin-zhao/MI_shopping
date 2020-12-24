package cn.bolin.dao.impl;

import cn.bolin.dao.AddressDao;
import cn.bolin.domain.Address;
import cn.bolin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Create By Bolin on
 */
public class AddressDaoImpl implements AddressDao {

    //查询用户
    @Override
    public List<Address> findByUid(Integer uid) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_address where uid = ?", new BeanListHandler<Address>(Address.class),uid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据uid查询用户失败", e);
        }
    }

    //添加地址
    @Override
    public void add(Address address) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        Object [] params = {address.getDetail(),address.getName(),address.getPhone(),address.getUid(),address.getLevel()};
        try {
            qr.update("insert into tb_address (detail,name,phone,uid,level)values(?,?,?,?,?)",params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("添加地址失败!", e);
        }
    }

    // 设置默认地址
    @Override
    public void setDefault(int uid,int aid) {
        // 设置默认地址
        // 根据页面传的id获取uid,把所有level设为0,再根据uid,把level设为1
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            qr.update("update tb_address set level = 0 where uid = ?", uid);
            qr.update("update tb_address set level = 1 where id = ?",aid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("设置默认地址失败", e);
        }
    }

    // 删除地址
    @Override
    public void delete(Integer aid) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            qr.update("delete from tb_address where id = ?", aid);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据id删除用户名失败!", e);
        }
    }

    // 修改地址信息
    @Override
    public void updateAddress(Address address) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        Object[] params = {address.getDetail(),address.getName(),address.getPhone(),address.getId()};
        try {
            qr.update("update tb_address set detail=?, name=?, phone=? where id=?",params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("修改地址信息失败", e);
        }
    }



}
