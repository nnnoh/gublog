package com.mine.gublog.service;

import com.mine.gublog.dto.BamsArticleDTO;

import java.util.List;
import java.util.Map;

public interface ArticleService {

    /**
     * 获取文章列表
     *
     * @param page
     * @param limit
     * @param map
     * @return
     */
    List getArticleList(Integer page, Integer limit, Map<String, String> map);

    /**
     * 新增文章
     *
     * @param bamsArticleDto
     * @return
     */
    int addArticle(BamsArticleDTO bamsArticleDto);

    /**
     * 获取文章内容
     *
     * @param id
     * @return
     */
    BamsArticleDTO getArticle(Long id);

    /**
     * 更新文章信息
     *
     * @param bamsArticleDto
     * @return
     */
    int updateArticle(BamsArticleDTO bamsArticleDto);

    /**
     * 删除文章
     * @param id
     * @return
     */
    int deleteArticle(Long id);

    /**
     * 获取可用文章总数
     *
     * @param map
     * @return
     */
    Long countArticle(Map<String, String> map);

    /**
     * 根据分类id获取文章
     *
     * @param id
     * @param page
     * @param limit
     * @return
     */
    List getArticleListByCategoryId(Long id, Integer page, Integer limit);

    /**
     * 根据分类id 获取可用文章总数
     *
     * @param id
     * @return
     */
    Long countArticleByCategoryId(Long id);

    /**
     * 按分类统计发布的文章
     * @return
     */
    List countArticleByCategory();

    /**
     * 分类获取发布的文章
     * @return
     */
    List getArticleByCategory();

    /**
     * 修改某分类所有文章的分类
     * @param oldId
     * @param newId
     * @return
     */
    int updateArticleCategoryWithIdByCategoryId(Long oldId, Long newId);
}
