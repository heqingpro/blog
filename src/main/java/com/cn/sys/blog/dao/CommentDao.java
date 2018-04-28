package com.cn.sys.blog.dao;

import com.cn.sys.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CommentDao extends CrudRepository<Comment, Integer> {
    @Transactional
    @Modifying
    @Query("delete from Comment where id = :id")
    void delComment(@Param("id") Integer id);

    List<Comment> findByArticleId(@Param("articleId") int articleId);

    @Query("select count(id) from Comment where articleId=:articleId")
    int getCommentCountByArticleId(@Param("articleId") int articleId);
}
