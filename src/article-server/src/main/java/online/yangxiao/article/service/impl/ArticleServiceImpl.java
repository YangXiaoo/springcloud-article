package online.yangxiao.article.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import online.yangxiao.article.dao.ArticleMapper;
import online.yangxiao.common.entity.Article;
import online.yangxiao.article.service.ArticleService;

//import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleMapper articleMapper;

    public List<Article> findAll() {
        return articleMapper.selectAll();
    }

    public Article findById(Integer aid) {
        Article article = new Article();
        article.setId(aid);
        return articleMapper.selectOne(article);
    }

    public int addComment(Integer aid) {
        return articleMapper.addComment(aid);
    }

    public int addArticle(Article article) {
        return articleMapper.insert(article);
    }

}
