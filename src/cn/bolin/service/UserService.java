package cn.bolin.service;
import cn.bolin.domain.User;

/**
 * Create By Bolin on 12.22
 */
public interface UserService {
    // 1. 用户注册
    void register(User user);
    // 2. 检查用户名是否存在
    User findByUserName(String username);
    // 3. 用户登录
    User login(String username, String password);
    // 4. 激活用户
    int active(String code);
}
