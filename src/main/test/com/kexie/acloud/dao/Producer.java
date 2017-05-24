package com.kexie.acloud.dao;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.common.RemotingHelper;

/**
 * Created by zojian on 2017/5/14.
 */
public class Producer {
    public static void main(String[] args) throws MQClientException,
            InterruptedException {
         /*
         * Instantiate with a producer group name.
         */
        DefaultMQProducer producer = new
                DefaultMQProducer("please_rename_unique_group_name");
        producer.setNamesrvAddr("localhost:9876");
         /*
         * Specify name server addresses.
         * <p/>
         *
         * Alternatively, you may specify name server addresses via exporting
        environmental variable: NAMESRV_ADDR
         * <pre>
         * {@code
         * producer.setNamesrvAddr("name-server1-ip:9876;name-server2-ip:9876");
         * }
         * </pre>
         */
         /*
         * Launch the instance.
         */
        producer.start();
        for (int i = 0; i < 5; i++) {
            try {
                 /*
                 * Create a message instance, specifying topic, tag and message
                body.
                 */
                Message msg = new Message("TopicTest" /* Topic */,
                        "TagA" /* Tag */,
                        ("Hello RocketMQ " +
                                i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
                );
                 /*
                 * Call send message to deliver message to one of brokers.
                 */
                SendResult sendResult = producer.send(msg);
                System.out.printf("%s%n", sendResult);
            } catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }
         /*
         * Shut down once the producer instance is not longer in use.
         */
        producer.shutdown();
    }
}
