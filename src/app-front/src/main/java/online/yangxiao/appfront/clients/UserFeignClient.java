package online.yangxiao.appfront.clients;

import online.yangxiao.appfront.clients.hystrix.UserFeignClientHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import online.yangxiao.common.entity.Article;
import online.yangxiao.common.entity.User;
import java.util.List;
import java.util.Map;

@FeignClient(value = "user-server", fallback = UserFeignClientHystrix.class)
public interface UserFeignClient {

    @RequestMapping("/user/signup")
    public boolean signup(@RequestParam(value="email", required=false) String email,
                          @RequestParam(value="password", required=false) String password,
                          @RequestParam(value="username", required=false) String username);

    @RequestMapping("/user/signin")
    public Map<String, Object> signin(@RequestParam(value="email", required=false) String email,
                                      @RequestParam(value="password", required=false) String password,
                                      @RequestParam(value="token", required=false) String token);

    @RequestMapping("/user/checkToken")
    public Map<String, Object> checkToken(@RequestParam(value="email", required=false) String email,
                                          @RequestParam(value="token", required=false) String token);

    @RequestMapping("/user/signout")
    public Map<String, Object> signout(@RequestParam(value="email", required=false) String email,
                                       @RequestParam(value="token", required=false) String token);

    @RequestMapping("/user/test")
    public boolean test();
}
