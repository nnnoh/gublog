package com.mine.gublog.service.impl;

import com.mine.gublog.dao.BamsArticleCategoryMapper;
import com.mine.gublog.entity.BamsArticleCategory;
import com.mine.gublog.entity.BamsArticleCategoryExample;
import com.mine.gublog.service.ArticleCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ArticleCategoryServiceImpl implements ArticleCategoryService {
    @Autowired
    private BamsArticleCategoryMapper articleCategoryMapper;

    @Override
    public int addCategory(BamsArticleCategory bamsArticleCategory) {
        int rows=articleCategoryMapper.insertSelective(bamsArticleCategory);
        return rows;
    }

    @Override
    public Long countCategory(Map<String,String> map) {
        BamsArticleCategoryExample categoryExample = new BamsArticleCategoryExample();
        BamsArticleCategoryExample.Criteria criteria= categoryExample.createCriteria();
        String value = map.get("name");
        if(value!=null){
            criteria.andNameLike(value);
        }
        return articleCategoryMapper.countByExample(categoryExample);
    }

    @Override
    public List getCategoryList(Integer page, Integer limit, Map<String,String> map) {
        List list = articleCategoryMapper.selectCategoryListWithPage(page, limit, map);
        return list;
    }

    @Override
    public int deleteCategory(Long id) {
        int rows = articleCategoryMapper.deleteByPrimaryKey(id);
        return rows;
    }

    @Override
    public int updateCategory(BamsArticleCategory bamsArticleCategory) {
        int rows = articleCategoryMapper.updateByPrimaryKey(bamsArticleCategory);
        return rows;
    }
}
