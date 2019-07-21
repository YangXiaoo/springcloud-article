package online.yangxiao.comment.controller;

import com.alibaba.fastjson.JSON;
import online.yangxiao.comment.service.CommentService;
import online.yangxiao.comment.service.UserService;
import online.yangxiao.common.entity.Comment;
import online.yangxiao.common.entity.User;
import online.yangxiao.common.util.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;


    // need todo 分页查询
    @RequestMapping("/detail")
    public RestResult<List<Comment>> getComments(@RequestParam(value = "aid", required = true) Integer aid) {
        logger.info("comment-server/comment/detail");

        List<Comment> commentList = commentService.findByArticleId(aid);
        for (Comment comment : commentList) {
            User curUser=  userService.findById(comment.getUserId());
            comment.setUser(curUser);
            List<Comment> childs = commentService.findAllChild(aid, comment.getId());

            for (Comment childComment : childs) {
                User childUser=  userService.findById(childComment.getUserId());
                childComment.setUser(childUser);
                if (childComment.getpUserId() != comment.getUserId()) {
                    User pUser = userService.findById((childComment.getpUserId()));
                    childComment.setPuser(pUser);
                }
            }
            comment.setChildComment(childs);
        }

        RestResult<List<Comment>> restResult = new RestResult<>(1,true, "查询成功", commentList);

        return restResult;
    }

    @RequestMapping("/reply")
    public RestResult<Boolean> articleReply(@RequestParam(value = "aid") Integer aid,
                                            @RequestParam(value = "pcid") Integer pcid,
                                            @RequestParam(value = "uid") Integer uid,
                                            @RequestParam(value = "puid") Integer puid,
                                            @RequestParam(value = "content") String content) {
        logger.info("[comment-server/reply]");

        Comment comment = new Comment();
        comment.setUserId(uid);
        comment.setArticleId(aid);
        comment.setPcommentId(pcid);
        comment.setpUserId(puid);
        comment.setContent(content);
        int insertRet = commentService.insertComment(comment);

        RestResult<Boolean> restResult = new RestResult<>();
        if (insertRet != 1) {
            restResult = new RestResult<>(0,false, "查询失败");
        }

        return restResult;
    }
}
