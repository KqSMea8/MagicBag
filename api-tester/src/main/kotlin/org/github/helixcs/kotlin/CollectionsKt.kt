package org .github.helixcs.kotlin


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

// max() , maxBy() , maxWith()
fun maxMethod(){
    val simpleList :List<Int> = listOf<Int>(1,32,43,53,54,46,4,4,3,2,23,43,43)
    println("max() is ${simpleList.max()}")

    val personDataList = listOf<PersonData>(
            PersonData(name = "apple", age = 11, sex = null),
            PersonData(name = "banana", age = 22, sex = "男"),
            PersonData(name = "cat", age = 29, sex = "female"),
            PersonData(name = "cat", age = 29, sex = "female"),
            PersonData(name = "banana", age = 99, sex = "female"))

    println("maxBy() is ${personDataList.maxBy { it.age }}")

    val customMax = personDataList.maxWith(object :Comparator<PersonData>{
        override fun compare(o1: PersonData?, o2: PersonData?): Int =
            when{
                o1==null-> -1
                o2==null-> 1
                o1.age > o2.age -> 1
                o1.age == o2.age -> 0
                else -> -1
            }
    })
    println("maxWith() is ${customMax}")
}

// min() , minBy() , minWith()

fun minMethod(){
    val simpleList :List<Int> = listOf<Int>(1,32,43,53,54,46,4,4,3,2,23,43,43)
    println("min() is ${simpleList.min()}")

    val personDataList = listOf<PersonData>(
            PersonData(name = "apple", age = 11, sex = null),
            PersonData(name = "banana", age = 22, sex = "男"),
            PersonData(name = "cat", age = 29, sex = "female"),
            PersonData(name = "cat", age = 29, sex = "female"),
            PersonData(name = "banana", age = 99, sex = "female"))

    println("minBy() is ${personDataList.minBy { it.age }}")

    personDataList.minWith(object :Comparator<PersonData>{
        override fun compare(o1: PersonData?, o2: PersonData?): Int = when{
            o1 == null -> 1
            o2 == null -> 1
            o1.age > o2.age -> 1
            o1.age == o2.age -> 0
            else -> 1
        }
    })
}

// reduce
fun reduceMethod(){
    val intList = listOf<Int>(1,2,4,5,6,7,8,9)
    val intResult  = intList.reduce{acc: Int, i: Int -> println("acc = $acc, i= $i")
        acc+i}
    println("reduce is $intResult")
}


fun main(args: Array<String>) {
    distinctMethod()
    distinctByMethod()
    maxMethod()
    minMethod()
    reduceMethod()
}