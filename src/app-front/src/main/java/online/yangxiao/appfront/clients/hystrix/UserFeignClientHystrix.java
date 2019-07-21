package online.yangxiao.appfront.clients.hystrix;

import online.yangxiao.appfront.clients.UserFeignClient;
import online.yangxiao.common.entity.Article;
import online.yangxiao.common.util.RestResult;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import online.yangxiao.common.entity.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserFeignClientHystrix implements UserFeignClient{
    @Override
    public RestResult<Boolean> signup(String email, String password, String username) {
        RestResult<Boolean> restResult = new RestResult<>(-2, false, "signup-api调用失败");
        return restResult;
    }

    @Override
    public RestResult<User> signin(String email, String password, String token) {
        RestResult<User> restResult = new RestResult<>(-2, false, "signin-api调用失败");
        return restResult;
    }

    @Override
    public RestResult<Boolean> checkToken(String email, String token) {
        RestResult<Boolean> restResult = new RestResult<>(-2, false, "checkToken-api调用失败");
        return restResult;
    }

    @Override
    public RestResult<Boolean> signout(String email, String token) {
        RestResult<Boolean> restResult = new RestResult<>(-2, false, "signout-api调用失败");
        return restResult;
    }

    @Override
    public boolean test() {
        System.out.println("test fail callback");

        return false;
    }
}
