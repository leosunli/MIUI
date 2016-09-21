package com.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by mi on 16-9-21.
 */
public class Receiver {
    public static void main(String[] args) {
        //连接工厂,JMS创建它
        ConnectionFactory connectionFactory;
        //JMS客户端到JMS Provider的连接
        Connection connection = null;
        //一个发送或接收消息的线程
        Session session;
        //消息的目的地
        Destination destination;
        //消息接收者
        MessageConsumer consumer;

        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616"
        );

        //构造从工厂得到连接对象
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE,
                    Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("FirstQueue");
            consumer = session.createConsumer(destination);

            while (true) {
                TextMessage message = (TextMessage) consumer.receive();
                if (null != message) {
                    System.out.println("收到消息:" + message.getText());
                } else {
                    break;
                }
            }
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
}
