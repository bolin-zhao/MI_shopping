package cn.bolin.dao.impl;
import cn.bolin.dao.UserDao;
import cn.bolin.domain.User;
import cn.bolin.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Create By Bolin on 12.22
 */
public class UserDaoImpl implements UserDao {
    @Override
    public List<User> findAll() {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
           return (List<User>) qr.query("select * from tb_user", new BeanHandler<User>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("查询用户失败!", e);
        }
    }

    @Override
    public User findUserByUserName(String username) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_user where username = ?", new BeanHandler<User>(User.class), username);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据用户名查找用户失败!",e);
        }
    }

    @Override
    public User findUserByUserNameAndPassword(String username,String password) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_user where username = ? and password = ?", new BeanHandler<User>(User.class), username,password );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据用户名和密码查找用户失败!",e);
        }
    }

    @Override
    public User findById(Integer id) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_user where id = ?", new BeanHandler<User>(User.class), id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据id查找用户失败!",e);
        }
    }

    @Override
    public void add(User user) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        Object [] args = {user.getUsername(),user.getPassword(),user.getEmail(),user.getGender(),user.getFlag(),user.getRole(),user.getCode()};
        try {
            qr.update("insert into tb_user(username,password,email,gender,flag,role,code) values(?,?,?,?,?,?,?)", args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("添加用户失败!",e);
        }
    }

    @Override
    public void update(User user) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        Object [] args = {user.getUsername(),user.getPassword(),user.getEmail(),user.getGender(),user.getFlag(),user.getId()};
        try {
            qr.update("update  tb_user set username=?,password=?,email=?,gender=?,flag=? where id =?", args);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("更改用户失败!",e);
        }
    }

    @Override
    public void delete(Integer id) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            qr.update("delete tb_user where id = ?", id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("删除用户失败!",e);
        }
    }

    @Override
    public User getUserByCode(String code) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        try {
            return qr.query("select * from tb_user  where code=?",new BeanHandler<User>(User.class),code);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据激活码查询失败!"+e.getMessage(), e);
        }
    }

    @Override
    public int updateFlagById(Integer id) {
        QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
        String sql = "update tb_user set flag = 1 where id = ?";
        try {
            qr.update(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("根据id激活失败!",e);
        }
        return 0;
    }
}
