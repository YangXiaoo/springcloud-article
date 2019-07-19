package online.yangxiao.appfront.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/demo")
public class ApiDemoController {
    private Logger logger = LoggerFactory.getLogger(ApiDemoController.class);

//    @Value("${message}")
    private String message;

    @RequestMapping("config-client")
    public String configClient(Model model) {
        logger.info("[demo/config-cient] request, message: {}", message);
        model.addAttribute("message", message);

        return "demo/config-client";
    }
}
