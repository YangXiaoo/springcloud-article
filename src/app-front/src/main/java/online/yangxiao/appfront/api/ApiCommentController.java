package online.yangxiao.appfront.api;

import online.yangxiao.appfront.clients.ArticleFeignClient;
import online.yangxiao.appfront.clients.CommentFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class ApiCommentController {
    private Logger logger = LoggerFactory.getLogger(ApiCommentController.class);

    @Autowired
    private ArticleFeignClient articleFeign;

    @Autowired
    private CommentFeignClient commentFeign;

    @RequestMapping("/reply")
    @ResponseBody
    public Map<String, Object> articleReply(@RequestParam(value = "aid") Integer aid,
                                            @RequestParam(value = "pcid") Integer pcid,
                                            @RequestParam(value = "uid") Integer uid,
                                            @RequestParam(value = "puid") Integer puid,
                                            @RequestParam(value = "content") String content) {
        logger.info("[comment/reply]");

        content.replaceAll("[\n\r]", "<br>");
        Map<String, Object> commentRet = commentFeign.articleReply(aid, pcid, uid, puid, content);
        Map<String, Object> articleRet = articleFeign.addComment(aid);

        if (!(Boolean)commentRet.get("success") || !(Boolean)articleRet.get("success")) {
            logger.info("commentRet.get(\"success\") : {}, articleRet.get(\"success\"): {}",(Boolean)commentRet.get("success"), (Boolean)articleRet.get("success") );
            commentRet.put("status", -1);
            commentRet.put("message", "评论失败！");
        }
        return commentRet;
    }
}
