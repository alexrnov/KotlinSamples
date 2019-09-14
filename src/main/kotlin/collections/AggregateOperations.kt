package collections

object AggregateOperationsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    operations()
    foldAndReduce()
  }

  private fun operations() {
    println("operations(): ")
    val list = listOf(1, 5, 3 ,6)
    println("count = ${list.count()}")
    println("max = ${list.max()}")
    println("min = ${list.min()}")
    println("average = ${list.average()}")
    println("sum = ${list.sum()}")
    println("-")
    val numbers = listOf(5, 42, 10, 4)
    // передать в качестве минимального значение - функию
    // будет возвращен элемент, который будет иметь минимальный
    // отсаток от деления на три
    val min3Remainder = numbers.minBy { it % 3}
    println("min3Remainder = $min3Remainder")
    val strings = listOf("one", "two", "three", "four")
    // вернет самое длинное слово
    val longestString1 = strings.maxBy { it.length }
    println("longestString1 = $longestString1")
    // вернет тот же результат
    val longestString2 = strings.maxWith ( compareBy { it.length } )
    println("longestString2 = $longestString2")
    println("-")
    println(numbers)
    // берет функцию в качестве аргумента - увеличить сумму всех
    // элементов в два раза
    println(numbers.sumBy { it * 2 } )
    // работает с функцией, которая возвращает Double
    println(numbers.sumByDouble { it.toDouble() / 2} )
    println("----------------------------")
  }

  /*
   * Fold() и reduce() последовательно применяют операцию к элементам
   * и возвращают накопленный результат
   */
  private fun foldAndReduce() {
    println("foldAndReduce(): ")
    val numbers = listOf(5, 2, 10, 4)
    println("numbers = $numbers")
    // операция берет два аргумента, предыдущее накопленное значение
    // и текущий элемент коллекции. Первый накопленный результат
    // берется из первого элемента и на первом шаге накопленный результат
    // будет соответсвтовать первому элементу.
    val sum = numbers.reduce { sum, element ->
      println("sum = $sum element = $element sum2 = ${sum + element}")
      sum + element
    }
    println("sum = $sum")
    println("-")
    // 0 - первоначальное накопленное значение. Первый накопленный
    // результат указывается явно, и на первом шаге накопленный
    // результатом будет результат выражения первого и второго элемента
    val sumDouble = numbers.fold(0) { sumD, element ->
      println("sum = $sumD element * 2 = ${element * 2} sum2 = ${sumD + element * 2}")
      sumD + element * 2
    }
    println("sumDouble = $sumDouble")

    println("----------------------------")
  }
}