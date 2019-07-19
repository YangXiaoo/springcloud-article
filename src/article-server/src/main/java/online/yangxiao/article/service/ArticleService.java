package online.yangxiao.article.service;

import online.yangxiao.common.entity.Article;
import java.util.List;

public interface ArticleService {
    List<Article> findAll();

    Article findById(Integer pid);

    int addComment(Integer aid);

    int addArticle(Article article);
}
