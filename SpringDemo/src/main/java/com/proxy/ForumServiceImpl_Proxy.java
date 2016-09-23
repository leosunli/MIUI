package com.proxy;

/**
 * Created by mi on 16-9-22.
 */
public class ForumServiceImpl_Proxy implements ForumService {
    public void removeTopic(int topicId) {
        System.out.println("模拟删除Topic记录:" + topicId);

        try {
            Thread.currentThread().sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void removeForum(int forumId) {
        System.out.println("模拟删除Forum记录:" + forumId);

        try {
            Thread.currentThread().sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
