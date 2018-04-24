package com.cn.sys.blog.aync;

import java.util.List;


public interface EventHandler {
    void doHandler(EventModel model);

    List<EventType> getSupportEventTypes();
}
