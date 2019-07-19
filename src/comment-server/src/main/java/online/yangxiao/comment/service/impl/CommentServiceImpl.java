package online.yangxiao.comment.service.impl;

import online.yangxiao.comment.service.CommentService;
import online.yangxiao.common.entity.Comment;
import online.yangxiao.comment.dao.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public List<Comment> findByArticleId(Integer aid) {
        Comment comment = new Comment();
        comment.setArticleId(aid);
        comment.setPcommentId(0);

        return commentMapper.select(comment);
    }

    public List<Comment> findAllChild(Integer aid, Integer pid) {
        Comment comment = new Comment();
        comment.setArticleId(aid);
        comment.setPcommentId(pid);

        return commentMapper.select(comment);
    }

    public int insertComment(Comment comment) {
        return commentMapper.insertSelective(comment);
    }
}
