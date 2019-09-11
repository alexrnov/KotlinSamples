package collections

object RetrievingCollectionPartsSamples {

  val numbers = listOf("one", "two", "three", "four", "five", "six", "seven")
  @JvmStatic
  fun main(args: Array<String>) {
    slice()
    takeAndDrop()
    chunked()
    windowed()
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

    // взять все элементы, начиная со слова, имеющего четыре буквы
    println(numbers.dropWhile {it.length != 4} )
    // взять все элементы начиная с элемента, имеющего букву 'u' до
    // первого элемента
    println(numbers.dropLastWhile {!it.contains('u')})
    println("--------------------------")
  }

  // chunked() из коллекции делает несколько коллекций
  private fun chunked() {
    println("chunked(): ")
    val numbers = (0..13).toList()
    println(numbers.chunked(3))
    println(numbers.chunked(3) { it.sum()} )
    println("--------------------------")
  }

  //  создание коллекции на основе движущегося окна
  private fun windowed() {
    println("windowed(): ")
    val numbers = listOf("one", "two", "three", "four", "five")
    println(numbers.windowed(3))
    println("-")
    val numbers2 = (1..10).toList()
    println(numbers2)
    println(numbers2.windowed(3, step = 2)) // добавить шаг окна
    // добавить неполную коллекцию
    println(numbers2.windowed(3, step = 2, partialWindows = true))
    // сразу же произвести вычисления над результатом
    println(numbers2.windowed(3) { it.sum() })
    println("-")
    // возвращает pair-объекты
    println(numbers.zipWithNext())
    // сразу же произвести вычисления над результатами
    println(numbers.zipWithNext {s1, s2 -> s1.length > s2.length})
    println("--------------------------")
  }

}