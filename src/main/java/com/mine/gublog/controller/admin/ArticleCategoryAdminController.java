package com.mine.gublog.controller.admin;

import com.mine.gublog.constants.ResultConstants;
import com.mine.gublog.utils.ResultWrapper;
import com.mine.gublog.entity.BamsArticleCategory;
import com.mine.gublog.service.ArticleCategoryService;
import com.mine.gublog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
public class ArticleCategoryAdminController {
    @Autowired
    private ArticleCategoryService articleCategoryService;

    @Autowired
    private ArticleService articleService;

    /**
     * 增加分类
     * @param bamsArticleCategory
     * @return
     */
    @PostMapping("")
    public ResultWrapper addCategory(BamsArticleCategory bamsArticleCategory){
        ResultWrapper resultWrapper = new ResultWrapper();
        int rows = articleCategoryService.addCategory(bamsArticleCategory);
        if (rows > 0) {
            resultWrapper.setStatus(ResultConstants.SUCCESS);
            resultWrapper.setData(bamsArticleCategory);
        } else {
            resultWrapper.setStatus(ResultConstants.FAIL);
            resultWrapper.setMessage(ResultConstants.COMMON_FAIL_MSG);
        }
        return resultWrapper;
    }

    /**
     * 更新分类
     *
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResultWrapper<BamsArticleCategory> putArticle(@PathVariable Long id, BamsArticleCategory bamsArticleCategory) {
        ResultWrapper<BamsArticleCategory> resultWrapper = new ResultWrapper<BamsArticleCategory>();
        bamsArticleCategory.setId(id);
        int rows = articleCategoryService.updateCategory(bamsArticleCategory);
        if (rows > 0) {
            resultWrapper.setStatus(ResultConstants.SUCCESS);
            resultWrapper.setData(bamsArticleCategory);
        } else {
            resultWrapper.setStatus(ResultConstants.FAIL);
            resultWrapper.setMessage(ResultConstants.COMMON_FAIL_MSG);
        }
        return resultWrapper;
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultWrapper<Long> deleteArticle(@PathVariable Long id) {
        ResultWrapper<Long> resultWrapper = new ResultWrapper<Long>();
        int rows = articleCategoryService.deleteCategory(id);
        if (rows > 0) {
            // 更新该分类文章信息表的分类字段值
            articleService.updateArticleCategoryWithIdByCategoryId(id, (long) 0);

            resultWrapper.setStatus(ResultConstants.SUCCESS);
            resultWrapper.setData(id);
        } else {
            resultWrapper.setStatus(ResultConstants.FAIL);
            resultWrapper.setMessage(ResultConstants.COMMON_FAIL_MSG);
        }
        return resultWrapper;
    }
}
