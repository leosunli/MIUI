package com.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by mi on 16-9-22.
 */
public class TestFormService {
    public static void main(String[] args) {
//        ForumService forumService = new ForumServiceImpl();
//        forumService.removeForum(10);
//        forumService.removeTopic(1012);


        ForumService target = new ForumServiceImpl_Proxy();
        PerfromanceHandler handler = new PerfromanceHandler(target);

        ForumService proxy = (ForumService) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                handler);

        proxy.removeForum(10);
        proxy.removeTopic(1024);

    }
}
