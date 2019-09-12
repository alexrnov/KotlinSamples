package collections

object RetrievingSingleElementsSample {

  @JvmStatic
  fun main(args: Array<String>) {
    retrievingByPosition()
    retrievingByCondition()
    checkingExistence()
  }

  // извлечение по позиции. Для list лучше использовать get() или []
  private fun retrievingByPosition() {
    println("retrievingByPosition(): ")
    val numbers = linkedSetOf("one", "two", "three", "four", "five")
    println("numbers = $numbers")
    println(numbers.elementAt(3))
    val numbersSortedSet = sortedSetOf("c4", "a1", "b3", "b2")
    println("numbersSortedSet = $numbersSortedSet")
    println(numbersSortedSet.elementAt(0))
    println("numbers.first() = " + numbers.first())
    println("numbers.last() = " + numbers.last())
    // возвращает null если элемента с таким индексом нет
    println(numbers.elementAtOrNull(5))
    // вместо null возвращается другое значение
    println(numbers.elementAtOrElse(5) {
      index -> "Элемента с индексом $index нет "
    })
    println("-----------------------------")
  }

  // извлечение по состоянию
  private fun retrievingByCondition() {
    println("retrievingByCondition(): ")
    val numbers = listOf("one", "two", "three", "four", "five", "six")
    // получить первый элемент, длина которого больше трех символов
    println(numbers.first {it.length > 3})
    // вернуть последний элемент, содержащий букву 'f'
    println(numbers.last {it.contains('f')})
    // безопасный возврат null когда нет элементов, удовлетворяющих
    // условию
    println(numbers.firstOrNull { it.length > 6 } )
    println(numbers.lastOrNull { it.length > 6 } )
    // тоже что и firstOrNull()
    println(numbers.find {it.length > 3})
    // тоже что и lastOrNull()
    println(numbers.findLast {it.length > 3})
    println("-----------------------------")
  }

  // проверка наличия элементов
  private fun checkingExistence() {
    println("checkingExistence(): ")
    val numbers = listOf("one", "two", "three", "four", "five", "six")
    println(numbers.contains("four"))
    println("zero" in numbers)
    println("-")
    println(numbers.containsAll(listOf("one", "four")))
    println(numbers.containsAll(listOf("zero", "one")))
    println("-")
    println(numbers.isEmpty())
    println(numbers.isNotEmpty())
    println("-")
    val empty = emptyList<String>()
    println(empty.isEmpty())
    println(empty.isNotEmpty())
    println("-----------------------------")
  }
}