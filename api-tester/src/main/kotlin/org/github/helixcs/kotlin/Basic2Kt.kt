package org.github.helixcs.kotlin

// inline
interface Printable {
    fun prettyPrint(): String
}

// 内联函数， cal 作为匿名函数，lambda 默认内联

inline fun lambdaFun(a: Int, b: Int): Int = a + b

// simple lambda

val sum: (Int, Int) -> Int = { x, y -> x + y }
val sum2 = { x: Int, y: Int -> x + y }

val intToString = { input: Int -> input.toString() }

// fun with lambda

fun funWithLambda(a: Int, b: Int, sum: (Int, Int) -> Int): Int {
    return sum(a, b)
}


fun main(args: Array<String>) {
    println(lambdaFun(1, 2))
    println(sum(1, 2))
    println(sum2(2, 3))
    println(intToString(1))
    println(funWithLambda(1, 2, { x, y -> x + y }))
    println(funWithLambda(1, 2, { x, y -> x * y }))

}

