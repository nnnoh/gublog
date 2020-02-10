package com.mine.gublog.controller.api;

import com.mine.gublog.constants.ParamNameConstants;
import com.mine.gublog.constants.ResultConstants;
import com.mine.gublog.dto.BamsArticleDTO;
import com.mine.gublog.utils.PageWrapper;
import com.mine.gublog.utils.ResultWrapper;
import com.mine.gublog.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/article")
public class ArticleApiController {
    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章列表
     * @param page 分页参数
     * @param limit 分页参数
     * @param map 其他参数
     * @return
     */
    @GetMapping("/list")
    public PageWrapper<List> getArticleList(Integer page, Integer limit, @RequestParam Map<String,String> map) {
        PageWrapper<List> pageWrapper = new PageWrapper<List>();
        // 参数预处理
        if (page != null && limit != null) {
            page = (page - 1) * limit;
            if (page < 0 || limit < 0) {
                // 分页参数 错误
                page = null;
                limit = null;
            }
        }
        pageWrapper.setCode(0);
        pageWrapper.setCount(articleService.countArticle(map));
        List list = articleService.getArticleList(page, limit, map);
        // 数据处理
        if(map!=null){
            // 预览文章内容（截取前n个字符）
            if(map.containsKey(ParamNameConstants.IS_PREVIEW) && Boolean.parseBoolean((String) map.get(ParamNameConstants.IS_PREVIEW))){
                for (Object o : list) {
                    BamsArticleDTO articleDto = (BamsArticleDTO) o;
                    if (articleDto.getContent().length() > 50) {
                        articleDto.setContent(articleDto.getContent().substring(0, 50) + "...");
                    }
                }
            }
        }
        pageWrapper.setData(list);
        return pageWrapper;
    }

    /**
     * 获取文章内容
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResultWrapper<BamsArticleDTO> getArticle(@PathVariable Long id) {
        ResultWrapper<BamsArticleDTO> resultWrapper = new ResultWrapper<>();
        BamsArticleDTO bamsArticleDto = articleService.getArticle(id);
        if (bamsArticleDto != null) {
            resultWrapper.setStatus(ResultConstants.SUCCESS);
            resultWrapper.setData(bamsArticleDto);
        } else {
            resultWrapper.setStatus(ResultConstants.NOT_FOUND);
            resultWrapper.setMessage("文章 " + id + " 不存在");
        }
        return resultWrapper;
    }

    /**
     * 按分类统计发布的文章
     * @return
     */
    @RequestMapping("/list/category/count")
    public ResultWrapper countArticleByCategory(){
        ResultWrapper resultWrapper = new ResultWrapper<>();
        resultWrapper.setStatus(ResultConstants.SUCCESS);
        resultWrapper.setData(articleService.countArticleByCategory());
        return resultWrapper;
    }

    /**
     * 按分类获取发布的文章
     * @return
     */
    @RequestMapping("/list/category")
    public ResultWrapper getArticleByCategory(){
        ResultWrapper resultWrapper = new ResultWrapper<>();
        resultWrapper.setStatus(ResultConstants.SUCCESS);
        resultWrapper.setData(articleService.getArticleByCategory());
        return resultWrapper;
    }

    /**
     * 获取某分类的文章
     * @param id
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/list/category/{id}")
    public PageWrapper getArticleListByCategoryId(@PathVariable Long id, Integer page, Integer limit){
        PageWrapper<List> pageWrapper = new PageWrapper<List>();
        // 参数预处理
        if (page != null && limit != null) {
            page = (page - 1) * limit;
            if (page < 0 || limit < 0) {
                // 分页参数 错误
                page = null;
                limit = null;
            }
        }
        pageWrapper.setCode(0);
        pageWrapper.setCount(articleService.countArticleByCategoryId(id));
        pageWrapper.setData(articleService.getArticleListByCategoryId(id, page, limit));
        return pageWrapper;
    }
}