package com.activemq.reply;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

import javax.jms.*;

/**
 * ActiveMQ之JMSReplyTo
 */
public class Sender {
    public static void main(String[] args) throws JMSException {
        // 注意三个参数 分别是 用户名 密码 （这个可以修改）   61616 代表端口号
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.start();

        //消息发送到这个queue
        Queue queue = new ActiveMQQueue("testQueue2");
        //消息回复到这个queue
        Queue replyQueue = new ActiveMQQueue("replyQueue");

        //创建会话  false表示可以回送消息
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建一个消息：
        Message message = session.createTextMessage(" I am JMS..");
        //并设置它的JMSReplyTo为replyQueue
        //通过下面一句代码指定回复的消息传到哪个队列。
        message.setJMSReplyTo(replyQueue);

        //创建生产者
        MessageProducer producer = session.createProducer(queue);
        //设置消息的保存模式：
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //发送消息;
        producer.send(message);

        System.out.println("生产消息已发送成功。");

        //创建一个消费者：来获取接收者回复的消息
        MessageConsumer consumer = session.createConsumer(replyQueue);
        consumer.setMessageListener( new MessageListener(){
            public void onMessage(Message message) {
                TextMessage tm = (TextMessage) message;
                try {
                    System.out.println("收到的回复： " + tm.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
