package online.yangxiao.appfront.clients;

import online.yangxiao.appfront.clients.hystrix.ArticleFeignClientHystrix;
import online.yangxiao.common.util.RestResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import online.yangxiao.common.entity.Article;
import java.util.List;
import java.util.Map;

@FeignClient(value = "article-server", fallback = ArticleFeignClientHystrix.class)
public interface ArticleFeignClient {
    @RequestMapping("/article/list")
    public RestResult<List<Article>> articleList();

    @RequestMapping("/article/detail")
    public RestResult<Article> getArticle(@RequestParam(value = "aid", required = true) Integer aid);

    @RequestMapping("/article/addComment")
    public RestResult<Boolean> addComment(@RequestParam(value = "aid", required = true) Integer aid);

    @RequestMapping("/article/add")
    public RestResult<Boolean> addArticle(@RequestParam(value = "uid") Integer uid,
                                          @RequestParam(value = "title") String title,
                                          @RequestParam(value = "content") String content,
                                          @RequestParam(value = "desc") String desc);
}
