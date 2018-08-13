import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @Author: Helixcs
 * @Time:7/22/18
 */
public class RocketMQProduct {
    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("p1");
        producer.setVipChannelEnabled(false);
        producer.setNamesrvAddr("192.168.31.174:9876");
        producer.start();


        for(int i=0;i<10;i++){
            Message message = new Message("topic1","tag1",("Hello World "+i).getBytes(RemotingHelper.DEFAULT_CHARSET));

            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        }

    }
}
