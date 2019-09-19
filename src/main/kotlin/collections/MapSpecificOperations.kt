package collections

object MapSpecificOperationsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    retrievingKeyAndValues()
    filtering()
    plusAndMinusOperators()
    addingAndUpdatingEntries()
    removingEntries()
  }

  private fun retrievingKeyAndValues() {
    fun f(map: Map<String, Int>, b: Boolean) {
      // безопасный доступ - в качестве возвращаемого значения используется
      // результат лямбда-выражения
      println(map.getOrElse("four") { if (b) 5 else 10 })
    }
    println("retrievingKeyAndValues()")
    val numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3)
    println(numbersMap.get("one"))
    println(numbersMap["one"])
    // безопасный доступ, в случае если элемент с таким ключем
    // отсутствует, вернется значение по умолчанию (4)
    println(numbersMap.getOrDefault("four", 4))
    println("-")
    f(numbersMap, true)
    f(numbersMap, false)
    println("-")
    println(numbersMap["five"]) // null
    // println(numbersMap.getValue("five")) // exception
    println("-")
    println(numbersMap.keys) // набор всех ключей
    println(numbersMap.values) // набор всех значений
    println("--------------------")
  }

  private fun filtering() {
    println("filtering()")
    val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
    val filteredMap1 = numbersMap.filter { it.value > 2 }
    println("filteredMap1 = $filteredMap1")
    // фидьтрация только по ключам
    val filteredMap2 = numbersMap.filterKeys {it.contains("1") }
    println("filteredMap2 = $filteredMap2")
    // фильтрация только по значениям
    val filteredMap3 = numbersMap.filterValues {it > 2}
    println("filteredMap3 = $filteredMap3")
    // фильтрация может осуществляться одновременно и по ключам
    // и по значениям
    val filteredMap4 = numbersMap.filter {
      (key, value) -> key.endsWith("1") && value > 10
    }
    println("filteredMap4 = $filteredMap4")
    println("--------------------")
  }

  private fun plusAndMinusOperators() {
    println("plusAndMinusOperators()")
    val numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3)
    println(numbersMap + Pair("four", 4))
    println(numbersMap + Pair("one", 10))
    // значение one заменится
    println(numbersMap + mapOf("five" to 5, "one" to 11))
    println(numbersMap - "one") // удалится элемент с ключем one
    println(numbersMap - listOf("two", "four"))
    println("--------------------")
  }

  private fun addingAndUpdatingEntries() {
    println("addingAndUpdatingEntries()")
    val numbersMap = mutableMapOf("one" to 1, "two" to 2) // реализация LinkedHashMap()
    numbersMap.put("three", 3)
    println(numbersMap)
    // добавить сразу несколько элементов
    numbersMap.putAll(setOf("four" to 4, "five" to 5))
    numbersMap.putAll(mapOf("six" to 6, "seven" to 7))
    println(numbersMap)
    println("-")
    // переменной будет присвоено прежнее значение (1)
    val previousValue = numbersMap.put("one", 11)
    println("previousValue = $previousValue")
    // а у отображения будет новое значение (11)
    println("numbersMap = $numbersMap")
    println("-")
    val numbersMap2 = mutableMapOf("one" to 1, "two" to 2)
    numbersMap2["three"] = 3
    println(numbersMap2)
    numbersMap2 += mapOf("four" to 4, "five" to 5)
    println(numbersMap2)
    println("--------------------")
  }

  private fun removingEntries() {
    println("removingEntries(): ")
    val numbersMap = mutableMapOf("one" to 1, "two" to 2, "three" to 3)
    println(numbersMap)
    numbersMap.remove("one")
    println(numbersMap)
    numbersMap.remove("three", 4) // элемент удален не будет, так как значение другое
    println(numbersMap)
    println("-")
    val numbersMap2 = mutableMapOf("one" to 1, "two" to 2, "three" to 3,
            "threeAgain" to 3)
    println(numbersMap2)
    numbersMap2.keys.remove("one") // удаление по ключу
    println(numbersMap2)
    // удаление по значению: будет удален только первый совпавший элемент
    numbersMap2.values.remove(3)
    println(numbersMap2)
    println("-")
    val numbersMap3 = mutableMapOf("one" to 1, "two" to 2, "three" to 3)
    numbersMap3 -= "two"
    println(numbersMap3)
    numbersMap3 -= "five"
    println(numbersMap3)
    println("--------------------")
  }
}