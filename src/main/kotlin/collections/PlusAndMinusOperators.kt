package collections

object PlusAndMinusOperatorsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    plusMinusForList()
    plusMinusForMap()
  }

  private fun plusMinusForList() {
    println("plusMinusForList(): ")
    val numbers = listOf("one", "two", "three", "four")
    val plusList = numbers + "five"
    val minusList = numbers - listOf("three", "four")
    println("plusList = $plusList")
    println("minusList = $minusList")
    println("----------------------------")
  }

  // plus и minus для отображений работают по другому
  private fun plusMinusForMap() {
    println("plusMinusForMap(): ")
    val numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3)
    // если элемента с таким ключем нет - элемент добавляется
    println(numbersMap + Pair("four", 4))
    // если элемент с таким ключем есть, переписывается только значение
    println(numbersMap + Pair("one", 10))
    println(numbersMap + mapOf("five" to 5, "one" to 11))
    println(numbersMap - "one")
    println(numbersMap - listOf("two", "four"))
    println("----------------------------")
  }
}