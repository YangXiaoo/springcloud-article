package online.yangxiao.appfront.clients.hystrix;

import online.yangxiao.appfront.clients.CommentFeignClient;
import online.yangxiao.common.entity.Comment;
import online.yangxiao.common.util.RestResult;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommentFeignClientHystrix implements CommentFeignClient {
    @Override
    public RestResult<List<Comment>> getComments(Integer aid) {
        RestResult<List<Comment>> restResult = new RestResult<>(-2, false, "/comment/detail-api调用失败, aid: " + aid);
        return restResult;
    }

    public RestResult<Boolean> articleReply(Integer aid, Integer pcid, Integer uid, Integer puid, String content) {
        RestResult<Boolean> restResult = new RestResult<>(-2, false, "/comment/reply-api调用失败, aid: " + aid);
        return restResult;
    }
}
