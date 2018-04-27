package com.cn.sys.blog.dao;

import com.cn.sys.blog.entity.Article;
import com.cn.sys.blog.entity.ArticleTag;
import com.cn.sys.blog.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ArticleTagDao extends CrudRepository<ArticleTag, Integer>,JpaSpecificationExecutor {
    @Transactional
    @Modifying
    @Query("delete from ArticleTag where id = :id")
    void delArticleTag(@Param("id") Integer id);

    @Query("select count(id) from Article where id in (select articleId from ArticleTag where tagId=:tagId)")
    @Modifying
    int selectArticleCountByTagId(int tagId);

    @Query(value = "SELECT * FROM article WHERE id IN (SELECT article_id from article_tag where tag_id=:tagId) LIMIT :offset,:limit",nativeQuery = true)
    @Modifying
    List<Article> selectByTagId(int tagId,int offset,int limit);

    @Query(value = "select * from tag where id in(select tag_id from article_tag where article_id=:articleId)",nativeQuery = true)
    List<Tag> selectByArticleId(@Param("articleId") int articleId);
}
