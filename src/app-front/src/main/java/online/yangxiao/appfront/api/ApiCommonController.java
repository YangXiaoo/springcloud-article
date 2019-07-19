package online.yangxiao.appfront.api;

import org.apache.ibatis.annotations.ResultMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import online.yangxiao.common.entity.User;

@Controller
public class ApiCommonController extends BaseController{
    private Logger logger = LoggerFactory.getLogger(ApiCommonController.class);

    @ResultMap("/common/appFrontHeader")
    public String appFrontHeader(Model model) {
        logger.info("header");
        User user = getCurrentUser();
        if (user != null) {
            model.addAttribute("user", user);
        }

        return "/common/appFrontHeader";
    }
}
