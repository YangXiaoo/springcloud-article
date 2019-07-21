package online.yangxiao.appfront.api;


import online.yangxiao.common.util.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import online.yangxiao.common.entity.User;
import online.yangxiao.appfront.clients.UserFeignClient;
import online.yangxiao.common.util.RandomGen;
import online.yangxiao.common.util.HttpServletUtil;

import java.util.LinkedHashMap;
import java.util.Map;


@Controller
@RequestMapping("/user")
public class ApiUserController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(ApiUserController.class);

    @Resource
    private UserFeignClient userFeign;

    @RequestMapping("/signup")
    public String signup(Model model,
                         @RequestParam(value="email", required=false) String email,
                         @RequestParam(value="password", required=false) String password,
                         @RequestParam(value="username", required=false) String username) {
        model.addAttribute("user", (User)getCurrentUser());
        if (email == null || password == null || username == null) {
            return "user/signup";
        }
        logger.info("[request app-front-server user/sigup] email: {}, username: {}, password: {}", email, username, password);
        RestResult<Boolean>  ret = userFeign.signup(email, password, username);
        logger.info("[request app-front-server user/sigup] userFeign.signup(email, password, username): {}", ret);
        if (ret.getResSuccess()) {
            return "user/signin";
        }

        return "user/signup";
    }

    @RequestMapping("signin")
    public String signin(Model model,
                         @RequestParam(value="email", required=false) String email,
                         @RequestParam(value="password", required=false) String password) {
        logger.info("[request app-front-server user/sigin]");
        if (email == null || password == null) {
            model.addAttribute("user", (User)getCurrentUser());
            return "user/signin";
        }
        logger.info("before userFeign");
        String token = RandomGen.getToken(email);

        RestResult<User> userFeignRet  = userFeign.signin(email, password, token);
        logger.info("[request app-front-server user/sigin] email: {}, password: {}, message: {}, success: {}", email, password, userFeignRet.getResMsg(), userFeignRet.getResSuccess());
        if (!userFeignRet.getResSuccess()) {
            return "user/signin";
        } else {
            logger.info("user.toString(): {}", userFeignRet.getResData().toString());
            User curUser = userFeignRet.getResData();
            // 存储session
            getSession().setAttribute("user", curUser);
            getSession().setAttribute("email", email);
            getSession().setAttribute("token", token);
            // 存储cookie
            Cookie usernameCookie = new Cookie("username", curUser.getUsername());
            usernameCookie.setPath("/");
            usernameCookie.setMaxAge(60 * 60);
            usernameCookie.setDomain("localhost");
            getResponse().addCookie(usernameCookie);

            Cookie emailCookie = new Cookie("email", curUser.getEmail());
            emailCookie.setPath("/");
            emailCookie.setMaxAge(60 * 60);
            emailCookie.setDomain("localhost");
            getResponse().addCookie(emailCookie);

            Cookie userIdCookie = new Cookie("userId", curUser.getId().toString());
            userIdCookie.setPath("/");
            userIdCookie.setMaxAge(60 * 60);
            userIdCookie.setDomain("localhost");
            getResponse().addCookie(userIdCookie);

            Cookie tokenCookie = new Cookie("token", token);
            tokenCookie.setPath("/");
            tokenCookie.setMaxAge(60 * 60);
            tokenCookie.setDomain("localhost");
            getResponse().addCookie(tokenCookie);

            model.addAttribute("user", curUser);
            logger.info("[request app-front-server user/sigin] signin successful");

            return "redirect:/article/list";
        }
    }

    @RequestMapping("signout")
    public String signout(Model model) {
        logger.info("[request app-front-server user/sigout] starting...");
        String email = (String) getSession().getAttribute("email");
        String token = (String) getSession().getAttribute("token");

        if (email != null && token != null) {
            RestResult<Boolean> userFeignRet = userFeign.signout(email, token);
            if (!userFeignRet.getResSuccess()) {
                model.addAttribute("message", userFeignRet.getResMsg());
                return "error/info";
            }
            getSession().setAttribute("user", null);
            getSession().setAttribute("email", null);
            getSession().setAttribute("token", null);
        }

        Map<String, Cookie> cookieMap = HttpServletUtil.getCookieMap(getRequest());
        for (Cookie cookie : cookieMap.values()) {
            cookie.setValue(null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            getResponse().addCookie(cookie);
        }
        logger.info("用户注销成功");
        return "redirect:/article/list";
    }


    @RequestMapping("/test")
    public String test(Model model) {
        boolean ret = userFeign.test();

        model.addAttribute("message", ret);

        return "/demo/config-client";
    }

}
