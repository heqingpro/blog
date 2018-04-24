package com.cn.sys.blog.aync;


public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2);

    private int value;
    EventType(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
