package org.m.rabbitmq.simple.dispatch;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;

/**
 * @version 1.5
 *          Created by wenzhouyang on 6/25/2014.
 */
public class Recv {

    private final static String QUEUE_NAME = "dispatch";

    /**
     * 当client 接收到消息后，rabbitMQ就删除此message，如果这个时候client down掉，则未及时处理的消息将丢失
     * 为了保证所有的消息在未处理时候不丢失，则需要改成手动应答
     * @param args
     * @throws java.io.IOException
     * @throws InterruptedException
     */

    public static void main(String[] args) throws IOException, InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.184.128");
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println("Waiting for messages...");

        QueueingConsumer consumer = new QueueingConsumer(channel);

        //关闭自动应答
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
        channel.basicQos(1);
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());

            System.out.println("Received message '" + message + "' on " + System.currentTimeMillis());
            Thread.sleep(1000);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        }

    }
}
