package online.yangxiao.appfront.clients.hystrix;

import online.yangxiao.appfront.clients.UserFeignClient;
import online.yangxiao.common.entity.Article;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import online.yangxiao.common.entity.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserFeignClientHystrix implements UserFeignClient{
    @Override
    public boolean signup(String email, String password, String username) {
        System.out.println("signup fail callback");
        return false;
    }

    @Override
    public Map<String, Object> signin(String email, String password, String token) {
        Map<String, Object>  ret = new HashMap<>();
        ret.put("status", -2);
        ret.put("success", false);
        ret.put("message", "user/signin fail call");

        return ret;
    }

    @Override
    public Map<String, Object> checkToken(String email, String token) {
        Map<String, Object>  ret = new HashMap<>();
        ret.put("status", -2);
        ret.put("success", false);
        ret.put("message", "user/checkToken fail call");

        return ret;
    }

    @Override
    public Map<String, Object> signout(String email, String token) {
        Map<String, Object>  ret = new HashMap<>();
        ret.put("status", -2);
        ret.put("success", false);
        ret.put("message", "user/signout fail call");

        return ret;
    }

    @Override
    public boolean test() {
        System.out.println("test fail callback");

        return false;
    }
}
