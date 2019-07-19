package online.yangxiao.appfront.clients.hystrix;

import online.yangxiao.appfront.clients.ArticleFeignClient;
import online.yangxiao.common.entity.Article;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ArticleFeignClientHystrix implements ArticleFeignClient{
    @Override
    public List<Article> articleList() {
        System.out.println("article-server error");
        return new ArrayList<>();
    }

    @Override
    public Map<String, Object> getArticle(Integer aid) {
        Map<String, Object>  ret = new HashMap<>();
        ret.put("status", -2);
        ret.put("success", false);
        ret.put("message", "article/detail fail call");

        return ret;
    }

    @Override
    public Map<String, Object> addComment(Integer aid) {
        Map<String, Object>  ret = new HashMap<>();
        ret.put("status", -2);
        ret.put("success", false);
        ret.put("message", "article/addComment fail call");

        return ret;
    }

    @Override
    public Map<String, Object> addArticle(Integer uid, String title, String content, String desc) {
        Map<String, Object>  ret = new HashMap<>();
        ret.put("status", -2);
        ret.put("success", false);
        ret.put("message", "article/add fail call");

        return ret;
    }
}
