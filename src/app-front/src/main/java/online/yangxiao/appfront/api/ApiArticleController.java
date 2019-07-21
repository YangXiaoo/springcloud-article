package online.yangxiao.appfront.api;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import online.yangxiao.appfront.clients.CommentFeignClient;
import online.yangxiao.appfront.clients.UserFeignClient;
import online.yangxiao.common.util.HttpServletUtil;
import online.yangxiao.common.util.RestResult;
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

        RestResult<List<Article>> articleList = articleFeign.articleList();

        User user = getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("articles", (List<Article>)articleList.getResData());

        logger.info("[leave app-front-server article/list]");

        return "article/list";
    }

    @RequestMapping("/detail")
    public String articleDetail(Model model,
                                @RequestParam(value = "aid", required = true) Integer aid) {
        logger.info("[app-front-server-article/detail");
        RestResult<Article> articleRet = articleFeign.getArticle(aid);
        logger.info("[app-front-server-article/detail] articleRet.message: {}", articleRet.getResMsg());
        if (!articleRet.getResSuccess()) {
            model.addAttribute("message", (String)articleRet.getResMsg());

            return "/error/info";
        }

        RestResult<List<Comment>> commentRet = commentFeign.getComments(aid);

        model.addAttribute("article", articleRet.getResData());
        model.addAttribute("commentList", commentRet.getResData());
        model.addAttribute("user", (User)getCurrentUser());

        return "article/detail";
    }

    @RequestMapping("/add")
    public String articleAdd(Model model,
                             @RequestParam(value = "title", required = false, defaultValue="") String title,
                             @RequestParam(value = "content", required = false, defaultValue="") String content,
                             @RequestParam(value = "desc", required = false, defaultValue="") String desc) {
        logger.info("/article/add");
        model.addAttribute("user", (User)getCurrentUser());
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
            RestResult<Boolean> userRet = userFeign.checkToken((String)emailCookie.getValue(),(String)tokenCookie.getValue());
            if (!userRet.getResSuccess()) {
                model.addAttribute("message", userRet.getResMsg());

                return "/error/info";
            }
        }

        User curUser = (User)getSession().getAttribute("user");
        RestResult<Boolean> articleRet = articleFeign.addArticle(curUser.getId(), title, content, desc);

        if (!articleRet.getResSuccess()) {
            model.addAttribute("message",(String)articleRet.getResMsg());

            return "/error/info";
        }

        return "redirect:/article/list";
    }
}
