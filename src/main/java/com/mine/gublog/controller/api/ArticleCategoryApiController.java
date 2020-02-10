package com.mine.gublog.controller.api;

import com.mine.gublog.utils.PageWrapper;
import com.mine.gublog.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/category")
public class ArticleCategoryApiController {

    @Autowired
    private ArticleCategoryService articleCategoryService;

    /**
     * 获取分类信息列表
     * @param page
     * @param limit
     * @param map
     * @return
     */
    @GetMapping("/list")
    public PageWrapper getCategoryList(Integer page, Integer limit, @RequestParam Map<String,String> map) {
            PageWrapper pageWrapper = new PageWrapper<List>();
            if (page != null && limit != null) {
                if (page < 0 || limit < 0) {
                    // 分页参数 错误
                    page = null;
                    limit = null;
                }
                page = (page - 1) * limit;
            }
            pageWrapper.setCode(0);
            pageWrapper.setCount(articleCategoryService.countCategory(map));
            pageWrapper.setData(articleCategoryService.getCategoryList(page, limit, map));
            return pageWrapper;
    }
}
