package online.yangxiao.appfront.api;

import com.alibaba.fastjson.JSON;
import online.yangxiao.appfront.clients.CommentFeignClient;
import online.yangxiao.appfront.clients.UserFeignClient;
import online.yangxiao.common.util.HttpServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import online.yangxiao.appfront.api.BaseController;
import online.yangxiao.common.entity.User;
import online.yangxiao.common.entity.Article;
import online.yangxiao.common.entity.Comment;
import online.yangxiao.appfront.clients.ArticleFeignClient;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ApiArticleController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(ApiArticleController.class);

    @Autowired
    private ArticleFeignClient articleFeign;

    @Autowired
    private UserFeignClient userFeign;

    @Autowired
    private CommentFeignClient commentFeign;

    @RequestMapping("/list")
    public String articleList(Model model) {
        logger.info("[request app-front-server article/list]");

        List<Article> articleList = articleFeign.articleList();

        User user = getCurrentUser();
        if (user != null) {
            model.addAttribute("user", user);
        }
        model.addAttribute("articles", articleList);

        logger.info("[leave app-front-server article/list]");

        return "article/list";
    }

    @RequestMapping("/detail")
    public String articleDetail(Model model,
                                @RequestParam(value = "aid", required = true) Integer aid) {
        logger.info("[app-front-server-article/detail");
        Map<String, Object> articleRet = articleFeign.getArticle(aid);
        logger.info("[app-front-server-article/detail] articleRet.message: {}", articleRet.get("message"));
        if (!(Boolean)articleRet.get("success")) {
            model.addAttribute("message", (String)articleRet.get("message"));

            return "/error/info";
        }

        Map<String, Object> commentRet = commentFeign.getComments(aid);

        model.addAttribute("article", JSON.parseObject((String) articleRet.get("data"), Article.class));
        model.addAttribute("commentList", JSON.parseObject((String) commentRet.get("data"), List.class));
        model.addAttribute("user", getCurrentUser());

        return "article/detail";
    }

    @RequestMapping("/add")
    public String articleAdd(Model model,
                             @RequestParam(value = "title", required = false, defaultValue="") String title,
                             @RequestParam(value = "content", required = false, defaultValue="") String content,
                             @RequestParam(value = "desc", required = false, defaultValue="") String desc) {
        logger.info("/article/add");

        if ("".equals(title) || "".equals(content)) {
            return "/article/add";
        }
        Map<String, Cookie> cookieMap = HttpServletUtil.getCookieMap(getRequest());
        Cookie emailCookie = cookieMap.get("email");
        Cookie tokenCookie = cookieMap.get("token");
        if (emailCookie == null || tokenCookie == null) {
            model.addAttribute("message", "非法操作");

            return "/error/info";
        } else {
            Map<String, Object> userRet = userFeign.checkToken((String)emailCookie.getValue(),(String)tokenCookie.getValue());
            if (!(Boolean)userRet.get("success")) {
                model.addAttribute("message", (String)userRet.get("message"));

                return "/error/info";
            }
        }

        User curUser = (User)getSession().getAttribute("user");
        Map<String, Object> articleRet = articleFeign.addArticle(curUser.getId(), title, content, desc);

        if (!(Boolean)articleRet.get("success")) {
            model.addAttribute("message", (String)articleRet.get("message"));

            return "/error/info";
        }

        return "redirect:/article/list";
    }
}
