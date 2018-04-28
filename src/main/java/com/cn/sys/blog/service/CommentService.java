package com.cn.sys.blog.service;


import com.cn.sys.blog.dao.CommentDao;
import com.cn.sys.blog.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;


@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private SensitiveService sensitiveService;

    public void addCommet(Comment comment){
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        comment.setContent(sensitiveService.filter(comment.getContent()));
        commentDao.save(comment);
    }

    public List<Comment> getCommentsByArticleId(int articleId){
        return commentDao.findByArticleId(articleId);
    }

    public int getCommentsCount(int articleId){
        return commentDao.getCommentCountByArticleId(articleId);
    }

}
