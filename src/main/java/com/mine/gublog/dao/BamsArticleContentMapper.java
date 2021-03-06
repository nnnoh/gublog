package com.mine.gublog.dao;

import com.mine.gublog.entity.BamsArticleContent;
import com.mine.gublog.entity.BamsArticleContentExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BamsArticleContentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bams_article_content
     *
     * @mbg.generated
     */
    long countByExample(BamsArticleContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bams_article_content
     *
     * @mbg.generated
     */
    int deleteByExample(BamsArticleContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bams_article_content
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bams_article_content
     *
     * @mbg.generated
     */
    int insert(BamsArticleContent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bams_article_content
     *
     * @mbg.generated
     */
    int insertSelective(BamsArticleContent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bams_article_content
     *
     * @mbg.generated
     */
    List<BamsArticleContent> selectByExample(BamsArticleContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bams_article_content
     *
     * @mbg.generated
     */
    BamsArticleContent selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bams_article_content
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") BamsArticleContent record, @Param("example") BamsArticleContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bams_article_content
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") BamsArticleContent record, @Param("example") BamsArticleContentExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bams_article_content
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(BamsArticleContent record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bams_article_content
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(BamsArticleContent record);
}