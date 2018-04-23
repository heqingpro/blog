package com.cn.sys.blog.dao;

import com.cn.sys.blog.entity.LoginTicket;
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
public interface LoginTicketDao extends CrudRepository<LoginTicket, Integer> {
    List<LoginTicket> findByNameLike(String name);

    Page<LoginTicket> findAll(Pageable pageable);

    @Transactional
    @Modifying
    @Query("select name from LoginTicket ")
    List<String> queryAllNames();

    @Transactional
    @Modifying
    @Query("delete from LoginTicket where id = :id")
    void delUser(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("update LoginTicket set status = :status where ticket = :ticket")
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);
}
