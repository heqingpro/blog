package com.cn.sys.blog.service;


import com.cn.sys.blog.dao.ArticleDao;
import com.cn.sys.blog.dao.ArticleTagDao;
import com.cn.sys.blog.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
        List<Object[]> list=articleDao.selectLatestArticles(offset,limit);
        return getActicles(list);
    }

    public List<Article> getArticlesByCategory(String category,int offset, int limit){
        List<Object[]> list=articleDao.selecttArticlesByCategory(category,offset,limit);
        return getActicles(list);
    }

    public List<Article> getArticlesByTag(int tagId,int offset, int limit){
        List<Object[]> list=articleTagDao.selectByTagId(tagId,offset,limit);
        return getActicles(list);
    }

    public void updateCommentCount(int id,int count){
        articleDao.updateCommentCount(id,count);
    }

    public List<Article> getActicles(List<Object[]> list){
        List<Article> articles=new ArrayList<>();
        for(Object[] objects:list){
            Article article=new Article();
            article.setId((int)objects[0]);
            article.setTitle((String)objects[1]);
            article.setDescribes((String)objects[2]);
            article.setContent((String)objects[3]);
            article.setCreatedDate((Date)objects[4]);
            article.setCommentCount((int)objects[5]);
            article.setCategory((String)objects[6]);
            articles.add(article);
        }
        return articles;
    }
}
