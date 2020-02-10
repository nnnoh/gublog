package com.mine.gublog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章dto
 */
@Data
public class BamsArticleDTO implements Serializable {
    /**
     * 文章id
     * from BAMS_ARTICLE
     */
    private Long id;

    /**
     * 标题
     * from BAMS_ARTICLE
     */
    private String title;

    /**
     * 作者id
     * from BAMS_ARTICLE
     */
    private Long userId;

    /**
     * 作者username
     */
    private String username;

    /**
     * 分类id
     * from BAMS_ARTICLE
     */
    private Long categoryId;

    /**
     * 分类
     * from BAMS_ARTICLE_CATEGORY
     */
    private String category;

    /**
     * 封面图片
     * from BAMS_ARTICLE
     */
    private String cover;

    /**
     * 状态 0:禁用, 1:暂存, 2:发布
     * from BAMS_ARTICLE
     */
    private Byte status;

    /**
     * 发布时间
     * from BAMS_ARTICLE
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;

    /**
     * 上次修改时间
     * from BAMS_ARTICLE
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editTime;

    /**
     * 创建时间
     * from BAMS_ARTICLE
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 文章内容id
     * from BAMS_ARTICLE_CONTENT
     */
    private Long contentId;

    /**
     * 文章内容
     * from BAMS_ARTICLE_CONTENT
     */
    private String content;
}
