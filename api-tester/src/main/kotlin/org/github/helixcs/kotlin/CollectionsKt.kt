package org.github.helixcs.kotlin


data class PersonData constructor(val name: String, val age: Int, val sex: String?)

var personDataList = listOf<PersonData>(PersonData(name = "zhangsan", age = 11, sex = null),
        PersonData(name = "lisi", age = 22, sex = "男"))

// distinct method
fun distinctMethod() {
    val simpleList = listOf<Int>(1, 1, 32, 42, 43, 3, 1, 432, 35, 3454, 45)
    println(simpleList)
    println(simpleList.distinct())
}

fun main(args: Array<String>) {
    distinctMethod()
}