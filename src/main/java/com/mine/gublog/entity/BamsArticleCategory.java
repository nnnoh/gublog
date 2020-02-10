package com.mine.gublog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Database Table Remarks:
 *   文章分类表
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table bams_article_category
 *
 * update: 时间格式转换
 * @mbg.generated do_not_delete_during_merge
 */
public class BamsArticleCategory {
    /**
     * Database Column Remarks:
     *   主题
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bams_article_category.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     * Database Column Remarks:
     *   分类名称
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bams_article_category.name
     *
     * @mbg.generated
     */
    private String name;

    /**
     * Database Column Remarks:
     *   分类创建时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bams_article_category.create_time
     *
     * @mbg.generated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * Database Column Remarks:
     *   分类修改时间
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bams_article_category.update_time
     *
     * @mbg.generated
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * Database Column Remarks:
     *   状态 0:禁用, 1:启用
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bams_article_category.status
     *
     * @mbg.generated
     */
    private Boolean status;

    /**
     * Database Column Remarks:
     *   用户id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column bams_article_category.user_id
     *
     * @mbg.generated
     */
    private Long userId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bams_article_category.id
     *
     * @return the value of bams_article_category.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bams_article_category.id
     *
     * @param id the value for bams_article_category.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bams_article_category.name
     *
     * @return the value of bams_article_category.name
     *
     * @mbg.generated
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bams_article_category.name
     *
     * @param name the value for bams_article_category.name
     *
     * @mbg.generated
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bams_article_category.create_time
     *
     * @return the value of bams_article_category.create_time
     *
     * @mbg.generated
     */
    public Date getcreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bams_article_category.create_time
     *
     * @param createTime the value for bams_article_category.create_time
     *
     * @mbg.generated
     */
    public void setcreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bams_article_category.update_time
     *
     * @return the value of bams_article_category.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bams_article_category.update_time
     *
     * @param updateTime the value for bams_article_category.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bams_article_category.status
     *
     * @return the value of bams_article_category.status
     *
     * @mbg.generated
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bams_article_category.status
     *
     * @param status the value for bams_article_category.status
     *
     * @mbg.generated
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column bams_article_category.user_id
     *
     * @return the value of bams_article_category.user_id
     *
     * @mbg.generated
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column bams_article_category.user_id
     *
     * @param userId the value for bams_article_category.user_id
     *
     * @mbg.generated
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}