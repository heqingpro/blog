package com.cn.sys.blog.dao;

import com.cn.sys.blog.entity.Tag;
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
public interface TagDao extends CrudRepository<Tag, Integer> {
    List<Tag> findByName(@Param("name") String name);
    @Transactional
    @Modifying
    @Query("delete from Tag where id = :id")
    void delTag(@Param("id") Integer id);

    @Query(value = "update tag set count=:count where id=:tagId",nativeQuery = true)
    void updateCount(@Param("tagId") int tagId,@Param("count") int count);
}
