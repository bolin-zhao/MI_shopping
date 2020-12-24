package cn.bolin.service.impl;
import cn.bolin.dao.UserDao;
import cn.bolin.dao.impl.UserDaoImpl;
import cn.bolin.domain.User;
import cn.bolin.service.UserService;
import cn.bolin.utils.EmailUtils;
import cn.bolin.utils.Md5Utils;
import cn.bolin.utils.RandomUtils;

/**
 * Create By Bolin on 12.22
 */
public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    // 注册
    @Override
    public void register(User user) {
        // 密码加密
        user.setPassword(Md5Utils.md5(user.getPassword()));
        //设置默认值没有激活
        user.setFlag(0);
        //设置角色
        user.setRole(1);
        //设置激活码
        user.setCode(RandomUtils.createActive());
        userDao.add(user);
        // 邮箱激活,发送邮件
        EmailUtils.sendEmail(user);

    }
    // 通过用户名查找
    @Override
    public User findByUserName(String username) {
        return userDao.findUserByUserName(username);
    }
    // 登录
    @Override
    public User login(String username, String password) {
        // 密码先加密再比对
        password = Md5Utils.md5(password);
        return userDao.findUserByUserNameAndPassword(username, password);
    }
    // 激活账号
    @Override
    public int active(String code) {
        User user = userDao.getUserByCode(code);

        // 更改用户的激活状态
        int result = userDao.updateFlagById(user.getId());
        if (result>0){
            return 1;
        }
        return 0;
    }
}
