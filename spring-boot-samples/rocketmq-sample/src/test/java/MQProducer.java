import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * @Author: Helixcs
 * @Time:8/12/18
 * 生产者
 *
 * 消息一致性
 * @See  https://blog.csdn.net/chunlongyu/article/details/53844393
 * 消息有一个应答处理的，如果你处理失败你就返回失败告诉mq，mq会重发消息，如果你这个处理逻辑复杂并很耗时间，那么先把消息数据入库，然后再搞一个job去扫描落库的数据
 *
 *
 *
 *
 */
public class MQProducer {

    private static final  String nameSrvAddr = "30.11.22.16:9876";

    // 短信发送
    private static  void syncProducerSendMsg() throws  Exception {
        DefaultMQProducer producer = new DefaultMQProducer("default_unique_group");
        producer.setNamesrvAddr(nameSrvAddr);
        producer.start();

        for(int i=0;i<100;i++){
            Message message = new Message(
                    "TopicsA",
                    "TagA",
                    ("Hello RocketMQ"+i).getBytes(RemotingHelper.DEFAULT_CHARSET)
            );

            SendResult sendResult = producer.send(message);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }

    private static void asyncProducerSendMsg() throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr(nameSrvAddr);
        producer.setProducerGroup("asyncGroup");
        producer.start();
        producer.setRetryTimesWhenSendAsyncFailed(0);

        for (int i=0;i<100;i++){
            final  int index = i;
            Message message = new Message(
                    "ASyncTopic",
                    "TagA",
                    "keysId",
                    "HelloWorld".getBytes(RemotingHelper.DEFAULT_CHARSET)
            );
            producer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index,
                            sendResult.getMsgId());
                }

                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }

        producer.shutdown();
    }

    //应用:单向传输用于要求中等可靠性的情况，如日志收集
    private static void oneWaySendMsg() throws  Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("oneway_group");
        defaultMQProducer.setNamesrvAddr(nameSrvAddr);
        defaultMQProducer.start();
        for (int i=0;i<100;i++){
            Message message = new Message(
                    "TopicOneWay",
                    "TagA",
                    ("Hello RocketMQ Oneway"+i).getBytes("UTF-8")
            );
            defaultMQProducer.sendOneway(message);
        }

        defaultMQProducer.shutdown();
    }

    // order product

    private static void orderProducer() throws  Exception{
        DefaultMQProducer mqProducer = new DefaultMQProducer("orderProducerGroup");
        mqProducer.setNamesrvAddr(nameSrvAddr);
        mqProducer.start();
        String [] tags = new String[]{"TagA","TagB","TagC","TagD"};
        for (int i=0;i<100;i++){
            int orderId = i%10;
            System.out.println("OrderId is :"+orderId);
            Message message = new Message("TopicOrder",
                    tags[i%tags.length],
                    "KEY"+i,
                    ("Hello "+i).getBytes("utf-8"));
            SendResult sendResult  = mqProducer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
        },orderId);

            System.out.printf("%s%n", sendResult);
        }

        mqProducer.shutdown();
    }


    public static void main(String[] args) throws Exception {
//        syncProducerSendMsg();

//        asyncProducerSendMsg();
//        oneWaySendMsg();

        orderProducer();
    }
}
