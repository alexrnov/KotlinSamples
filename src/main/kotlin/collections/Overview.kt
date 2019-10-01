package collections

object OverviewSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    covariance()
    collection()
    mutableCollection()
    list()
    set()
    map()
  }

  // ковариантность матриц
  private fun covariance() {
    fun f(list: List<ColClass1>) = list.forEach { it.f() }
    fun f2(map: Map<Int, ColClass1>) {
      map.forEach { it.value.f() }
    }
    fun f3(list: MutableList<ColClass1>) = list.forEach { it.f() }

    println("Ковариантность коллекций: ")
    // коллекции только для чтения list1 и list2 являются ковариантными
    // можно использовать list2 везде, где используется list1
    val list1: List<ColClass1> = listOf(ColClass1(1), ColClass1(2))
    val list2: List<ColClass2> = listOf(ColClass2(1), ColClass2(2))
    f(list1)
    println("-")
    f(list2)
    println("--")
    // отображения являются ковариантными по значению, но не по ключу
    val map1 = mapOf(1 to ColClass1(1), 2 to ColClass1(2))
    val map2 = mapOf(1 to ColClass2(1), 2 to ColClass2(2))
    f2(map1)
    println("-")
    f2(map2)
    println("--")
    // изменяемые (mutable) коллекции не являются ковариантными
    val list3: MutableList<ColClass1> = mutableListOf(ColClass1(1), ColClass1(2))
    val list4: MutableList<ColClass2> = mutableListOf(ColClass2(1), ColClass2(2))
    f3(list3)
    // не допустимо, поскольку изменяемые (mutable) коллекции
    // не являются ковариантными
    //f3(list4)
    println("------------------------------------")
  }
}

private fun collection() {
  println("collection(): ")
  fun f(p: Collection<Int>) { // можно добавлять различные типы коллекций
    for (element in p) print("$element ")
    println()
  }

  val list1 = listOf(1, 2, 3)
  val list2 = setOf(1, 2, 3)
  f(list1)
  f(list2)
  println("------------------------------------")
}

private fun mutableCollection() {
  // mutableCollection позволяет использовать, для различных видов
  // коллекций, такие методы как: add() и remove()
  fun List<String>.getShortWordsTo(shortWords: MutableCollection<String>,
                                   maxLength: Int) {
    // в коллекцию shortWords записать слова с длиной <= 3 символа
    println("all sentence: " + this)
    this.filterTo(shortWords) { it.length <= maxLength }
    println("words less or equal 3: " + shortWords)
    // удалить артикли, при этом можно использовать и set
    val articles = setOf("a", "A", "an", "An", "the", "The")
    shortWords -= articles
  }
  println("mutableCollection(): ")
  val words = "A long time ago in a galaxy far far away".split(" ")
  val shortWords = mutableListOf<String>()
  words.getShortWordsTo(shortWords, 3)
  println("without articles: " + shortWords)
  println("------------------------------------")
}

private fun list() {
  println("list() sample: ")
  val bob = Person("Bob", 31)
  val people = listOf(Person("Adam", 20), bob, bob)
  val people2 = listOf(Person("Adam", 20), Person("Bob", 31), bob)
  // коллекции могут иметь разные экземпляры, важно только
  // структурное равенство объектов
  println("Коллекции равны: " + (people == people2))
  bob.age = 32
  if (people != people2) println("Теперь коллекции не равны")
  println("-")
  val numbers = mutableListOf(1, 2, 3, 4)
  numbers.add(5)
  println(numbers)
  numbers.removeAt(1)
  println(numbers)
  numbers[0] = 0
  println(numbers)
  numbers.shuffle() // случайно распределить элементы
  println(numbers)
  println("------------------------------------")
}

private fun set() {
  println("set(): ")
  val set1 = setOf(1, 2, 3, 4, 5) // реализация LinkedHashSet
  val set2 = setOf(5, 4, 3, 2, 1)
  // равными считаются наборы, которые имеют одинаковый размер
  // и одинаковые элементы
  println("Наборы являются равными: " + (set1 == set2))
  println("-")
  if (set1.contains(5)) println(" Набор содержит значение 5")
  println("-")
  println("Реализация LinkedHashSet запоминает порядок вставки элементов: ")
  println("set1.first() = " + set1.first() + ", set2.first() = " + set2.first())
  // В отличии от LinkedHashSet, HashSet не запоминает порядок
  // вставки элементов, зато требует меньше памяти
  println("------------------------------------")
}

private fun map() {
  println("map(): ")
  val map = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 4)
  println("all keys: ${map.keys}")
  println("all values: ${map.values}")
  // поиск ключа
  if ("key2" in map) println("${map["key2"]}")
  // поиск значения
  if (2 in map.values) println("Значение 2 есть в отображении")
  // ручной вариант
  if (map.containsValue(2)) println("Значение 2 есть в отображении")
  println("-")
  val map2 = mapOf("key4" to 4, "key2" to 2, "key3" to 3, "key1" to 1)
  // при сравнении коллекций порядок не важен
  println(map == map2)
  println("-")
  val map3 = mutableMapOf("one" to 1, "two" to 2)
  map3.put("three", 3)
  map3["one"] = 4
  println(map3)
  // по умолчанию реализация интерфейса map - LinkedHashMap, которая
  // хранит пары в соответсвии с порядком вставки. Напротив, HashMap
  // не хранит порядок вставки, и должна занимать меньше памяти
  println("------------------------------------")
}

open class ColClass1(val v: Int) {
  open fun f() {
    println("ColClass1: v = $v")
  }
}

class ColClass2(v: Int): ColClass1(v) {
  override fun f() {
    println("ColClass2: v = $v")
  }
}

data class Person(var name: String, var age: Int)