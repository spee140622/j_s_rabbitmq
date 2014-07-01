package org.m.rabbitmq.simple.helloworld.Send;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @version 1.5
 *          Created by wenzhouyang on 7/1/2014.
 */
public class Send2 {

    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("192.168.184.128");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();



        channel.close();
        connection.close();

    }
}
