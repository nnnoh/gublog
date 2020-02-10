package com.mine.gublog.controller.router;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 路径路由
 */
@Controller
public class SiteRouterController {

    /**
     * 首页
     * @return
     */
    @RequestMapping("")
    public String index(){
        return "/site/index";
    }

    /**
     * 登录注册
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "/site/login";
    }
}
