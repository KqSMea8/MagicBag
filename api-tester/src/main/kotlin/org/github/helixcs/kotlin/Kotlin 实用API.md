## Kotlin 实用 API

> 基于 kotlin 1.3.0

----

### Collections 集合

#### List 集合

1. size

```kotlin
// 定义 PersonData 类
data class PersonData constructor(val name: String, val age: Int, val sex: String?)

val personDataList = listOf<PersonData>(
        PersonData(name = "apple", age = 11, sex = null),
        PersonData(name = "banana", age = 22, sex = "男"),
        PersonData(name = "cat", age = 29, sex = "female"),
        PersonData(name = "cat", age = 29, sex = "female"),
        PersonData(name = "banana", age = 29, sex = "female")

)

println("size() is ${personDataList.size}")

// size() is 5

```

2. contains()

```kotlin

println("contains() is ${personDataList.contains(PersonData(name = "dog", age = 11, sex = null))}")

// contains() is false
```

3. containsAll()

```kotlin
println("containsAll() is ${personDataList.containsAll(listOf(PersonData(name = "dog", age = 11, sex = null)))}")
//containsAll() is false


```

TODO
