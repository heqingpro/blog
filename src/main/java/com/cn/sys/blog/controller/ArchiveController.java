package com.cn.sys.blog.controller;

import com.cn.sys.blog.dao.ArchiveDao;
import com.cn.sys.blog.entity.Archive;
import com.cn.sys.blog.entity.ViewObject;
import com.cn.sys.blog.service.JedisService;
import com.cn.sys.blog.util.RedisKeyUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ArchiveController {
    @Autowired
    private ArchiveDao archiveDao;

    @Autowired
    private JedisService jedisService;

    @RequestMapping("/archive")
    public String archive(Model model){
        List<Object[]> list=archiveDao.seletArticleGroupByTime();
        List<Archive> archives=new ArrayList<>();
        for (Object[] objects:list){
            Archive archive=new Archive();
            archive.setArticleId((int)objects[0]);
            archive.setArticleTitle((String)objects[1]);
            archive.setYear(Integer.valueOf(objects[2].toString()));//(int)objects[2]
            archive.setMonth(Integer.valueOf(objects[3].toString()));//(int)objects[3]
            archives.add(archive);
        }
        Map<String,List<Archive>> map = new HashMap<>();
        for (Archive archive : archives){
            String date = archive.getYear()+"-"+archive.getMonth();
            List<Archive> list1 = map.getOrDefault(date,new ArrayList<>());
            list1.add(archive);
            map.put(date,list1);
        }
        List<ViewObject> vos = new ArrayList<>();
        for (Map.Entry entry:map.entrySet()){
            ViewObject vo = new ViewObject();
            vo.set("date",entry.getKey());
            vo.set("list",entry.getValue()  );
            vos.add(vo);
        }
        model.addAttribute("vos",vos);

        ViewObject clickCount = new ViewObject();
        String currentPage = jedisService.get(RedisKeyUntil.getClickCountKey("/archive"));
        String sumPage = jedisService.get(RedisKeyUntil.getClickCountKey("SUM"));
        clickCount.set("currentPage",currentPage);
        clickCount.set("sumPage",sumPage);
        model.addAttribute("clickCount",clickCount);

        return "archive";
    }
}
