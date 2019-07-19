package online.yangxiao.comment.service;

import online.yangxiao.common.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> findByArticleId(Integer aid);

    List<Comment> findAllChild(Integer aid, Integer pid);

    int insertComment(Comment comment);
}
