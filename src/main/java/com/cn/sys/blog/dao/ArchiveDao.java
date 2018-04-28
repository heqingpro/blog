package com.cn.sys.blog.dao;

import com.cn.sys.blog.entity.Archive;
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
public interface ArchiveDao extends CrudRepository<Archive, Integer> {

    @Transactional
    @Modifying
    @Query("delete from Archive where id = :id")
    void delArchive(@Param("id") Integer id);
    //
    @Query(value = "SELECT article.id AS articleId,article.title AS articleTitle, YEAR(article.created_date) AS year,MONTH(article.created_date) AS month " +
            "FROM article GROUP BY YEAR(article.created_date), MONTH(article.created_date),article.id,article.title order by id desc",nativeQuery = true)
    List<Object[]> seletArticleGroupByTime();
}
