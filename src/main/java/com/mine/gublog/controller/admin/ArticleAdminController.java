package com.mine.gublog.controller.admin;

import com.mine.gublog.constants.ResultConstants;
import com.mine.gublog.dto.BamsArticleDTO;
import com.mine.gublog.utils.ResultWrapper;
import com.mine.gublog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/article")
public class ArticleAdminController {
    @Autowired
    private ArticleService articleService;

    /**
     * 新增文章
     *
     * @return
     */
    @PostMapping("")
    public ResultWrapper<BamsArticleDTO> postArticle(BamsArticleDTO bamsArticleDto) {
        ResultWrapper<BamsArticleDTO> resultWrapper = new ResultWrapper<>();
        int rows = articleService.addArticle(bamsArticleDto);
        if (rows > 0) {
            resultWrapper.setStatus(ResultConstants.SUCCESS);
            resultWrapper.setData(bamsArticleDto);
        } else {
            resultWrapper.setStatus(ResultConstants.FAIL);
            resultWrapper.setMessage(ResultConstants.COMMON_FAIL_MSG);
        }
        return resultWrapper;
    }

    /**
     * 更新文章
     *
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public ResultWrapper<BamsArticleDTO> putArticle(@PathVariable Long id, BamsArticleDTO bamsArticleDto) {
        ResultWrapper<BamsArticleDTO> resultWrapper = new ResultWrapper<BamsArticleDTO>();
        bamsArticleDto.setId(id);
        int rows = articleService.updateArticle(bamsArticleDto);
        if (rows > 0) {
            resultWrapper.setStatus(ResultConstants.SUCCESS);
            resultWrapper.setData(bamsArticleDto);
        } else {
            resultWrapper.setStatus(ResultConstants.FAIL);
            resultWrapper.setMessage(ResultConstants.COMMON_FAIL_MSG);
        }
        return resultWrapper;
    }

    /**
     * 删除文章
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResultWrapper<Long> deleteArticle(@PathVariable Long id) {
        ResultWrapper<Long> resultWrapper = new ResultWrapper<Long>();
        int rows = articleService.deleteArticle(id);
        if (rows > 0) {
            resultWrapper.setStatus(ResultConstants.SUCCESS);
            resultWrapper.setData(id);
        } else {
            resultWrapper.setStatus(ResultConstants.FAIL);
            resultWrapper.setMessage(ResultConstants.COMMON_FAIL_MSG);
        }
        return resultWrapper;
    }
}
