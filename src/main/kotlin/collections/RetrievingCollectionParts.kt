package collections

object RetrievingCollectionPartsSamples {

  val numbers = listOf("one", "two", "three", "four", "five", "six", "seven")
  @JvmStatic
  fun main(args: Array<String>) {
    slice()
    takeAndDrop()
  }

  // выборка коллекции по интервалу или набору
  private fun slice() {
    println("slice(): ")
    println(numbers.slice(1..3))
    println(numbers.slice(0..6 step 2))
    println(numbers.slice(setOf(3, 5, 0)))
    println("--------------------------")
  }

  // выборка по ограничиающему индексу
  private fun takeAndDrop() {
    println("takeAndDrop(): ")
    println(numbers)
    println("take(3) = " + numbers.take(3)) // взять превые три элемента коллекции
    println("takeLast(3) = " + numbers.takeLast(3)) // взять последние три элемента коллекции
    println("drop(1) = " + numbers.drop(1)) // взять все элементы, начиная со второго
    println("dropLast(5) = " + numbers.dropLast(5)) // взять все элементы, не считая пять последних
    // все элементы кроме первого и последнего
    println("drop(1).dropLast(1) = " + numbers.drop(1).dropLast(1))
    println("-")
    // взять элементы коллекции начиная сначала, пока не встретится
    // слово начинающееся на букву 'f'
    println(numbers.takeWhile {!it.startsWith('f')})
    // взять начиная с конца элементы до тех пор, пока не встретится
    // слово "three"
    println(numbers.takeLastWhile { it != "three"})

    // взять все элементы, начиная со слова, имеющего три буквы
    println(numbers.dropWhile {it.length == 3} )
    // взять все элементы кроме последнего
    println(numbers.dropLastWhile {it.contains('v')})
    println("--------------------------")
  }
}