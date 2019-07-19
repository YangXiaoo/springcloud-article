package online.yangxiao.appfront.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import online.yangxiao.appfront.dao.ArticleMapper;
import online.yangxiao.appfront.entity.Article;
import online.yangxiao.appfront.service.ArticleService;

//import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleMapper articleMapper;

    public List<Article> findAll() {
        return articleMapper.selectAll();
    }
}
