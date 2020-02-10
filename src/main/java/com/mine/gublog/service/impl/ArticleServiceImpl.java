package com.mine.gublog.service.impl;

import com.mine.gublog.dao.BamsArticleContentMapper;
import com.mine.gublog.dao.BamsArticleInfoMapper;
import com.mine.gublog.dao.BamsArticleMapper;
import com.mine.gublog.dto.BamsArticleDTO;
import com.mine.gublog.entity.BamsArticle;
import com.mine.gublog.entity.BamsArticleContent;
import com.mine.gublog.entity.BamsArticleContentExample;
import com.mine.gublog.entity.BamsArticleExample;
import com.mine.gublog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private BamsArticleMapper articleMapper;

    @Autowired
    private BamsArticleContentMapper articleContentMapper;

    @Autowired
    private BamsArticleInfoMapper articleInfoMapper;

    private Map<String, String> sortFieldMap;

    public ArticleServiceImpl(){
        // 排序字段映射， where字段在sql语句中相当于做了映射
        sortFieldMap = new HashMap<>();
        sortFieldMap.put("id","ba.id");
        sortFieldMap.put("username","uu.username");
        sortFieldMap.put("status","ba.status");
        sortFieldMap.put("categoryId","ba.category_id");
        sortFieldMap.put("publishTime","ba.publish_time");
        sortFieldMap.put("editTime","ba.edit_time");
        sortFieldMap.put("createTime","ba.create_time");
    }

    @Override
    public List getArticleList(Integer page, Integer limit, Map<String,String> map) {
        String sort = map.get("sort");
        if(sort!=null){
            map.put("sort",sortFieldMap.get(sort));
        }
        List list = articleInfoMapper.selectArticleListWithPage(page, limit, map);
        return list;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    @CacheEvict(value="redisCacheManager",key="getArticleByCategory")
    public int addArticle(BamsArticleDTO bamsArticleDto) {
        // 插入信息到 bams_article, bams_article_content 表
        // 忽视传入的两表主键
        // bams_article_content
        BamsArticleContent bamsArticleContent = new BamsArticleContent();
        bamsArticleContent.setContent(bamsArticleDto.getContent());
        articleContentMapper.insertSelective(bamsArticleContent);
        // bams_article
        BamsArticle bamsArticle = new BamsArticle();
        bamsArticle.setContentId(bamsArticleContent.getId());
        bamsArticle.setTitle(bamsArticleDto.getTitle());
        bamsArticle.setUserId(bamsArticleDto.getUserId());
        bamsArticle.setCategoryId(bamsArticleDto.getCategoryId());
        bamsArticle.setCover(bamsArticleDto.getCover());
        bamsArticle.setStatus(bamsArticleDto.getStatus());
        bamsArticle.setPublishTime(bamsArticleDto.getPublishTime());
        bamsArticle.setEditTime(bamsArticleDto.getEditTime());
        bamsArticle.setCreateTime(bamsArticleDto.getCreateTime());
        int rows = articleMapper.insertSelective(bamsArticle);
        // bams_article_content   set article_id
        bamsArticleContent.setArticleId(bamsArticle.getId());
        articleContentMapper.updateByPrimaryKeySelective(bamsArticleContent);

        bamsArticleDto.setId(bamsArticle.getId());
        bamsArticleDto.setContentId(bamsArticleContent.getId());
        return rows;
    }

    @Override
    public BamsArticleDTO getArticle(Long id) {
        BamsArticleDTO bamsArticleDto = articleInfoMapper.selectArticleInfoById(id);
        return bamsArticleDto;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    @CacheEvict(value="redisCacheManager",key="getArticleByCategory")
    public int updateArticle(BamsArticleDTO bamsArticleDto) {
        // article_id, content_id，user_id, create_time 在insert后不再修改
        // bams_article_content
        if (bamsArticleDto.getContent() != null) {
            BamsArticleContent bamsArticleContent = new BamsArticleContent();
            bamsArticleContent.setId(bamsArticleDto.getContentId());
            bamsArticleContent.setContent(bamsArticleDto.getContent());
            articleContentMapper.updateByPrimaryKeySelective(bamsArticleContent);
        }
        // bams_article
        BamsArticle bamsArticle = new BamsArticle();
        bamsArticle.setId(bamsArticleDto.getId());
        bamsArticle.setTitle(bamsArticleDto.getTitle());
        bamsArticle.setCategoryId(bamsArticleDto.getCategoryId());
        bamsArticle.setCover(bamsArticleDto.getCover());
        bamsArticle.setStatus(bamsArticleDto.getStatus());
        bamsArticle.setPublishTime(bamsArticleDto.getPublishTime());
        bamsArticle.setEditTime(bamsArticleDto.getEditTime() != null ? bamsArticleDto.getEditTime() : new Date());
        // update 字段都为空会抛出异常
        int rows = articleMapper.updateByPrimaryKeySelective(bamsArticle);
        return rows;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    @CacheEvict(value="redisCacheManager",key="getArticleByCategory")
    public int deleteArticle(Long id) {
        // bams_article_content
        BamsArticleContentExample contentExample = new BamsArticleContentExample();
        BamsArticleContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andArticleIdEqualTo(id);
        articleContentMapper.deleteByExample(contentExample);
        // bams_article
        BamsArticle bamsArticle = new BamsArticle();
        int rows = articleMapper.deleteByPrimaryKey(id);
        return rows;
    }

    @Override
    public Long countArticle(Map<String, String> map) {
        return articleInfoMapper.countArticle(map);
    }

    @Override
    public List getArticleListByCategoryId(Long id, Integer page, Integer limit) {
        Map<String,String> map = new HashMap(1);
        map.put("categoryId",id.toString());
        List list = articleInfoMapper.selectArticleListWithPage(page, limit, map);
        return list;
    }

    @Override
    public Long countArticleByCategoryId(Long id) {
        return null;
    }

    @Override
    public List countArticleByCategory() {
        List list= articleInfoMapper.countArticleByCategory();
        return list;
    }

    @Override
    @Cacheable(value="redisCacheManager",key="'getArticleByCategory'")
    public List getArticleByCategory() {
        // 按分类排序获取所有文章
        Map map=new HashMap(3);
        map.put("sort",sortFieldMap.get("categoryId"));
        map.put("order","desc");
        map.put("status","2");
        List list = articleInfoMapper.selectArticleListWithPage(null, null, map);
        // 分类
        // 格式 [{categoryId:"",category:"",data:[]}]
        List res = new ArrayList();
        Map category = null;
        List data = null;
        Long categoryId = null;
        Iterator iterator = list.iterator();
        while(iterator.hasNext()) {
            BamsArticleDTO row = (BamsArticleDTO) iterator.next();
            // 字段非空
            if (!row.getCategoryId().equals(categoryId)) {
                if (categoryId != null) {
                    res.add(category);
                }
                category = new HashMap(3);
                list = new ArrayList();
                categoryId = row.getCategoryId();
                category.put("categoryId", categoryId);
                category.put("category", row.getCategory());
                category.put("data", list);
            }
            // 预览文章内容
            if (row.getContent().length() > 50) {
                row.setContent(row.getContent().substring(0, 50) + "...");
            }
            list.add(row);
        }
        if (categoryId != null) {
            res.add(category);
        }
        return res;
    }

    @Override
    @CacheEvict(value="redisCacheManager",key="getArticleByCategory")
    public int updateArticleCategoryWithIdByCategoryId(Long oldId, Long newId) {
        BamsArticleExample bamsArticleExample = new BamsArticleExample();
        BamsArticleExample.Criteria criteria = bamsArticleExample.createCriteria();
        criteria.andCategoryIdEqualTo(oldId);

        BamsArticle bamsArticle = new BamsArticle();
        bamsArticle.setCategoryId(newId);
        int rows = articleMapper.updateByExampleSelective(bamsArticle,bamsArticleExample);
        return rows;
    }
}
