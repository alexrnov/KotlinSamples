package collections

object CollectionOperations {

  @JvmStatic
  fun main(args: Array<String>) {
    destinationsObject()
    writeOperations()
  }

  // демонстрация функций с целевыми коллекциями, в которые
  // добавляется результат. Такие функции имеют постфикс To() в
  // названии функций, например filterTo() вместо filter(), associateTo()
  // вместо associate() и так далее. Эти функции берут целевую
  // коллекцию как дополнительный параметр
  private fun destinationsObject() {
    println("destinationsObject():")
    var numbers = listOf("one", "two", "three", "four")
    val filterResults = mutableListOf<String>()
    // результаты сохраняются в коллекцию filterResults
    numbers.filterTo(filterResults) { it.length > 3 }
    println(numbers)
    println(filterResults)
    // добавить элемент с нулевым индексом
    numbers.filterIndexedTo(filterResults) { index, _ -> index == 0 }
    println(filterResults)

    // создание целевой коллекции прямо при передаче аргумента
    // позволяет вернуть результат объекту присваивания
    val result = numbers.mapTo(HashSet()) { it.length }
    println(result)
    println("-----------------------------------")
  }

  // есть два типа записывающих функций: те, что изменяют
  // исходную коллекцию и те, которые создают новую коллекцию
  private fun writeOperations() {
    println("writeOperations(): ")
    val numbers = mutableListOf("one", "two", "three", "four")
    // создание новой коллекции на основе сортировки исходной коллекции
    val sortedNumbers = numbers.sorted()
    println(sortedNumbers)
    // сортировка исходной коллекции
    numbers.sort()
    println(numbers)
    println("-----------------------------------")
  }
}