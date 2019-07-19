package online.yangxiao.user.dao;

import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import online.yangxiao.common.entity.Comment;

@org.apache.ibatis.annotations.Mapper
public interface CommentMapper extends Mapper<Comment>{
}
