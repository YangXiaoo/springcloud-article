package online.yangxiao.common.entity;

import java.util.Date;
import java.util.List;

public class Comment {
    private Integer id;

    private Integer userId;

    private Integer articleId;

    private Integer pUserId;

    private Integer pcommentId;

    private Integer upvote;

    private Integer downvote;

    private String status;

    private String viewed;

    private Date createTime;

    private String content;

    private User user;
    // 添加comment
    private List<Comment> childComment;

    public  void setChildComment(List<Comment> comment) { this.childComment = comment; }

    public List<Comment> getChildComment() { return childComment; }

    public void setUser(User user) { this.user = user; }

    public User getUser() { return  user; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getpUserId() {
        return pUserId;
    }

    public void setpUserId(Integer pUserId) {
        this.pUserId = pUserId;
    }

    public Integer getPcommentId() {
        return pcommentId;
    }

    public void setPcommentId(Integer pcommentId) {
        this.pcommentId = pcommentId;
    }

    public Integer getUpvote() {
        return upvote;
    }

    public void setUpvote(Integer upvote) {
        this.upvote = upvote;
    }

    public Integer getDownvote() {
        return downvote;
    }

    public void setDownvote(Integer downvote) {
        this.downvote = downvote;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getViewed() {
        return viewed;
    }

    public void setViewed(String viewed) {
        this.viewed = viewed == null ? null : viewed.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}