package online.yangxiao.article.controller;

import online.yangxiao.common.entity.User;
import online.yangxiao.article.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

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
    public List<Article> articleList() {
        logger.info("[request article/list]");
        // 不使用关联查找
        List<Article> articleList = articleService.findAll();
        logger.info("[leave article/list]");
        return articleList;
    }

    @RequestMapping("/detail")
    public Map<String, Object> detail(@RequestParam(value = "aid", required = true) Integer aid) {
        logger.info("[article-server-article/detail] aid : {}", aid);

        Article article = articleService.findById(aid);

        Map<String, Object> ret = new HashMap<>();
        if (article == null) {
            ret.put("status", -1);
            ret.put("success", false);
            ret.put("message", "查询失败");
        } else {
            User user = userService.findById(article.getUserId());
            article.setUser(user);
            ret.put("status", 1);
            ret.put("success", true);
            ret.put("message", "查询成功");
            ret.put("data", JSON.toJSONString(article));
        }

        return ret;
    }

    @RequestMapping("/addComment")
    public Map<String, Object> addComment(@RequestParam(value = "aid", required = true) Integer aid) {
        int addRet = articleService.addComment(aid);
        Map<String, Object> ret = new HashMap<>();

        if (addRet == 1) {
            ret.put("status", 1);
            ret.put("success", true);
            ret.put("message", "添加评论成功");
        } else {
            ret.put("status", -1);
            ret.put("success", false);
            ret.put("message", "添加评论失败");
        }
        logger.info(ret.toString());
        return ret;
    }

    @RequestMapping("/add")
    public Map<String, Object> addArticle(@RequestParam(value = "uid") Integer uid,
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

        Map<String, Object> ret = new HashMap<>();
        if (articleRet == 1) {
            ret.put("status", 1);
            ret.put("success", true);
            ret.put("message", "添加成功");
        } else {
            ret.put("status", -1);
            ret.put("success", false);
            ret.put("message", "添加失败");
        }
        logger.info(ret.toString());
        return ret;
    }
}

