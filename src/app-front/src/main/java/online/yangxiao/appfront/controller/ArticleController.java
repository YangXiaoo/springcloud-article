package online.yangxiao.appfront.controller;


// import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
        import online.yangxiao.appfront.entity.Article;
import online.yangxiao.appfront.service.ArticleService;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ArticleController {

	@Resource
	private ArticleService articleService;


    @RequestMapping("/article")
    public String article(Model model, 
                          @RequestParam(value="aid", required=false) Integer aid) {
        System.out.println("article...");
        // 不使用关联查找
        List<Article> article = articleService.findAll();
        System.out.println("article] after query");
        model.addAttribute("articles", article);
        return "list";
    }
	


}

