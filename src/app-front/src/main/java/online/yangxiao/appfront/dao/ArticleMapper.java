package online.yangxiao.appfront.dao;

import org.apache.ibatis.annotations.*;
import online.yangxiao.appfront.entity.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {
	String TABLE_NAME = "article";
	String INSERT_FIELDS = "user_id, category_id, title, pic, description, top, content, status";
	String SELECT_FIELDS = "id, user_id, category_id, title, pic, description, top, content, status, upvote, downvote, comment_count, browse, favorite, create_time";

	@Select({"Select", SELECT_FIELDS, "FROM", TABLE_NAME, "order by id desc"})
	List<Article> selectAll();

}