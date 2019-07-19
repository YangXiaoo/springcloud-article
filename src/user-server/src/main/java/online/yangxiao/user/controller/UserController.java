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

import online.yangxiao.common.entity.User;
import online.yangxiao.user.service.UserService;
import online.yangxiao.common.util.JedisUtil;

import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/signup")
    public boolean signup(@RequestParam(value="email", required=false) String email,
                          @RequestParam(value="password", required=false) String password,
                          @RequestParam(value="username", required=false) String username) {
        logger.info("[user-server/user/signup] param: {}, {}, {}", email, password, username);

        User user = userService.findByEmail(email);
        if (user != null) {
            return  false;
        } else {
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setProfile("/images/default_profile.jpg");

            // 此处应该设置激活
            userService.regist(user);

            return true;
        }
    }

    @RequestMapping("/signin")
    public Map<String, Object> signin(@RequestParam(value="email", required=false) String email,
                                      @RequestParam(value="password", required=false) String password,
                                      @RequestParam(value="token", required=false) String token) {
        logger.info("[user-server/user/sigin] email: {}, password: {}, token: {}", email, password, token);
        Map<String, Object> ret = new HashMap<>();

        User user = userService.findByEmail(email);

        if (user == null ) {
            ret.put("status", 0);
            ret.put("success", false);
            ret.put("message", "用户不存在");
        } else {
            logger.info(user.getPassword() + ", " + password);
            if (user.getPassword().trim().equals(password.trim())) {
                ret.put("status", 1);
                ret.put("success", true);
                ret.put("message", "登录成功");
                ret.put("data", JSON.toJSONString(user));
                JedisUtil.STRINGS.setEx(email, 3600, token);    // 一小时过期
            } else {
                ret.put("status", -1);
                ret.put("success", false);
                ret.put("message", "用户密码错误");
            }
        }

        return ret;
    }

    @RequestMapping("/checkToken")
    public Map<String, Object> checkToken(@RequestParam(value="email", required=false) String email,
                                          @RequestParam(value="token", required=false) String token) {
        Map<String, Object> ret = new HashMap<>();

        String redisToken = JedisUtil.STRINGS.get(email);
        if (redisToken != null && !"".equals(redisToken)) {
            if (token.equals(redisToken)) {
                JedisUtil.STRINGS.setEx(email, 3600, token);
                ret.put("status", 1);
                ret.put("success", true);
                ret.put("message", "登录成功");
            } else {    // 登录过期或被踢下
                ret.put("status", -1);
                ret.put("success", false);
                ret.put("message", "登录过期或被踢下");
            }
        } else {
            ret.put("status", 0);
            ret.put("success", false);
            ret.put("message", "用户不存在");
        }

        return ret;
    }

    @RequestMapping("/signout")
    public Map<String, Object> signout(@RequestParam(value="email", required=false) String email,
                                       @RequestParam(value="token", required=false) String token) {
        if (email != null && JedisUtil.KEYS.exists(email)) {
            long delKey = JedisUtil.KEYS.del(email);
        }
        Map<String, Object> ret = new HashMap<>();

        ret.put("status", 1);
        ret.put("success", true);
        ret.put("message", "注销成功");

        return ret;
    }

    @RequestMapping("/test")
    public boolean test() {
        logger.info("[user/test] test");

        return true;
    }
}
