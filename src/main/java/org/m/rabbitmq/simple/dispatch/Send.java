package org.m.rabbitmq.simple.dispatch;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

/**
 * @version 1.5
 *          Created by wenzhouyang on 7/1/2014.
 */
public class Send {

    public static void main(String[] args) throws IOException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setHost("192.168.184.128");

        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        channel.queueDeclare("dispatch", false, false, false, null);
        String message = "helloWorld" + Thread.currentThread();
        for (int i=0; i< 1000; i++) {
            channel.basicPublish("", "dispatch", null, message.getBytes());
        }

        channel.close();
        connection.close();
    }


}


