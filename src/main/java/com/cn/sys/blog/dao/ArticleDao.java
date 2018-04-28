package com.cn.sys.blog.dao;

import com.cn.sys.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ArticleDao extends CrudRepository<Article, Integer> {
    @Transactional
    @Modifying
    @Query("delete from Article where id = :id")
    void delArticle(@Param("id") Integer id);

    @Query("select count(id) from Article")
    int getArticleCount();

    @Query("select count(id) from Article where category= :category")
    int getArticleCountByCategory(@Param("category") String category);

    @Query(value = "select * from article ORDER BY id DESC limit :offset,:limit",nativeQuery=true)
    List<Object[]> selectLatestArticles(@Param("offset") int offset,@Param("limit") int limit);

    @Query(value = "SELECT * FROM article WHERE category=:category ORDER BY id DESC LIMIT  :offset,:limit",nativeQuery = true)
    List<Object[]> selecttArticlesByCategory(@Param("category") String category, @Param("offset") int offset,@Param("limit") int limit);

    @Transactional
    @Modifying
    @Query("update Article set commentCount=:commentCount where id=:id")
    void updateCommentCount(@Param("id") int id,@Param("commentCount") int commentCount);

}
