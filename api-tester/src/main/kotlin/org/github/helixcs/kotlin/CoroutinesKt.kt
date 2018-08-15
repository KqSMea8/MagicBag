package org.github.helixcs.kotlin

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.timeunit.TimeUnit

/**
 * @Author: Helixcs
 * @Time:8/14/18
 *
 * IO 密集型 ： 多进程 -> 多线程 -> 事件驱动（Event Loop） -> 协程
 * CPU 密集型: 多进程 -> 多线程
 *协程 coroutine 轻量级线程
 */


fun main(args: Array<String>) {
    launch(CommonPool) {
        delay(3000L, TimeUnit.MILLISECONDS)
        println("Hello,")
    }
    println("World!")
    Thread.sleep(5000L)
}