package online.yangxiao.appfront.clients.hystrix;

import online.yangxiao.appfront.clients.ArticleFeignClient;
import online.yangxiao.common.entity.Article;
import online.yangxiao.common.util.RestResult;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ArticleFeignClientHystrix implements ArticleFeignClient{
    @Override
    public RestResult<List<Article>> articleList() {
        RestResult<List<Article>> restResult = new RestResult<>(-2, false, "/article/list-api调用失败, aid: ");
        return restResult;
    }

    @Override
    public RestResult<Article> getArticle(Integer aid) {
        RestResult<Article> restResult = new RestResult<>(-2, false, "/article/detail-api调用失败, aid: ");
        return restResult;
    }

    @Override
    public RestResult<Boolean> addComment(Integer aid) {
        RestResult<Boolean> restResult = new RestResult<>(-2, false, "/article/addComment-api调用失败, aid: ");
        return restResult;
    }

    @Override
    public RestResult<Boolean> addArticle(Integer uid, String title, String content, String desc) {
        RestResult<Boolean> restResult = new RestResult<>(-2, false, "/article/add-api调用失败, aid: ");
        return restResult;
    }
}
