package org.m.rabbitmq.simple.durability;

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
        ConnectionFactory factory  = new ConnectionFactory();
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setHost("192.168.184.128");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durability = true;
        //rabbitMQ 不允许你使用不同的参数去重新定义一个已经存在的queue
        channel.queueDeclare("hello2", durability, false, false, null);
        String message = "helloWorld";

        //将消息标记为持久性，并不能完全的保证这个消息不会丢失。即使已经告诉rabbitMQ将这个消息保存
        //到硬盘上，但是依然存在一个很短的窗口期，在rabbitMQ接收到一个消息，但是还没有进行存储。
        //虽然持久性保证不强，但是作为简单的任务队列已经足够了，如果你想要一个强的事务保证，
        //可以将代码放到一个事务中。
        channel.basicPublish("", "hello2", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

        channel.close();
        connection.close();



    }
}
