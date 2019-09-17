package collections

object SetSpecificOperationsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    val numbers = setOf("one", "two", "three")
    println("Объединение наборов: ")
    println(numbers.union(setOf("four", "five"))) // обычная форма
    println(numbers union setOf("four", "five")) // инфиксная форма
    // при записи в инфиксной форме, важен порядок следования операндов
    println(setOf("four", "five") union numbers)

    println("\nПересечение наборов: ")
    println(numbers.intersect(setOf("two", "one"))) // обычная форма
    println(numbers intersect setOf("two", "one")) // инфиксная форма

    println("\nВычитание наборов: ")
    println(numbers.intersect(setOf("three", "four"))) // обычная форма
    println(numbers intersect setOf("three", "four")) // инфиксная форма
    println(numbers intersect setOf("four", "three")) // такой же результат
  }
}