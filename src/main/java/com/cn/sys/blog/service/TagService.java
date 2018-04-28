package com.cn.sys.blog.service;


import com.cn.sys.blog.dao.ArticleTagDao;
import com.cn.sys.blog.dao.TagDao;
import com.cn.sys.blog.entity.ArticleTag;
import com.cn.sys.blog.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TagService {
    @Autowired
    private TagDao tagDao;

    @Autowired
    private ArticleTagDao articleTagDao;

    public Tag selectByName(String name){
        return tagDao.findByName(name).get(0);
    }

    public List<Tag> getAllTag(){
        return (List<Tag>)tagDao.findAll();
    }

    public List<Tag> getTagByArticleId(int articleId){
        List<Object[]> list=articleTagDao.selectByArticleId(articleId);
        List<Tag> tags=new ArrayList<>();
        for(Object[] object:list){
            Tag tag=new Tag();
            tag.setId((int)object[0]);
            tag.setName((String)object[1]);
            tag.setCount((int)object[2]);
            tags.add(tag);
        }
        return tags;
    }

    public int addTag(Tag tag){
        Tag tag1=tagDao.save(tag);
        return tag1.getId();
    }

    public int addArticleTag(ArticleTag articleTag){
        return articleTagDao.save(articleTag).getId();
    }

    public void updateCount(int tagId,int count){
        tagDao.updateCount(tagId,count);
    }
}
