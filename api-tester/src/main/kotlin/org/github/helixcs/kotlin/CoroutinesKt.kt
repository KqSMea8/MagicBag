package org.github.helixcs.kotlin

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * @Author: Helixcs
 * @Time:8/14/18
 *
 * IO 密集型 ： 多进程 -> 多线程 -> 事件驱动（Event Loop） -> 协程
 * CPU 密集型: 多进程 -> 多线程
 * 协程 coroutine 轻量级线程
 * Tips : 注意把IDEA 中kotlin 插件版本升级到 1.3.0
 */

fun main(args: Array<String>) {
    GlobalScope.launch { // launch new coroutine in background and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
    }
    println("Hello,") // main thread continues while coroutine is delayed
    Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive
}