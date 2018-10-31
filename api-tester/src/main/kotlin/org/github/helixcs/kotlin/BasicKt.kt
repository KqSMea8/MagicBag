package org.github.helixcs.kotlin

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.declaredMemberProperties

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

// control flow
//if expression

fun if_expression() {
    var max = 0
    val a = 1
    val b = 2

    if (a > b) {
        max = a
    } else {
        max = b
    }
    println("traditional max is $max")

    max = if (a > b) {
        println("a is $a")
        a
    } else {
        println("b is $b")
        b
    }

    max = if (a > b) a else b

    println("as expression max is $max")


}

fun when_expression(code: Int) {
    var result = ""
    when (code) {
        1 -> result = "One"
        2 -> result = "Two"
        3, 4 -> {
            result = "3,4"
        }
        else -> result = "Default"
    }

    println("when expression is $result")
}

// for loops

fun for_loops() {
    val a = arrayListOf<Int>(1, 2, 43, 43, 53, 3, 3, 3, 9)
    for (item in a)
        println(item)

    for (item: Int in a)
        println(item)

    for (i in a.indices)
        println("$i --> ${a[i]}")

    for ((index, value) in a.withIndex())
        println("$index --> $value")
}


// return and jump

class Person1(val name: String, val age: Int? = 10)

fun return_and_jump() {
    val p1: Person1 = Person1("zhangsan", 11)
    println("name is ${p1.name}, age is ${p1.age}")

    loop@
    for (i in 1..100) {
        for (j in 1..100) {
            println("j is $j")
            if (j == 10) break@loop
        }
    }


    run loop@{
        listOf(1, 3, 4, 5, "da").forEach {
            if (it == 5)
                return@loop
            println(it)
        }

    }


    listOf(1, 242, 43, 53, 54).forEach(fun(v: Int) {
        println(v)
    })
}

// class and inheritance

class InitOrderDemo(name: String) {
    val firstProperty = "first property is $name".also(::println)

    init {
        println("first init property is $name")
    }

    val secondProperty = "second property is $name".also(::println)

    init {
        println("second init property is $name")
    }

}

class Person2 private constructor(val name: String)


open class BaseClass {
    open val x: Int = 1
    open fun v() {

    }

    fun nv() {

    }
}

class Derived() : BaseClass() {
    final override fun v() {
        super.v()
    }

    override val x: Int get() = super.x + 1
}


// properties and fields

class Address {
    var name: String = ""
        get() = if (field == "") "blank" else field
        set(value) = if (value == "") field = "blank value" else field = value.also { println("==> $value") }
    var street: String = ""
        private set
    var city: String = ""
    var state: String? = ""
    var zip: String = ""

}


// interface
interface MyInterface {
    var prop: Int // abstract
    fun bar()
    fun foo() {
        // optional body
    }
}

class Child : MyInterface {
    override
    var prop: Int = 1

    override fun bar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}


// interface inheritance
interface Named {
    val name: String
}

interface Person : Named {
    val firstName: String
    val lastName: String

    override val name: String get() = "$firstName , $lastName"

}

data class Employee(
        override val firstName: String,
        override val lastName: String

) : Person


// test reflect
// TODO

data class ReflectedClass constructor(val name: String?, val age: Int?)


// test logger
fun <T> loggerFor(clazz: Class<T>?): Logger = LoggerFactory.getLogger(clazz)

class LoggerTest {
    // first
    private val LOG = loggerFor(javaClass)
    // second
    private val Logger = LoggerFactory.getLogger(javaClass)

    fun testPrintLog() {
        LOG.info("test info log")
        LOG.debug("test debug log ")
        LOG.error("test error log")
        LOG.warn("test warn log")
    }
}

// Extension

// @reference at https://kotlinlang.org/docs/reference/extensions.html
// fun extension

fun MutableList<Int>.swap(index1: Int, index2: Int) {
    var tmp: Int = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
}

fun testListExtension() {
    val l = mutableListOf<Int>(1, 2, 3)
    l.swap(0, 1)
    for (index: Int in l.indices)
        println(l[index])
}

// fun statically

class C {
    fun foo() = println("i am foo")
}

fun C.foo(index: Int?) = println("i am extension fun ,index is $index")


// extension properties
val <T> List<T>.lastIndex: Int
    get() = size - 1

val <T> Collection<T>.isEmpty: Boolean
    get() = isEmpty()

fun extensionProperties() {
    val list = listOf<Int>(1, 2, 43, 54, 5)
    println(list.isEmpty)
    println(list.lastIndex)

}

// companion object extension

class MyClass {
    companion object {}
}

fun MyClass.Companion.foo() = println("companion object extension")



fun kotlin_reflect() {

    val reflectedClass: ReflectedClass = ReflectedClass(name = "helixcs", age = 11)
    val clazz = reflectedClass.javaClass.kotlin
    val a = clazz.declaredMemberProperties.forEach(::print)
    println(a)
//
//
//    println("==> simpleName : ${clazz.simpleName}")
//    println("==> name :${clazz.pro("name")}")
//    println("==> ages :${clazz.getDeclaredField("age")}")

}

// coroutines
fun corouties_sample() {
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

    // test if
    if_expression()

    // test when
    when_expression(11)

    // test for
    for_loops()

    // test return and jump
    return_and_jump()

    // test class and inheritance
    val iod = InitOrderDemo(name = "name")

    // test superclass implementation
    val d = Derived()
    println(d.x)

    // test properties and fields
    var address = Address()
    println(address.name)
    address.name = "helixcs"
    println(address.name)

    // test interface
    var myInterface: MyInterface = Child()
    println("child prop is ${myInterface.prop}")

    // test interface inheritance
    val employee: Named = Employee(firstName = "zhang", lastName = "jian")
    println("$employee")

    // test reflector
    kotlin_reflect()

    // test logger
    val testLog = LoggerTest()
    testLog.testPrintLog()

    // test fun extension
    testListExtension()

    // test fun statically
    val c: C = C()
    c.foo()
    c.foo(7)

    // test extension properties
    extensionProperties()

    // test companion object extension
    MyClass.foo()
}

