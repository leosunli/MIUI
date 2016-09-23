package com.proxy;

/**
 * Created by mi on 16-9-22.
 */
public class ForumServiceImpl implements ForumService {
    public void removeTopic(int topicId) {
        PerfromanceMonitor.begin("com.proxy.ForumServiceImpl.removeTopic");
        System.out.println("模拟删除Topic记录:" + topicId);

        try {
            Thread.currentThread().sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PerfromanceMonitor.end();

    }

    public void removeForum(int forumId) {
        PerfromanceMonitor.begin("com.proxy.ForumServiceImpl.removeForum");
        System.out.println("模拟删除Forum记录:" + forumId);

        try {
            Thread.currentThread().sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PerfromanceMonitor.end();
    }
}
