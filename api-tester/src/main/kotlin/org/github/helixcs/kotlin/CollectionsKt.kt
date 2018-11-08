package org.github.helixcs.kotlin


data class PersonData constructor(val name: String, val age: Int, val sex: String?)

val personDataList = listOf<PersonData>(
        PersonData(name = "apple", age = 11, sex = null),
        PersonData(name = "banana", age = 22, sex = "男"),
        PersonData(name = "cat", age = 29, sex = "female"),
        PersonData(name = "cat", age = 29, sex = "female"),
        PersonData(name = "banana", age = 29, sex = "female")

)

fun collectionMethod() {
    // distinct object

    println("size() is ${personDataList.size}")
    println("contains() is ${personDataList.contains(PersonData(name = "dog", age = 11, sex = null))}")
    println("containsAll() is ${personDataList.containsAll(listOf(PersonData(name = "dog", age = 11, sex = null)))}")
    println("isEmpty() is ${personDataList.isEmpty()}")

    val iterator = personDataList.iterator()
    while (iterator.hasNext()) {
        println("next is ${iterator.next()}")
    }

    for (i in personDataList.indices) {
        println("index : $i --> ${personDataList.get(i)}")
    }
}

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

fun distinctByMethod() {
    var personDataList = listOf<PersonData>(
            PersonData(name = "apple", age = 11, sex = null),
            PersonData(name = "banana", age = 22, sex = "男"),
            PersonData(name = "cat", age = 29, sex = "female"),
            PersonData(name = "cat", age = 29, sex = "female"),
            PersonData(name = "banana", age = 29, sex = "female"))

    println("==> before distinctBy")
    personDataList.forEach { element -> println(element) }
    println("==> after distinctBy")
    personDataList.distinctBy { it.name }.forEach { element -> println(element) }


}

// max() , maxBy() , maxWith()
fun maxMethod() {
    val simpleList: List<Int> = listOf<Int>(1, 32, 43, 53, 54, 46, 4, 4, 3, 2, 23, 43, 43)
    println("max() is ${simpleList.max()}")

    val personDataList = listOf<PersonData>(
            PersonData(name = "apple", age = 11, sex = null),
            PersonData(name = "banana", age = 22, sex = "男"),
            PersonData(name = "cat", age = 29, sex = "female"),
            PersonData(name = "cat", age = 29, sex = "female"),
            PersonData(name = "banana", age = 99, sex = "female"))

    println("maxBy() is ${personDataList.maxBy { it.age }}")

    val customMax = personDataList.maxWith(object : Comparator<PersonData> {
        override fun compare(o1: PersonData?, o2: PersonData?): Int =
                when {
                    o1 == null -> -1
                    o2 == null -> 1
                    o1.age > o2.age -> 1
                    o1.age == o2.age -> 0
                    else -> -1
                }
    })
    println("maxWith() is ${customMax}")
}

// min() , minBy() , minWith()

fun minMethod() {
    val simpleList: List<Int> = listOf<Int>(1, 32, 43, 53, 54, 46, 4, 4, 3, 2, 23, 43, 43)
    println("min() is ${simpleList.min()}")

    val personDataList = listOf<PersonData>(
            PersonData(name = "apple", age = 11, sex = null),
            PersonData(name = "banana", age = 22, sex = "男"),
            PersonData(name = "cat", age = 29, sex = "female"),
            PersonData(name = "cat", age = 29, sex = "female"),
            PersonData(name = "banana", age = 99, sex = "female"))

    println("minBy() is ${personDataList.minBy { it.age }}")

    personDataList.minWith(object : Comparator<PersonData> {
        override fun compare(o1: PersonData?, o2: PersonData?): Int = when {
            o1 == null -> 1
            o2 == null -> 1
            o1.age > o2.age -> 1
            o1.age == o2.age -> 0
            else -> 1
        }
    })
}

// TODO:reduce
fun reduceMethod() {
    val intList = listOf<Int>(1, 2, 4, 5, 6, 7, 8, 9)
    val intResult = intList.reduce { acc: Int, i: Int ->
        println("acc = $acc, i= $i")
        acc + i
    }
    println("reduce is $intResult")
}


// filter(), filterTo() , filterNotTo(),filterIndexed(),filterIndexedTo(),filterIsInstance()
fun filterMethod() {
    // filter()
    val simpleList = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println("filter() is ${simpleList.filter { it % 2 == 0 }}")

    // filterTo()
    // 在原有list后追加符合的item
    val list1 = mutableListOf<Int>(100, 20)
    println("filterTo() is ${simpleList.filterTo(list1, { it -> it % 2 == 0 })}")

    // filterNotTo()
    val list2 = mutableListOf<Int>(100, 20)
    println("filterNotTo() is ${simpleList.filterNotTo(list2, { it -> it % 2 == 0 })}")


    // filterNotNull
    val list3 = mutableListOf(1, 2, 3, "One", "Two", null, "Three", null)
    println("filterNotNull() is ${list3.filterNotNull()}")
}


