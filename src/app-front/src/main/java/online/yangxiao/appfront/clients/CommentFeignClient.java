package online.yangxiao.appfront.clients;

import online.yangxiao.appfront.clients.hystrix.CommentFeignClientHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "comment-server", fallback = CommentFeignClientHystrix.class)
public interface CommentFeignClient {

    @RequestMapping("/comment/detail")
    Map<String, Object> getComments(@RequestParam(value = "aid", required = true) Integer aid);

    @RequestMapping("/comment/reply")
    Map<String, Object> articleReply(@RequestParam(value = "aid") Integer aid,
                                     @RequestParam(value = "pcid") Integer pcid,
                                     @RequestParam(value = "uid") Integer uid,
                                     @RequestParam(value = "puid") Integer puid,
                                     @RequestParam(value = "content") String content);
}
