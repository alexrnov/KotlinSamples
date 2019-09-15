package collections

object WriteOperationsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    addingElements()
    removingElements()
  }

  private fun addingElements() {
    println("addingElement()")
    val numbers = mutableListOf(1, 2, 6)
    numbers.add(5)
    println(numbers)
    numbers.addAll(arrayOf(7, 8)) // добавить коллекцию
    println(numbers)
    // добавить коллекцию в определенное место
    numbers.addAll(2, setOf(-5, -6))
    println(numbers)
    println("-")
    val numbers2 = mutableListOf("one", "two")
    numbers2 += "tree"
    println(numbers2)
    numbers2 += listOf("four", "five")
    println(numbers2)
    println("----------------")
  }

  private fun removingElements() {
    println("removingElements()")
    val numbers = mutableListOf(1, 2, 3, 4, 3)
    numbers.remove(3)
    println(numbers)
    numbers.remove(5)
    println(numbers)
    println("-")
    numbers.retainAll { it >= 3 } // оставить элементы больше и равные трем
    println(numbers)
    numbers.clear()
    println(numbers)
    val numbersSet = mutableSetOf("one", "two", "three", "four")
    numbersSet.removeAll(setOf("two", "three"))
    println(numbersSet)
    println("-")
    val numbers2 = mutableListOf("one", "two", "three", "three", "four")
    numbers2 -= "three"
    println(numbers2)
    numbers2 -= listOf("four", "five")
    println(numbers2)
    println("----------------")
  }
}