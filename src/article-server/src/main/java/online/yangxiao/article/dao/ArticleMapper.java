package online.yangxiao.article.dao;

import org.apache.ibatis.annotations.*;
import online.yangxiao.common.entity.Article;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ArticleMapper extends Mapper<Article>{
	String TABLE_NAME = "article";
	String INSERT_FIELDS = "user_id, category_id, title, pic, description, top, content, status";
	String SELECT_FIELDS = "id, user_id, category_id, title, pic, description, top, content, status, upvote, downvote, comment_count, browse, favorite, create_time";

	@Select({"Select", SELECT_FIELDS, "FROM", TABLE_NAME, "order by id desc"})
	List<Article> selectAll();

	@Update({"Update", TABLE_NAME, "set comment_count=comment_count+1 where id=#{aid}"})
	int addComment(@Param("aid") Integer aid);
}