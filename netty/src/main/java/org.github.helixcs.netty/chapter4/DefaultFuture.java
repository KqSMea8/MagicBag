//package org.github.helixcs.netty.chapter4;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
//import com.alibaba.dubbo.remoting.transport.netty.NettyServer;
//
//import com.futurehotel.ciksc.client.netty.constants.Constants;
//import com.futurehotel.ciksc.client.netty.constants.ResponseConstant;
//import com.futurehotel.ciksc.client.netty.constants.StatusCode;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//public class DefaultFuture {
//
//    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);
//
//    private static final Map<String, DefaultFuture> FUTURES = new ConcurrentHashMap<String, DefaultFuture>();
//
//    /**
//     * invoke id.
//     */
//    private final String id;
//
//    private final HfMessage request;
//
//    private final int timeout;
//
//    private final Lock lock = new ReentrantLock();
//
//    private final Condition done = lock.newCondition();
//
//    private final long start = System.currentTimeMillis();
//
//    private volatile long sent;
//
//    private volatile HfMessage response;
//
//    public DefaultFuture(HfMessage request, int timeout) {
//        this.request = request;
//        this.id = request.getMid();
//        this.timeout = timeout > 0 ? timeout : Constants.DEFAULT_TIMEOUT;
//        // put into waiting map.
//        FUTURES.put(id, this);
//    }
//
//    public Object get() throws Exception {//RemotingException
//        return get(timeout);
//    }
//
//    public Object get(int timeout) throws Exception {//RemotingException
//        if (timeout <= 0) {
//            timeout = Constants.DEFAULT_TIMEOUT;
//        }
//        if (!isDone()) {
//            long start = System.currentTimeMillis();
//            lock.lock();
//            try {
//                while (!isDone()) {
//                    done.await(timeout, TimeUnit.MILLISECONDS);
//                    if (isDone() || System.currentTimeMillis() - start > timeout) {
//                        break;
//                    }
//                }
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            } finally {
//                lock.unlock();
//            }
//            if (!isDone()) {
//                logger.error("Response timeout !  request:{}", request.toString());
//                throw new Exception("Response timeout");
//            }
//        }
//        return returnFromResponse();
//    }
//
//    public void cancel() {
//        HfMessage errorResult = new HfMessage(id);
//        errorResult.setCtx("request future has been canceled.");
//        response = errorResult;
//        FUTURES.remove(id);
//    }
//
//    public boolean isDone() {
//        return response != null;
//    }
//
//    private Object returnFromResponse() throws Exception {//RemotingException
//        HfMessage res = response;
//        if (res == null) {
//            throw new IllegalStateException("response cannot be null");
//        }
//        if (res.getStatusCode() == StatusCode.OK) {
//        }
//        //        if (res.getStatusCode() == StatusCode.ERROR) {
//        //            throw new TimeoutException(res.getStatusCode() == StatusCode.ERROR, channel, res.getStatusCode());
//        //        }
//        //        throw new RemotingException(channel, res.getErrorMessage());
//        return res;
//
//    }
//
//    private String getId() {
//        return id;
//    }
//
//    private boolean isSent() {
//        return sent > 0;
//    }
//
//    public HfMessage getRequest() {
//        return request;
//    }
//
//    private int getTimeout() {
//        return timeout;
//    }
//
//    private long getStartTimestamp() {
//        return start;
//    }
//
//    public static DefaultFuture getFuture(long id) {
//        return FUTURES.get(id);
//    }
//
//    public static void sent(HfMessage request) {
//        DefaultFuture future = FUTURES.get(request.getMid());
//        if (future != null) {
//            future.doSent();
//        }
//    }
//
//    private void doSent() {
//        sent = System.currentTimeMillis();
//    }
//
//    public static void received(HfMessage response) {
//        try {
//            DefaultFuture future = FUTURES.remove(response.getMid());
//            if (future != null) {
//                future.doReceived(response);
//            } else {
//                logger.warn("The timeout response finally returned at "
//                    + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()))
//                    + ", response " + response);
//            }
//        } finally {
//            FUTURES.remove(response.getMid());
//        }
//    }
//
//    private void doReceived(HfMessage res) {
//        lock.lock();
//        try {
//            response = res;
//            if (done != null) {
//                done.signal();
//            }
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    private String getTimeoutMessage(boolean scan) {
//        long nowTimestamp = System.currentTimeMillis();
//        return (sent > 0 ? "Waiting server-side response timeout" : "Sending request timeout in client-side")
//            + (scan ? " by scan timer" : "") + ". start time: "
//            + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date(start))) + ", end time: "
//            + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())) + ","
//            + (sent > 0 ? " client elapsed: " + (sent - start)
//            + " ms, server elapsed: " + (nowTimestamp - sent)
//            : " elapsed: " + (nowTimestamp - start)) + " ms, timeout: "
//            + timeout + " ms, request: " + request;
//    }
//
//    /**
//     * 用于清理长时间未得到响应的DefaultFuture
//     * 自定义超时响应
//     */
//    private static class RemotingInvocationTimeoutScan implements Runnable {
//
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    for (DefaultFuture future : FUTURES.values()) {
//                        if (future == null || future.isDone()) {
//                            continue;
//                        }
//                        if (System.currentTimeMillis() - future.getStartTimestamp() > future.getTimeout()) {
//                            // create exception response.
//                            HfMessage timeoutResponse = new HfMessage(future.getId());
//                            // set timeout status.
//                            timeoutResponse.setStatusCode(
//                                future.isSent() ? ResponseConstant.SERVER_TIMEOUT : ResponseConstant.CLIENT_TIMEOUT);
//                            timeoutResponse.setCtx(future.getTimeoutMessage(true));
//                            // handle response.
//                            DefaultFuture.received(timeoutResponse);
//                        }
//                    }
//                    Thread.sleep(30);
//                } catch (Throwable e) {
//                    logger.error("Exception when scan the timeout invocation of remoting.", e);
//                }
//            }
//        }
//    }
//
//    static {
//        Thread th = new Thread(new RemotingInvocationTimeoutScan(), "ResponseTimeoutScanTimer");
//        th.setDaemon(true);
//        th.start();
//    }
//
//}
