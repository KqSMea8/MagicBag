package org.github.helixcs.kotlin


data class PersonData constructor(val name: String, val age: Int, val sex: String?)


// distinct method
fun distinctMethod() {
    val simpleList = listOf<Int>(1, 1, 32, 42, 43, 3, 1, 432, 35, 3454, 45)
    println(simpleList)
    println(simpleList.distinct())


    // distinct object
    var personDataList = listOf<PersonData>(
            PersonData(name = "apple", age = 11, sex = null),
            PersonData(name = "banana", age = 22, sex = "男"),
            PersonData(name = "cat", age = 29, sex = "female"),
            PersonData(name = "cat", age = 29, sex = "female"),
            PersonData(name = "banana", age = 29, sex = "female")

    )

    println("==> before distinct")
    personDataList.forEach({ element -> println(element) })
    println("==> after distinct")
    personDataList.distinct().forEach { element -> println(element) }


}

// distinctBy method

fun distinctByMethod(){
    var personDataList = listOf<PersonData>(
            PersonData(name = "apple", age = 11, sex = null),
            PersonData(name = "banana", age = 22, sex = "男"),
            PersonData(name = "cat", age = 29, sex = "female"),
            PersonData(name = "cat", age = 29, sex = "female"),
            PersonData(name = "banana", age = 29, sex = "female"))

    println("==> before distinctBy")
    personDataList.forEach { element-> println(element) }
    println("==> after distinctBy")
    personDataList.distinctBy { it.name }.forEach { element-> println(element) }

}


fun main(args: Array<String>) {
    distinctMethod()
    distinctByMethod()
}