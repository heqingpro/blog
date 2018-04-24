package com.cn.sys.blog.service;


import com.cn.sys.blog.dao.ArticleDao;
import com.cn.sys.blog.dao.ArticleTagDao;
import com.cn.sys.blog.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleTagDao articleTagDao;

    public int addArticle(Article article){
        Article article1=articleDao.save(article);
        return article1.getId();
    }

    public Article getArticleById(int qId){
        return articleDao.findById(qId).get();
    }

    public int getArticleCount(){
        return articleDao.getArticleCount();
    }

    public int getArticleCountByCategory(String category){
        return articleDao.getArticleCountByCategory(category);
    }

    public int getArticleCountByTag(int tagId){
        return articleTagDao.selectArticleCountByTagId(tagId);
    }

    public List<Article> getLatestArticles(int offset, int limit){
        return articleDao.selectLatestArticles(offset,limit);
    }

    public List<Article> getArticlesByCategory(String category,int offset, int limit){
        return articleDao.selecttArticlesByCategory(category,offset,limit);
    }

    public List<Article> getArticlesByTag(int tagId,int offset, int limit){
        return articleTagDao.selectByTagId(tagId,offset,limit);
    }

    public void updateCommentCount(int id,int count){
        articleDao.updateCommentCount(id,count);
    }
}
