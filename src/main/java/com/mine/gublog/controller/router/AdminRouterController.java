package com.mine.gublog.controller.router;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 后台视图
 */
@Controller
@RequestMapping("/admin")
public class AdminRouterController {
    /**
     * 后台首页
     *
     * @return
     */
    @RequestMapping("")
    public String index() {
        return "/admin/index";
    }
}
