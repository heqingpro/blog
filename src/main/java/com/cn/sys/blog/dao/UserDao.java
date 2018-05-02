package com.cn.sys.blog.dao;

import com.cn.sys.blog.entity.User;
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
public interface UserDao extends CrudRepository<User, Integer> {
    User findByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("delete from User where id = :id")
    void delUser(@Param("id") Integer id);

}
