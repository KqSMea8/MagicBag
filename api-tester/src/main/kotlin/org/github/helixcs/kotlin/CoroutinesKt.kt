package org.github.helixcs.kotlin

import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch


/**
 * @Author: Helixcs
 * @Time:8/14/18
 *
 * IO 密集型 ： 多进程 -> 多线程 -> 事件驱动（Event Loop） -> 协程
 * CPU 密集型: 多进程 -> 多线程
 *协程 coroutine 轻量级线程
 */

fun main(args: Array<String>) {
    GlobalScope.launch {
        delay(100L)
    }
}