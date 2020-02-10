package com.mine.gublog.dao;

import com.mine.gublog.dto.BamsArticleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 文章相关表连查操作
 *
 */
@Mapper
public interface BamsArticleInfoMapper {
    /**
     * 查询ArticleDto信息
     * @param id
     * @return
     */
    BamsArticleDTO selectArticleInfoById(Long id);

    /**
     * 查询ArticlePreviewDto列表信息
     * page 及 limit 不为 null，则分页
     * @param page 分页参数
     * @param limit 分页参数
     * @param map 支持 like title，usernam； equal categoryId; sort
     * @return
     */
    List selectArticleListWithPage(Integer page, Integer limit,@Param("paramMap") Map<String, String> map);

    /**
     * 统计文章数量
     * @param map
     * @return
     */
    Long countArticle(@Param("paramMap")Map<String, String> map);

    /**
     * 按分类统计发布的文章
     * @return
     */
    List countArticleByCategory();
}
