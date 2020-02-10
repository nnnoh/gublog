package com.mine.gublog.service;

import com.mine.gublog.entity.BamsArticleCategory;

import java.util.List;
import java.util.Map;

public interface ArticleCategoryService {

    /**
     * 增加分类
     * @param bamsArticleCategory
     * @return
     */
    int addCategory(BamsArticleCategory bamsArticleCategory);

    /**
     * 统计分类个数
     * @param map
     * @return
     */
    Long countCategory(Map<String,String> map);

    /**
     * 获取分类列表
     * @param page
     * @param limit
     * @param map
     * @return
     */
    List getCategoryList(Integer page, Integer limit, Map<String,String> map);

    /**
     * 删除分类
     * @param id
     * @return
     */
    int deleteCategory(Long id);

    /**
     * 更新分类
     * @param bamsArticleCategory
     * @return
     */
    int updateCategory(BamsArticleCategory bamsArticleCategory);
}
