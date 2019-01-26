package org.github.helixcs.netty.chapter4;

/**
 * @Email: wb-zj268791@alibaba-inc.com .
 * @Author: wb-zj268791
 * @Date: 1/26/2019.
 * @Desc:
 */
public class ClientNettyInvoker extends BaseNettyInvoker {


    private Client client ;

    public ClientNettyInvoker() {
        this.client = new Client("127.0.0.1",9999,"ddasda");

    }





    public static void main(String[] args) {
        ClientNettyInvoker invoker = new ClientNettyInvoker();
    }
}
