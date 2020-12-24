package cn.bolin.web.servlet;

import cn.bolin.domain.Address;
import cn.bolin.domain.User;
import cn.bolin.service.AddressService;
import cn.bolin.service.UserService;
import cn.bolin.service.impl.AddressServiceImpl;
import cn.bolin.service.impl.UserServiceImpl;
import cn.bolin.utils.RandomUtils;
import cn.bolin.utils.StringUtils;
import cn.dsna.util.images.ValidateCode;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Create By Bolin on 12.22
 */
@WebServlet("/userservlet")
public class UserServlet extends BaseServlet {
    // 检查用户名是否正确
    public String checkUserName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 接收页面输入的用户名
        String username = request.getParameter("username");
        if (StringUtils.isEmpty(username)){
            return null;
        }

        UserServiceImpl userService = new UserServiceImpl();
        // 2. 把页面接收的用户名值传过去
        User user = userService.findByUserName(username);
        // System.out.println("user 对象是:"+user);

        // 3. 判断从数据库里接收到的对象是否为空, 如果不为空,则返回1, 否则返回 0
        if (user != null){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("0");
        }
        return null;
    }

    // 用户注册
    public String register(HttpServletRequest request, HttpServletResponse response){
        // 获取数据
        try {
            User user = new User();
            BeanUtils.populate(user, request.getParameterMap());
            String repassword = request.getParameter("repassword");
            //校验数据
            if (StringUtils.isEmpty(user.getUsername())){
                request.setAttribute("registerMsg", "用户名不能为空");
                return "/register.jsp";
            }
            if (StringUtils.isEmpty(user.getPassword())){
                request.setAttribute("registerMsg", "密码不能为空");
                return "/register.jsp";
            }
            if (!user.getPassword().equals(repassword)){
                request.setAttribute("registerMsg", "两次密码不一致");
                return "/register.jsp";
            }
            if (StringUtils.isEmpty(user.getEmail())){
                request.setAttribute("registerMsg", "邮箱不能为空");
                return "/register.jsp";
            }

            // 调用业务
            UserService userService = new UserServiceImpl();
            // flag / role / code
            user.setFlag(0);
            user.setRole(1);
            user.setCode(RandomUtils.createActive());
            userService.register(user);
            // 注册成功
            return "redirect:/registerSuccess.jsp";

        } catch (Exception e) {
            request.setAttribute("registerMsg", "注册失败!");
            e.printStackTrace();
        }
        return "/index.jsp";
    }

    // 用户登录
    public String login (HttpServletRequest request, HttpServletResponse response){
        // 获取用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // 自动登录
        String auto = request.getParameter("auto");

        if (StringUtils.isEmpty(username)){
            request.setAttribute("msg", "用户名不能为空");
            return "/login.jsp";
        }
        if (StringUtils.isEmpty(password)){
            request.setAttribute("msg", "密码不能为空");
            return "/login.jsp";
        }

        // 验证用户名和密码是否正确
        UserService userService = new UserServiceImpl();
        User user = userService.login(username, password);

        if (user == null){
            request.setAttribute("msg", "用户名密码不匹配!");
            return "/login.jsp";
        }else {
            // 有用户
            // 有没有激活?
            if (user.getFlag()!=1){
                request.setAttribute("msg", "该用户尚未激活或禁用!");
                return "/login.jsp";
            }
            // 有没有权限
            if (user.getRole()!=1){
                request.setAttribute("msg", "该用户没有权限!");
                return "/login.jsp";
            }
            // 登录成功
            request.getSession().setAttribute("user", user);
            if (auto!=null){
                // 创建cookie
                String userinfo = username+"#"+password;
                Cookie cookie = new Cookie("userinfo", userinfo);
                cookie.setMaxAge(60*60*24*14); // 有效时间: 两周
                cookie.setPath("/");
                cookie.setHttpOnly(true);
                response.addCookie(cookie);
            }
            // 重定向到首页
            return "redirect:/index.jsp";
        }
    }

    // 验证码
    public String code(HttpServletRequest request, HttpServletResponse response){
        // 1. 设置验证码
        ValidateCode code = new ValidateCode(100,40,4,20);
        // 2. 获取验证码
        String code1 = code.getCode();
        // 3. 将获取的验证码存储到session里
        request.getSession().setAttribute("code", code1);
        try {
            code.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 检查验证码是否正确
    public String checkCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取用户输入的code
        String clientcode = request.getParameter("code");
        // 获取缓存的sessioncode
        String sessioncode = (String) request.getSession().getAttribute("code");
        // 如果用户输入为空
        if (StringUtils.isEmpty(clientcode)){
            return null;
        }
        // 比较验证码, 忽略大小写
        if (clientcode.equalsIgnoreCase(sessioncode)){
            response.getWriter().write("0");
        }else {
            response.getWriter().write("1");
        }
        return null;
    }

    //注销登录
    public String logOut(HttpServletRequest request, HttpServletResponse response){
        // 1. 删除用户中的session数据
        request.getSession().removeAttribute("user");
        // 2. session 自杀
        request.getSession().invalidate();
        // 3. 删除cookie
        Cookie cookie = new Cookie("userinfo", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "redirect:/index.jsp";

    }

    //查询地址
    public String getAddress(HttpServletRequest request, HttpServletResponse response){
        // 1. 判断是否登录,session
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            return "/login.jsp";
        }
        // 2. 不为空,则开始查询地址
        AddressService addressService = new AddressServiceImpl();
        List<Address> addList = addressService.findByUid(user.getId());

        // 3. 向self_info页面传递数据 键名: addList 值:我们通过数据库接收到的addList
        request.setAttribute("addList",addList);
        return "/self_info.jsp";
    }

    // 添加地址
    public String addAddress (HttpServletRequest request,HttpServletResponse response){
        // 1.判断是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "/login.jsp";
        }
        System.out.println(user);
        // 已登录,添加地址
        // 2. 获取页面传过来的用户名name,电话phone,地址详情detail
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");
        System.out.println(user.getId());
        Address address = new Address(null, detail, name, phone, user.getId(), 0);

        // 3. 创建对象AddressServiceImpl, 把address作为参数传递进去
        AddressService addressService = new AddressServiceImpl();
        addressService.add(address);

        return getAddress(request, response);

    }

    // 设置默认
    public String defaultAddress (HttpServletRequest request,HttpServletResponse response){
        // 1. 判断是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "/login.jsp";
        }
        // 2. 已登录, 设为默认地址, 获取两个参数,aid(页面传的) uid(用户的)
        String id = request.getParameter("id");
        Integer aid = Integer.parseInt(id);
        Integer uid = user.getId();
        // 根据页面传的id获取uid,把所有level设为0,再根据uid,把level设为1
        AddressService addressService = new AddressServiceImpl();
        addressService.setDefault(uid,aid);

        return getAddress(request, response);
    }

    // 删除地址
    public String deleteAddress(HttpServletRequest request,HttpServletResponse response){
        // 1. 获取页面传递的参数id
        String id = request.getParameter("id");
        Integer aid = Integer.parseInt(id);
         // 2. 创建AddressServiceImpl对象
        AddressService addressService = new AddressServiceImpl();
        addressService.delete(aid);

        return getAddress(request, response);
    }

    // 修改地址信息
    public String updateAddress(HttpServletRequest request, HttpServletResponse response){
        // 1.判断是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "/login.jsp";
        }
        // 已登录,修改地址
        // 2.从页面获取5个参数
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");
        String id = request.getParameter("id");
        String level = request.getParameter("level");

        // 以下3个参数不能为空
        if (StringUtils.isEmpty(name)||StringUtils.isEmpty(phone)||StringUtils.isEmpty(detail)){
            return getAddress(request, response);
        }


        // 封装Address对象
        Address address = new Address(Integer.parseInt(id),detail,name,phone,user.getId(),Integer.parseInt(level));

        // 创建AddressServiceImpl对象
        AddressService addressService = new AddressServiceImpl();
        addressService.updateAddress(address);

        return getAddress(request, response);
    }
}