// Map

val personDataMap = hashMapOf(
        "zhangsan" to PersonData(name = "zhangsan", age = 11, sex = null),
        "lisi" to PersonData(name = "lisi", age = 30, sex = "nan"),
        "zhaowu" to PersonData(name = "zhaowu", age = 22, sex = "nan"),
        "wangliu" to PersonData(name = "wangliu", age = 29, sex = "nv"))


// maxBy() and maxByWith()
fun maxByMethod() {
    val maxByResult = personDataMap.maxBy { (_, person) -> person?.age ?: 0 }
    println("maxBy() is $maxByResult")
    val maxWithResult = personDataMap.maxWith(object : Comparator<Map.Entry<String, PersonData?>> {
        override fun compare(o1: Map.Entry<String, PersonData?>?, o2: Map.Entry<String, PersonData?>?): Int = when {
            o1 == null -> 1
            o2 == null -> -1
            o1.value!!.age > o2.value!!.age -> 1
            o1.value!!.age == o2.value!!.age -> 0
            else -> -1
        }

    })

    println("maxWith() is $maxWithResult")

}

// minBy() and minByWith()
fun minByMethod() {
    val minByResult = personDataMap.minBy { (_, person) -> person?.age ?: 0 }
    println("minBy() is $minByResult")

    val minWith = personDataMap.minWith(object : Comparator<Map.Entry<String, PersonData?>> {
        override fun compare(o1: Map.Entry<String, PersonData?>?, o2: Map.Entry<String, PersonData?>?): Int = when {
            o1 == null -> -1
            o2 == null -> 1
            o1.value!!.age > o2.value!!.age -> -1
            o1.value!!.age == o2.value!!.age -> 0
            else -> -1
        }
    })
    println("minWith() is $minWith")
}


// toSortedMap()
fun toSortedMapMethod() {
    val intMap = mapOf<Int, String>(2 to "two", 1 to "one", 9 to "nine", 5 to "five")
    println("toSortedMap() is ${intMap.toSortedMap()}")
    //use toSortedMap() with Comparator
    val sortedMap = personDataMap.toSortedMap(object : Comparator<String> {
        override fun compare(o1: String?, o2: String?): Int {
            return o1!!.compareTo(o2!!)
        }
    })

    println("sortedMap() is $sortedMap")
}

// filter

data class PersonAddress(val street: String, val houseNum: String)

val personAndAddressMap = mapOf(
        Pair(PersonData(name = "zhangsan", age = 11, sex = "nan"), PersonAddress(street = "yuhang", houseNum = "001")),
        Pair(PersonData(name = "lisi", age = 22, sex = "nv"), PersonAddress(street = "xiaguan", houseNum = "002")),
        Pair(PersonData(name = "zhaowu", age = 30, sex = "nv"), PersonAddress(street = "xiaoshan", houseNum = "003"))
)

fun mapFilterMethod() {
    // public inline fun <K, V> Map<out K, V>.filter(predicate: (Map.Entry<K, V>) -> Boolean): Map<K, V>
    val pad = personAndAddressMap.filter { (person, address) -> person.age > 10 && address.houseNum.equals("002") }
    println("filter() is $pad")

    //public inline fun <K, V> Map<out K, V>.filterNot(predicate: (Map.Entry<K, V>) -> Boolean): Map<K, V>
    val padNotFilter = personAndAddressMap.filterNot { (_, address) -> address.houseNum != "002" }
    println("filterNot() is $padNotFilter")

    // filterTo()
    //public inline fun <K, V, M : MutableMap<in K, in V>> Map<out K, V>.filterTo(destination: M, predicate: (Map.Entry<K, V>) -> Boolean): M
    val padFilterTo = ""

    // filterNotTo()
    //public inline fun <K, V, M : MutableMap<in K, in V>> Map<out K, V>.filterNotTo(destination: M, predicate: (Map.Entry<K, V>) -> Boolean): M


    // filterKeys()
    //public inline fun <K, V> Map<out K, V>.filterKeys(predicate: (K) -> Boolean): Map<K, V>
    println("filterKeys() is ${personAndAddressMap.filterKeys { personData -> personData.name === "zhangsan" }}")

    // filterValues()
    //public inline fun <K, V> Map<out K, V>.filterValues(predicate: (V) -> Boolean): Map<K, V>
    println("filterValues() is ${personAndAddressMap.filterValues { personAddress -> personAddress.houseNum == "yuhang" }}")


}

fun main(args: Array<String>) {
    // List
    collectionMethod()
    distinctMethod()
    distinctByMethod()
    maxMethod()
    minMethod()
    reduceMethod()
    filterMethod()
    //Map
    maxByMethod()
    minByMethod()
    toSortedMapMethod()
    mapFilterMethod()
}