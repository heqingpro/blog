package com.cn.sys.blog.controller;


import com.cn.sys.blog.entity.*;
import com.cn.sys.blog.service.ArticleService;
import com.cn.sys.blog.service.JedisService;
import com.cn.sys.blog.service.TagService;
import com.cn.sys.blog.service.UserService;
import com.cn.sys.blog.util.JblogUtil;
import com.cn.sys.blog.util.RedisKeyUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequestMapping(value = "hello")
@RestController
public class Helloword {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private JedisService jedisService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String hello() {
        return "hello world, " + LocalDateTime.now().toString();
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String Index(Model model){
        List<ViewObject> vos = new ArrayList<>();
        List<Article> articles = articleService.getLatestArticles(0,4);
        for (Article article:articles){
            ViewObject vo = new ViewObject();
            List<Tag> tags = tagService.getTagByArticleId(article.getId());
            String clickCount = jedisService.get(RedisKeyUntil.getClickCountKey("/article/"+article.getId()));
            if (clickCount==null)
                clickCount = "0";
            vo.set("clickCount",clickCount);
            vo.set("article",article);
            vo.set("tags",tags);
            vos.add(vo);
        }
        model.addAttribute("vos",vos);

        List<Tag> tags = tagService.getAllTag();
        model.addAttribute("tags",tags);

        ViewObject pagination = new ViewObject();
        int count = articleService.getArticleCount();
        User user = hostHolder.getUser();
        if (user==null||"admin".equals(user.getRole())){
            model.addAttribute("create",1);
        }else {
            model.addAttribute("create",0);
        }
        pagination.set("current",1);
        pagination.set("nextPage",2);
        pagination.set("lastPage",count/4+1);
        model.addAttribute("pagination",pagination);

        ViewObject categoryCount = new ViewObject();
        for (String category: JblogUtil.categorys){
            String categoryKey = RedisKeyUntil.getCategoryKey(category);
            String num = jedisService.get(categoryKey);
            if (num!=null)
                categoryCount.set(JblogUtil.categoryMap.get(category),num);
            else
                categoryCount.set(JblogUtil.categoryMap.get(category),0);
        }
        model.addAttribute("categoryCount",categoryCount);

        ViewObject clickCount = new ViewObject();
        String countStr1 = jedisService.get(RedisKeyUntil.getClickCountKey("/"));
        String countStr2 = jedisService.get(RedisKeyUntil.getClickCountKey("/index"));
        String countStr3 = jedisService.get(RedisKeyUntil.getClickCountKey("/page/1"));
        String currentPage = String.valueOf(Integer.parseInt(countStr1==null?"0":countStr1)
                + Integer.parseInt(countStr2==null?"0":countStr2)+ Integer.parseInt(countStr3==null?"0":countStr3));
        String sumPage = jedisService.get(RedisKeyUntil.getClickCountKey("SUM"));
        clickCount.set("currentPage",currentPage);
        clickCount.set("sumPage",sumPage);
        model.addAttribute("clickCount",clickCount);

        List<Article> hotArticles = new ArrayList<>();
        Set<String> set = jedisService.zrevrange("hotArticles",0,6);
        for (String str : set){
            int articleId = Integer.parseInt(str.split(":")[1]);
            Article article = articleService.getArticleById(articleId);
            hotArticles.add(article);
        }
        model.addAttribute("hotArticles",hotArticles);
        return "index";
    }
}
