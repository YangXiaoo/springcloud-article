package online.yangxiao.article.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

import online.yangxiao.common.entity.User;
import online.yangxiao.article.service.UserService;
import online.yangxiao.common.util.RestResult;
import online.yangxiao.common.entity.Article;
import online.yangxiao.article.service.ArticleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private Logger logger = LoggerFactory.getLogger(ArticleController.class);
	@Resource
	private ArticleService articleService;

	@Resource
    private UserService userService;

    @RequestMapping("/list")
    public RestResult<List<Article>> articleList() {
        logger.info("[request article/list]");
        // 不使用关联查找
        List<Article> articleList = articleService.findAll();
        for (Article article : articleList) {
            logger.info("[request article/list] userid: {}", article.getUserId());
            if (article.getUserId() == null){
                continue;
            }
            User user = userService.findById(article.getUserId());
            article.setUser(user);
        }
        RestResult<List<Article>> restResult = new RestResult<>(1,true, articleList);
        logger.info("[leave article/list]");
        return restResult;
    }

    @RequestMapping("/detail")
    public  RestResult<Article> detail(@RequestParam(value = "aid", required = true) Integer aid) {
        logger.info("[article-server-article/detail] aid : {}", aid);

        Article article = articleService.findById(aid);
        RestResult<Article> restResult = null;
        if (article == null) {
            restResult = new RestResult<>(0,false, "查询失败");
        } else {
            User user = userService.findById(article.getUserId());
            article.setUser(user);
            restResult = new RestResult<Article>(1,true, "查询成功", article);
        }

        return restResult;
    }

    @RequestMapping("/addComment")
    public RestResult<Boolean> addComment(@RequestParam(value = "aid", required = true) Integer aid) {
        int addRet = articleService.addComment(aid);
        RestResult<Boolean> restResult = null;
        if (addRet == 1) {
            restResult = new RestResult<>(1,true, "添加成功");
        } else {
            restResult = new RestResult<>(0,false, "添加失败");
        }
        logger.info(restResult.toString());
        return restResult;
    }

    @RequestMapping("/add")
    public RestResult<Boolean> addArticle(@RequestParam(value = "uid") Integer uid,
                                          @RequestParam(value = "title") String title,
                                          @RequestParam(value = "content") String content,
                                          @RequestParam(value = "desc") String desc) {
        logger.info("article/add");
        Article article = new Article();
        article.setUserId(uid);
        article.setTitle(title);
        article.setContent(content);
        article.setDescription(desc);
        int articleRet = articleService.addArticle(article);

        RestResult<Boolean> restResult = null;
        if (articleRet == 1) {
            restResult = new RestResult<>(1,true, "添加成功");
        } else {
            restResult = new RestResult<>(0,false, "添加失败");
        }
        logger.info(restResult.toString());
        return restResult;
    }
}

