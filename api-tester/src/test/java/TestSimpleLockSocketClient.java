import org.github.helixcs.java.nio.SimpleLockSocketClient;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author: helix
 * @Time:9/17/18
 * @Site: http://iliangqunru.bitcron.com/
 */
public class TestSimpleLockSocketClient {
    @Test
    public void testLockSocketWithHTTP() throws IOException {
        String content = "GET http://www.sina.com/  HTTP/1.1\r\nHost: www.sina.com\r\n\r\n";  //发送数据
        SimpleLockSocketClient client = new SimpleLockSocketClient("www.sina.com", 80,30000,30000);
        System.out.println(client.sendHttpData(content));
    }
}
