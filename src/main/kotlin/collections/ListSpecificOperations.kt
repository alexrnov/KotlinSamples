package collections

object ListSpecificOperations {

  @JvmStatic
  fun main(args: Array<String>) {
    retrievingElementsByIndex()
    retrievingListParts()
  }

  private fun retrievingElementsByIndex() {
    println("retrievingElementsByIndex()")
    val numbers = listOf(1, 2, 3, 4)
    println(numbers.get(0))
    println(numbers[0])
    // numbers[5] // exception
    println(numbers.getOrNull(5)) // безопасно (без исключения) вернет null
    // безопасно (без исключения) вернет альтернативное значение 10
    println(numbers.getOrElse(5) { 10 })
    println("------------------------")
  }

  // выборка из коллекции по диапазону
  private fun retrievingListParts() {
    println("retrievingListParts() ")
    val numbers = (0..13).toMutableList()
    val numbers2 = numbers.subList(3, 6)
    println(numbers2)
    // изменения в полученной коллекции повлияют и на исходную коллекцию
    numbers2.add(100)  
    println(numbers)
    println("------------------------")
  }
}