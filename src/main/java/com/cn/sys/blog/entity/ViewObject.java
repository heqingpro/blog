package com.cn.sys.blog.entity;

import org.apache.commons.collections.map.HashedMap;

import java.util.Map;


public class ViewObject {
    private Map<String,Object> objects = new HashedMap();

    public void set(String key, Object value){
        objects.put(key,value);
    }

    public Object get(String key){
        return objects.get(key);
    }
}
