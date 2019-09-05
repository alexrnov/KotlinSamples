package collections

object IteratorsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    println("iterator: ")
    val numbers = listOf("One", "Two", "Three", "Four")
    val iterator1: Iterator<String> = numbers.iterator()
    while (iterator1.hasNext()) print("${iterator1.next()} ")
    println()
    // аналогично коду выше, итератор вызывется неявно
    for (element in numbers) print("$element ")
    println()
    numbers.forEach { print("$it ") } // еще один способ
    println()
    println("----------------------------")
    println("ListIterator: ")
    // listIterator поддерживает обход коллекции в двух направлениях,
    // а также поддерживает информацию об индексе через методы
    // nextIndex() и previousIndex()
    val listIterator: ListIterator<String> = numbers.listIterator()
    while (listIterator.hasNext()) print("${ listIterator.next() } ")
    println()
    while (listIterator.hasPrevious())
      println("${ listIterator.previousIndex() } : ${ listIterator.previous() } ")
    println("----------------------------")
    println("MutableIterator: ")
    val numbers2 = mutableListOf("one", "two", "three", "four")
    // mutableIterator позволяет удалять элементы во время итерации
    val mutableIterator: MutableIterator<String> = numbers2.iterator()
    while (mutableIterator.hasNext()) {
      // удалить второй элемент
      if (mutableIterator.next() == "two") mutableIterator.remove()
    }
    println(numbers2)
    println("----------------------------")
    println("MutableListIterator: ")
    // MutableListIterator поддерживает, помимо функции удаления,
    // также замену и вставку элементов
    val numbers3 = mutableListOf("one", "two", "three", "four")
    val mutableListIterator = numbers3.listIterator()
    while (mutableListIterator.hasNext()) {
      if (mutableListIterator.next() == "two") {
        // изменить второй элемент
        mutableListIterator.set("change two")
        // добавить элемент после второго элемента
        mutableListIterator.add("after two")
      }
    }
    println(numbers3)
  }
}
