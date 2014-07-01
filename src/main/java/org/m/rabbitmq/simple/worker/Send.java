package org.m.rabbitmq.simple.worker;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

/**
 * @version 1.1
 *          Created by wenzhouyang on 6/25/2014.
 */
public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.184.128");
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String[] s = {"helloWorld", "wenzhouyang"};
        String message = getMessage(s);
        for (int i=0; i<100;i++) {
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println("sent '" + message + "' on " + System.currentTimeMillis() );
        }

        channel.close();
        connection.close();
    }

    private static String getMessage(String[] strings) {
        if (strings.length < 1) return "helloWold";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder stringBuilder = new StringBuilder(strings[0]);
        for (String string : strings) {
            stringBuilder.append(delimiter).append(string);
        }

        return stringBuilder.toString();
    }
}
