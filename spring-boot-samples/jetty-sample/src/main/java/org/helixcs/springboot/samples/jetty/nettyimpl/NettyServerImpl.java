package org.helixcs.springboot.samples.jetty.nettyimpl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.github.helixcs.netty.chapter4.C4Sever;
import org.springframework.stereotype.Component;

/**
 * @Email: wb-zj268791@alibaba-inc.com .
 * @Author: wb-zj268791
 * @Date: 1/27/2019.
 * @Desc:
 */
@Component
public class NettyServerImpl {
    @Resource
    private C4Sever c4Sever;

    @PostConstruct
    public void startNettServer(){
        c4Sever.startServer();
    }

    public void sendMsgToNetty(String msg){
        c4Sever.sendMsg(msg);
    }
}
