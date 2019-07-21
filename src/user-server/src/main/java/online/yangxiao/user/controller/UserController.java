package online.yangxiao.user.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.naming.event.ObjectChangeListener;

import online.yangxiao.common.util.RestResult;
import online.yangxiao.common.entity.User;
import online.yangxiao.user.service.UserService;
import online.yangxiao.common.util.JedisUtil;
import online.yangxiao.common.util.RestAPIResult;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/signup")
    public RestResult<Boolean> signup(@RequestParam(value="email", required=false) String email,
                                      @RequestParam(value="password", required=false) String password,
                                      @RequestParam(value="username", required=false) String username) {
        logger.info("[user-server/user/signup] param: {}, {}, {}", email, password, username);
        RestResult<Boolean> restResult = null;
        User user = userService.findByEmail(email);
        if (user != null) {
            restResult = new RestResult<>(-1, false, "用户已存在");
        } else {
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setProfile("/images/default_profile.jpg");

            // 此处应该设置激活
            userService.regist(user);

            restResult = new RestResult<>();
        }

        return restResult;
    }

    @RequestMapping("/signin")
    public RestResult<User> signin(@RequestParam(value="email", required=false) String email,
                                      @RequestParam(value="password", required=false) String password,
                                      @RequestParam(value="token", required=false) String token) {
        logger.info("[user-server/user/sigin] email: {}, password: {}, token: {}", email, password, token);

        User user = userService.findByEmail(email);
        RestResult<User> restResult = null;
        if (user == null ) {
            restResult = new RestResult<>(-1, false, "用户已存在");
        } else {
            logger.info(user.getPassword() + ", " + password);
            if (user.getPassword().trim().equals(password.trim())) {
                restResult = new RestResult<User>(1, true, "登录成功", user);
                JedisUtil.STRINGS.setEx(email, 3600, token);    // 一小时过期
            } else {
                restResult = new RestResult<>(0, false, "用户密码错误");
            }
        }

        return restResult;
    }

    @RequestMapping("/checkToken")
    public RestResult<Boolean> checkToken(@RequestParam(value="email", required=false) String email,
                                          @RequestParam(value="token", required=false) String token) {
        RestResult<Boolean> restResult = null;

        String redisToken = JedisUtil.STRINGS.get(email);
        if (redisToken != null && !"".equals(redisToken)) {
            if (token.equals(redisToken)) {
                JedisUtil.STRINGS.setEx(email, 3600, token);
                restResult = new RestResult<>(1, true, "用户已登录");
            } else {    // 登录过期或被踢下
                restResult = new RestResult<>(-1, false, "登录过期或被踢下");
            }
        } else {
            restResult = new RestResult<>(0, false, "用户不存在");
        }

        return restResult;
    }

    @RequestMapping("/signout")
    public RestResult<Boolean> signout(@RequestParam(value="email", required=false) String email,
                                       @RequestParam(value="token", required=false) String token) {
        long delKey = 1;
        RestResult<Boolean> restResult = null;
        if (email != null && JedisUtil.KEYS.exists(email)) {
             delKey = JedisUtil.KEYS.del(email);
        }
        if (delKey != 1) {
            restResult = new RestResult<>(0, false, "注销失败");
        }else{
            restResult = new RestResult<>(1, true, "注销成功");
        }

        return restResult;
    }

    @RequestMapping("/test")
    public boolean test() {
        logger.info("[user/test] test");

        return true;
    }
}
