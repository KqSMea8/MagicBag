package org.github.helixcs.kotlin

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

/**
 * @Author: Helixcs
 * @Time:8/14/18
 */


fun main(args: Array<String>) {
    launch {
        delay(1000)
        println("Hello from Kotlin Coroutines!")
    }
}