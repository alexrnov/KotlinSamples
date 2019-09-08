package collections

object FilteringSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    filteringByPredicate()
    partitioning()
    testingPredicates()
  }

  private fun filteringByPredicate() {
    println("filteringByPredicate(): ")
    val numbers = listOf("one", "two", "three", "four")
    val longerThan3 = numbers.filter { it.length > 3 }
    println(longerThan3)
    println("-")
    val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
    val filteredMap = numbersMap.filter { (key, value) -> key.endsWith("1") && value > 10}
    println(filteredMap)
    println("-")
    // фильтрация с использованием индексов
    val filteredIdx = numbers.filterIndexed {
      index, s -> (index != 0) && (s.length < 5)
    }
    println("filteredIdx = $filteredIdx")
    // фильтрация с отрицательным условием
    val filteredNot = numbers.filterNot { it.length <=3 }
    println("filteredNot = $filteredNot")
    println("-")
    val numbers2 = listOf(null, 1, "two", 3.0, "four")
    val numbersString = // фильтрация по типу
            numbers2.filterIsInstance<String>().map { it.toUpperCase() }
    println("numbersString = $numbersString")
    val numbersInt = numbers2.filterIsInstance<Int>()
    println("numbersInt = $numbersInt")
    println("-")
    val numbers3 = listOf(null, 1, 2, null, 3, 4, 5)
    // возвращаются все не null-элементы
    val numbersNotNull = numbers3.filterNotNull()
    println("numbersNotNull = $numbersNotNull")
    println("-----------------------------")
  }

  private fun partitioning() {
    println("partitioning(): ")
    val numbers = listOf("one", "two", "three", "four")
    // возвращаются две коллекции, первая (match) содержит элементы,
    // которые удовлетворили условию, во вторую записываются
    // все остальные элементы
    val (match, rest) = numbers.partition {it.length > 3}
    println("match = $match")
    println("rest = $rest")
    println("-----------------------------")
  }

  // просто проверка коллеций на определенные условия
  private fun testingPredicates() {
    println("testingPredicates(): ")
    val numbers = listOf("one", "two", "tree", "four")
    println(numbers.any {it.endsWith("e")})
    println(numbers.none {it.endsWith("a")})
    // проверка - оканчиваются ли все слова на "e"
    println(numbers.all {it.endsWith("e")})
    // для пустой коллекции любое условие вернет true ( так называемая
    // пустая истина - vacuous truth)
    println(emptyList<Int>().all{ it > 5})
    println("-")
    val empty = emptyList<String>()
    // any() и none() без предикатов могут быть использованы для
    // проверки коллекций на пустоту
    println(numbers.any())
    println(empty.any())
    println("-")
    println(numbers.none())
    println(empty.none())
    println("-----------------------------")
  }
}