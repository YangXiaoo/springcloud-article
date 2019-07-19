package online.yangxiao.appfront.clients.hystrix;

import online.yangxiao.appfront.clients.CommentFeignClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommentFeignClientHystrix implements CommentFeignClient {
    @Override
    public Map<String, Object> getComments(Integer aid) {
        Map<String, Object>  ret = new HashMap<>();
        ret.put("status", -2);
        ret.put("success", false);
        ret.put("message", "comment/detail fail call, aid = " +  aid);

        return ret;
    }

    public Map<String, Object> articleReply(Integer aid, Integer pcid, Integer uid, Integer puid, String content) {
        Map<String, Object>  ret = new HashMap<>();
        ret.put("status", -2);
        ret.put("success", false);
        ret.put("message", "comment/reply fail call, aid = " +  aid);

        return ret;
    }
}
