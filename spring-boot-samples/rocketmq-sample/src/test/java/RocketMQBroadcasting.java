import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * @Author: Helixcs
 * @Time:8/12/18
 */
public class RocketMQBroadcasting {
    public static void broadcastingProducer() throws  Exception{
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("broadcastingGroup");
        defaultMQProducer.setNamesrvAddr("192.168.31.174:9876");
        defaultMQProducer.start();

        for(int i=0;i<100;i++){
            Message message = new Message(
                    "TopicBroadingcasting",
                    "TagsA",
                    "hello rocketMQ broadcasting ".getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            SendResult sendResult = defaultMQProducer.send(message);
            System.out.printf("%s%n",sendResult);
        }
        defaultMQProducer.shutdown();

    }

    public static void broadingcastingConsumer() throws  Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("TopicBroadingcasting");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        consumer.setMessageModel(MessageModel.BROADCASTING);
        consumer.subscribe("TopicBroadingcasting","*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName()+" Receive New Messages: " + msgs + "%n");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.out.printf("Broadcast Consumer Started.%n");
    }

    public static void main(String[] args) throws Exception{
        broadcastingProducer();
        broadingcastingConsumer();
    }
}
