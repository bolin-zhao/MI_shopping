package cn.bolin.dao;

import cn.bolin.domain.User;

import java.util.List;

/**
 * Create By Bolin on 12.22
 */
public interface UserDao {
    List<User> findAll();

    User findUserByUserName(String username);

    User findUserByUserNameAndPassword(String username, String password);

    User findById(Integer id);

    void add(User user);

    void update(User user);

    void delete(Integer id);

    User getUserByCode(String code);

    int updateFlagById(Integer id);
}
