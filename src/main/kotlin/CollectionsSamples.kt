object CollectionsKotlin {

  @JvmStatic
  fun main(args: Array<String>) {
    keysAndValuesOfMap()
    println("-----------------")
    putAllSample()
    println("-----------------")
    addAllSample()
  }
}

/* получить ключи и значения на основе ассоциативного массива */
fun keysAndValuesOfMap() {
  val map: Map<Int, String> = mapOf(1 to "one", 2 to "two", 3 to "three")
  val k: List<Int> = map.keys.toList()
  val v: Collection<String> = map.values
  k.forEach {
    println(it)
  }
  v.forEach {
    println(it)
  }
}

// эксперименты с объединением отображений
fun putAllSample() {
  val map1: MutableMap<Int, String> = mutableMapOf(1 to "one", 2 to "two", 3 to "three")
  val map2: MutableMap<Int, String> = mutableMapOf(4 to "four", 1 to "five")
  val map3: MutableMap<Int, String> = HashMap()
  map3.putAll(map1)
  map3.putAll(map2)
  println("Отображение map3, составленное на основе map1 и map2:")
  map3.forEach {
    println("${it.key} ${it.value}")
  }
  map3[1] = "x"
  map3[4] = "x"
  println("Отображение map3, изменилось: ")
  map3.forEach {
    println("${it.key} ${it.value}")
  }
  println("отображение map1 осталось неизменным:")
  map1.forEach {
    println("${it.key} ${it.value}")
  }
  println("отображение map2 осталось неизменным:")
  map2.forEach {
    println("${it.key} ${it.value}")
  }
}

// эксперименты с объединением листов и использованием фильтрации
fun addAllSample() {
  val list1 = mutableListOf(1, 2, 3)
  val list2 = mutableListOf(1, 2, 4, 5)
  val list3 = ArrayList(list1)
  list3.addAll(list2.filter { it != 1 && it != 2 })
  println("массив list3, созданный на основе объединения list1 и list2:")
  list3.forEach {
    println(it)
  }
  list3.remove(1)
  println("массив list1 не изменился:")
  list1.forEach {
    println(it)
  }
  println("массив list2 не изменился:")
  list2.forEach {
    println(it)
  }

}
