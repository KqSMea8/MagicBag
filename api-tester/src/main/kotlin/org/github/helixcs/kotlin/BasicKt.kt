package org.github.helixcs.kotlin

/**
 * @Author: Helixcs
 * @Time:10/26/18
 */

// basic collection
val numbers: MutableList<Int> = mutableListOf(1, 2, 4)

val onlyReadMap = mapOf<Int, String>(1 to "one", 2 to "two", 3 to "three")

val items = listOf<Int>(1, 32, 43, 43, 43)

val hashSet = hashSetOf<String>("a", "das", "dsa", "b")

// destructuring declarations  解构
enum class Status {
    SUCCESS, FAILED, EXCEPTION
}

enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00)
}

data class Result(val result: Int, val status: Status)

fun generateResultFunction(result: Int, status: Status): Result {
    return Result(result, status)
}

fun printLoop() {
    for ((k, v) in onlyReadMap) {
        println("$k --> $v")
    }
}

fun whenSample(code:Int):String?{
    when(code){
        is 1 -> return "One"
        is 2 -> return  "Two"
        else -> return "Default"

    }

}
fun main(args: Array<String>) {
    numbers.add(12)

    var entries = onlyReadMap.filter { it.key == 2 }.entries.firstOrNull()

    println(numbers)
    println(onlyReadMap)
    println(entries)
    println(items)
    println(hashSet)

    val (result, status) = generateResultFunction(1, Status.SUCCESS)
    println("$result , $status")

    printLoop()
}