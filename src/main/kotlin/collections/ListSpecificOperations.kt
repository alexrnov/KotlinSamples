package collections

import kotlin.math.sign

object ListSpecificOperationsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    retrievingElementsByIndex()
    retrievingListParts()
    findingElementPositions()
    binarySearchInSortedList()
    comparatorBinarySearch()
    comparisonBinarySearch()
    // операции записи в список
    adding()
    updating()
    removing()
    sortingForMutableLists()
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
    // изменения в полученной коллекции повлияют и на исходную
    // коллекцию
    numbers2.add(100)
    println(numbers)
    println("------------------------")
  }

  private fun findingElementPositions() {
    println("findingElementPositions()")
    val numbers = listOf(1, 2, 3, 4, 2, 5)
    println(numbers.indexOf(2)) // индекс первого элемента 2
    println(numbers.lastIndexOf(2)) // индекс последнего элемента 2
    println(numbers.indexOf(10)) // если нет такого элемента, вернет -1
    println("-")
    val numbers2 = listOf(1, 2, 3, 4)
    // индекс первого элемента, большего чем 2
    println(numbers2.indexOfFirst { it > 2 })
    // индекс последнего элемента, остаток деления которого на 2 будет равен единице
    println(numbers2.indexOfLast {it % 2 == 1} )
    println("------------------------")
  }

  // бинарный поиск
  private fun binarySearchInSortedList() {
    println("binarySearchInSortedList()")
    val numbers = mutableListOf("one", "two", "three", "four")
    numbers.sort() // коллекция должна быть отсортирована
    println(numbers)
    println(numbers.binarySearch("two"))
    println(numbers.binarySearch("a"))
    println(numbers.binarySearch("z"))
    println(numbers.binarySearch("two", 0, 2)) // поиск в диапазоне
    println("------------------------")
  }

  // Когда элементы не являются Comparable, можно обеспечить Comparator
  // используя бинарный поиск. Список должен быть отсортирован согласно
  // этому компаратору в возрастающем порядке
  private fun comparatorBinarySearch() {
    println("comparatorBinarySearch()")
    var productList = listOf(
            Product("WebStorm", 4.0), // Product не является Comparable
            Product("DotTrance", 30.0),
            Product("AppCode", 20.0),
            Product("ReSharper", 1.0)
    )
    // сначала коллекцию нужно отсортирвать в соответствии с компаратором
    // по которому будет осуществлятся бинарный поиск
    productList = productList.sortedWith(compareBy<Product> {it.price}.thenBy {it.name})
    productList.forEach {println(it)}

    println(productList.binarySearch(Product("AppCode", 20.0),
            compareBy<Product> {it.price}.thenBy {it.name}))
    println("-")
    // пользовательские компараторы также удобны, когда в коллекции
    // используется порядок, отличный от естественного, например,
    // порядок элементов String без учета регистра.
    val colors = listOf("Blue", "green", "ORANGE", "Red", "yellow")
    // сортировать без учета регистра
    println(colors.binarySearch("RED", String.CASE_INSENSITIVE_ORDER))
    println("------------------------")
  }

  // бинарный поиск с функцией сравнения поиск элементов без явных
  // значений поиска
  private fun comparisonBinarySearch() {
    println("comparisionBinarySearch()")
    fun priceComparison(product: Product, price: Double) = sign(product.price - price).toInt()

    var productList =listOf(
            Product("WebStorm", 4.0), // Product не является Comparable
            Product("DotTrance", 30.0),
            Product("AppCode", 20.0),
            Product("ReSharper", 1.0)
    )

    // сначала коллекцию нужно отсортирвать в соответствии с comparison
    productList = productList.sortedWith(compareBy<Product> {it.price})
    productList.forEach { println(it) }
    println(productList.binarySearch { priceComparison(it, 20.0) })
    println("------------------------")
  }

  private fun adding() {
    println("adding()")
    val numbers = mutableListOf("one", "five", "six")
    numbers.add(1, "two")
    numbers.addAll(2, listOf("three", "four"))
    println(numbers)
    println("------------------------")
  }

  private fun updating() {
    println("updating()")
    val numbers = mutableListOf("one", "five", "three")
    numbers[1] = "two"
    println(numbers)
    numbers.set(1, "2")
    println(numbers)
    println("-")
    val numbers2 = mutableListOf(1, 2, 3, 4)
    numbers2.fill(3) // заменяет значение всех элементов
    println(numbers2)
    println("------------------------")
  }

  private fun removing() {
    println("removing()")
    val numbers = mutableListOf(1, 2, 3, 4, 3)
    numbers.removeAt(3) // удалить элемент с индексом 3
    println(numbers)
    println("------------------------")
  }

  // функции для сортировки mutable-коллекций подобны функциям
  // для сортировки коллекций только для чтения. При сортировке
  // меняется исходная коллекция
  private fun sortingForMutableLists() {
    println("sortingForMutableList()")
    val numbers = mutableListOf("one", "two", "three", "four", "five")
    numbers.sort()
    println(numbers)
    numbers.sortDescending()
    println(numbers)
    println("------------------------")
  }
}

data class Product(val name: String, val price: Double)