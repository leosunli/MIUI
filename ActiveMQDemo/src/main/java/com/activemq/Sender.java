package com.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by mi on 16-9-21.
 */
public class Sender {

    private static final int SEND_NUMBER = 5;

    public static void main(String[] args) {
        //连接工厂,JMS创建它
        ConnectionFactory connectionFactory;
        //JMS客户端到JMS Provider的连接
        Connection connection = null;
        //一个发送或接收消息的线程
        Session session;
        //消息的目的地
        Destination destination;
        //消息发送者
        MessageProducer producer;

        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616"
        );

        //构造从工厂得到连接对象
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("FirstQueue");
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            sendMessage(session, producer);
            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != null) {
                    connection.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }

    private static void sendMessage(Session session, MessageProducer producer) throws JMSException {
        for (int i = 1; i <= SEND_NUMBER; i++) {
            TextMessage message = session.createTextMessage("ActiveMq 发送的消息 " + i);
            System.out.println("发送消息 ActiveMq 发送的消息 " + i);
            producer.send(message);
        }
    }

}
