package org.github.helixcs.kotlin


/**
 * @Author: Helixcs
 * @Time:8/14/18
 *
 * IO 密集型 ： 多进程 -> 多线程 -> 事件驱动（Event Loop） -> 协程
 * CPU 密集型: 多进程 -> 多线程
 *协程 coroutine 轻量级线程
 */

//suspend fun worker(n:Int):Int{
//    delay(1000L)
//    return n
//}
fun main(args: Array<String>) {

    // outdated
//    launch(CommonPool) {
//        delay(3000L, TimeUnit.MILLISECONDS)
//        println("Hello,")
//    }
//    println("World!")
//    Thread.sleep(5000L)
}