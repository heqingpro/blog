package com.cn.sys.blog.controller;


import com.cn.sys.blog.aync.EventModel;
import com.cn.sys.blog.aync.EventProducer;
import com.cn.sys.blog.aync.EventType;
import com.cn.sys.blog.entity.Comment;
import com.cn.sys.blog.entity.HostHolder;
import com.cn.sys.blog.service.ArticleService;
import com.cn.sys.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;


@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(path = "/addComment/{articleId}")
    public String addComment(@PathVariable("articleId") int articleId, @RequestParam("content")String content
    ,@RequestParam(value = "next",required = false)String next){
        Comment comment = new Comment();
        if (hostHolder.getUser()==null)
            return "redirect:/in?next=/article/"+articleId;
        else
            comment.setUserId(hostHolder.getUser().getId());
        comment.setContent(content);
        comment.setCreatedDate(new Date());
        comment.setArticleId(articleId);
        comment.setStatus(0);
        commentService.addCommet(comment);

        int count = commentService.getCommentsCount(articleId);
        articleService.updateCommentCount(articleId,count);

        eventProducer.fireEvent(new EventModel().setType(EventType.COMMENT)
                .setActorId(hostHolder.getUser().getId())
                .setExts("username",hostHolder.getUser().getName())
                .setExts("email","zhenyutu@126.com")
                .setExts("articleId",String.valueOf(articleId)));

        return "redirect:/article/"+articleId;
    }
}
