package collections

import java.util.*

object ConstructingSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    println("Constructing from elements: ")
    val set1 = setOf("one", "two", "three", "four")
    val emptySet = mutableSetOf<String>()
    // при таком создании отображения создаются PairGeneric-объекты и
    // сбособ рекомендован, когда нет больших требований к
    // производительности
    val map1 = mapOf(1 to "One", 2 to "Two", 3 to "Three", 4 to "Four")
    // альтернативный подход, в отношении памяти, менее затратный
    val map2 = mutableMapOf<String, String>().
            apply { this["one"] = "1"; this["two"] = "2" }
    map2.forEach { println(it.key + ":" + it.value) }
    println("----------------------------")
    println("Empty collections: ")
    val empty1 = emptyList<String>()
    println(empty1)
    val empty2 = emptySet<String>()
    val empty3 = emptyMap<String, String>()
    println("----------------------------")
    println("Initializer functions for lists: ")
    // или mutableList, ели нужно изменить коллекцию позже
    // инициализация списка на основе индексов и выражения,
    // основанного на индексе
    val doubled = List(5, {it * 2})
    doubled.forEach { print("$it ") }
    println()
    println("----------------------------")
    println("Concrete type constructors: ")
    // для указания конкретных реализаций коллекций (например
    // ArrayList или LinkedList) можно использовать соответствующие
    // конструкты
    val arrayList = ArrayList<String>()
    // связанный список - хранит порядок вставки, но требут больше памяти
    val linkedList = LinkedList<String>(listOf("1", "2", "3"))
    val hashSet = HashSet<String>()
    // связанное множество - хранит порядок вставки, но требует больше памяти
    val hashSet2 = LinkedHashSet<String>()
    val hashMap1 = HashMap<String, Int>()
    // связанное отображение - хранит порядок вставки, но требут больше памяти
    val hashMap2 = LinkedHashMap<String, Int>()
    println("----------------------------")
    println("Copying: ")
    val sourceList = mutableListOf(P(1), P(2), P(3), P(4))
    val copyList = sourceList.toMutableList() // полное копирование
    copyList.add(P(5))
    copyList[2] = P(10)
    // исходная коллекция и ее объекты не изменились
    print("sourceList: ")
    sourceList.forEach { print("${it.i} ") }
    println()
    print("copyList: ")
    copyList.forEach { print("${it.i} ") }
    println()
    println("----------------------------")
    println("converting to other types: ")
    val sourceList2 = mutableListOf(1, 2, 3, 4)
    // конвертировать list в set
    val set2 = sourceList2.toMutableSet()
    set2.add(4); set2.add(5)
    set2.forEach { print("$it ") }
    println("----------------------------")
    println("restricting mutability:")
    // ограничение на редактирование
    val sourceList3 = mutableListOf(1, 2, 3, 4, 5)
    val newList: List<Int> = sourceList3
    //newList.add() // компилятор не даст модифицировать коллекцию
    println("----------------------------")
    println("Invoking functions on other collections: ")
    // коллекции могут быть созланы на основе действий с другими коллекциями
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    // создать новую коллекцию на основе фильтрации
    val moreThanFive = numbers.filter { it > 5 }
    moreThanFive.forEach { print("$it ") }
    println()
    val numbers2 = setOf(1, 2, 3)
    // создать новую коллекцию на основе функции map()
    val numbers3 = numbers2.map { it * 2 }
    println(numbers3)
    // создать новую коллекцию на основе функции mapIndexed()
    val numbers4 = numbers2.mapIndexed { ind, value -> value * ind }
    println(numbers4)
    val stringList = listOf("", "1", "22", "333", "4444", "55555")
    // создать новую коллекцию на основе функции associateBy()
    val numbers5: Map<Int, String> = stringList.associateBy { it.length }
    numbers5.forEach {
      println("${it.key} : ${it.value}")
    }
    println("-")
    // создать новую коллекцию на основе функции associate()
    stringList.associate { Pair(it, it.length) }
            .forEach { println("${it.key} : ${it.value}")}

    println("-------------------")
    // создание оторажения на основе двух списков
    val listA = listOf("1", "2", "3", "4")
    val listB = listOf("a", "b", "c", "d")
    val b = listB.iterator()
    val resultList = listA.associate { Pair(it, b.next()) }
    println("resultList = $resultList")
    println("-------------------")
    val list3 = listA.toMutableList()
    list3.addAll(listB)
    list3.forEach { println(it) }
    println("-------------------")
    var list4 = mutableListOf("1", "2", "3")
    val list5 = list4
    list4 = mutableListOf("4", "5", "6") // при новом присваивании колекции List4, коллекция list5 не повреждается
    println("list5 = $list5")
  }
}

data class P(var i: Int)