package collections

object OrderingSample {

  @JvmStatic
  fun main(args: Array<String>) {
    comparable()
    comparator()
    natureOrder()
    customOrders()
    reverseOrder()
    randomOrder()
    // сортировка для mutable-коллекций приведена в классе
    // ListSpecificOperations.kt
  }

  private fun comparable() {
    println("comparable(): ")
    println(VersionOrder(1, 2) > VersionOrder(1, 3))
    println(VersionOrder(2, 0) > VersionOrder(1, 5))
    println("------------------------")
  }

  // использование компаратора для сортировки
  private fun comparator() {
    println("comparator(): ")
    val comparator = Comparator {
      str1: String, str2: String -> str1.length - str2.length
    }
    // сортировка по длине слов
    println(listOf("aaa", "bb", "c").sortedWith(comparator))
    // тот же результат
    println(listOf("aaa", "bb", "c").sortedBy { it.length } )
    // тот же результат
    println(listOf("aaa", "bb", "c").sortedWith( compareBy { it.length}))
    println("------------------------")
  }

  // стандартные функции сортировки
  private fun natureOrder() {
    println("natureOrder(): ")
    val numbers = listOf("one", "two", "three", "four")
    println(numbers.sorted()) // сортировка
    println(numbers.sortedDescending()) // обратная сортировка
    println("------------------------")
  }

  // настраиваемая сортировка
  private fun customOrders() {
    println("customOrders(): ")
    val numbers = listOf("one", "two", "three", "four")
    // на выходе будет коллекция только для чтения
    val sortedNumbers = numbers.sortedBy { it.length }
    println("sortedNumbers = $sortedNumbers")
    // обратная сортировка по последнему символу в слове
    val sortedByLast = numbers.sortedByDescending { it.last() }
    println("sortedByLast = $sortedByLast")
    println("-")
    // настраиваемая сортировка с компаратором
    println(numbers.sortedWith(compareBy {it.length}))
    println("------------------------")
  }

  private fun reverseOrder() {
    println("reverseOrder(): ")
    val numbers = mutableListOf("one", "two", "three", "four")
    // возвращает новую коллекцию с копиями всех элементов
    println(numbers.reversed()) // сортировка в обратном порядке
    println("-")
    // возвращает коллекцию, которая ссылается на исходную коллекцию
    // если оригинальная коллекция изменится, изменится и отсортированная
    // коллекция и наоборот
    val reversedNumbers = numbers.asReversed()
    numbers[0] = "dsd" // изменение оригинальной коллекции
    println(reversedNumbers)
    reversedNumbers[1] ="dff" // изменение отсортированной коллекции
    println(numbers)
    println("------------------------")
  }

  // сортировка в случайном порядке
  private fun randomOrder() {
    println("randomOrder(): ")
    val numbers = listOf("one", "two", "three", "four")
    println(numbers.shuffled())
    println("------------------------")
  }
}

// для сравнения пользовательских объектов, они должны реализовать
// интерфейс Comparable
class VersionOrder(private val major: Int, private val minor: Int):
        Comparable<VersionOrder> {
  override fun compareTo(other: VersionOrder): Int {
    return when {
      this.major != other.major -> this.major - other.major
      this.minor != other.minor -> this.minor - other.minor
      else -> 0
    }
  }
}