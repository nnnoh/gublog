package com.mine.gublog.controller.router;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class BlogRouterController {
    /**
     * 博客首页
     * @return
     */
    @RequestMapping("")
    public String index(){
        return "/blog/index";
    }

    /**
     * 博客文章
     * @return
     */
    @RequestMapping("/article/*")
    public String article(){
        return "/blog/article";
    }

    /**
     * 分类文章
     * @return
     */
    @RequestMapping("/category")
    public String category(){
        return "/blog/category";
    }
}
