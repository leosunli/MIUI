package com.activemq.reply;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * ActiveMQ之JMSReplyTo
 */
public class Receiver {
    public static void main(String[] args) throws JMSException {
        ConnectionFactory factory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.start();

        //创建会话   false表示可以回送消息
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建一个目的地 它用来接收消息：
        Destination destination = session.createQueue("testQueue2");

        //创建一个消费者： 来获取接收者回复的消息
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener(){
            public void onMessage(Message message) {
                TextMessage tm = (TextMessage) message;
                try {
                    System.out.println("接收到的生产消息： " + tm.getText());
                    System.out.println("回复的目标地址：  " + message.getJMSReplyTo());

                    //准备发送一个回复消息：
                    //创建一个新的生产者来发送回复消息至接收回复消息的队列：
                    MessageProducer producer = session.createProducer(message.getJMSReplyTo());
                    producer.send(session.createTextMessage(" Hello, I am receiver .."));
                    System.out.println( "\n 回复生产者成功。。。");
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
