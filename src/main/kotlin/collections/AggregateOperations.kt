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
    println("reduce: ")
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
    println("fold: ")
    // 0 - первоначальное накопленное значение. Первый накопленный
    // результат указывается явно, и на первом шаге накопленный
    // результатом будет результат выражения первого и второго элемента
    val sumDouble = numbers.fold(0) { sumD, element ->
      println("sum = $sumD element * 2 = ${element * 2} sum2 = ${sumD + element * 2}")
      sumD + element * 2
    }
    println("sumDouble = $sumDouble")

    println("-")
    println("reduceRight")
    // аналогичная функции reduce(), только перебор элементов
    // осуществляется в обратном порядке. При этом element и sum
    // меняются местами
    val reduceRight = numbers.reduceRight { element, sumR1 ->
      println("sum = $sumR1 element = $element sum = ${sumR1 + element}")
      sumR1 + element
    }
    println("reduceRight = $reduceRight")
    println("-")
    println("foldRight: ")
    // аналогичная функции fold(), только перебор элементов
    // осуществляется в обратном порядке. При этом element и sum
    // меняются местами
    val sumDoubledRight = numbers.foldRight(0) { element, sumR2 ->
      println("sum = $sumR2 element * 2 = ${element * 2} sum2 = ${sumR2 + element * 2}")
      sumR2 + element * 2
    }
    println("sumDoubledRight = $sumDoubledRight")
    println("-")
    println("reduceIndexed: ")
    // Использование reduce с индексами
    println("numbers = $numbers")
    val sumReduceIndexed = numbers.reduceIndexed { idx, sumRI, element ->
      if (idx % 2 == 0) {
        println("idx = $idx, sumRI = $sumRI, element = $element " +
                "sumRI + element = ${sumRI + element}")
        sumRI + element
      } else {
        println("ничего не делать, idx = $idx sumRI = $sumRI")
        sumRI
      }
    }
    println("sumReduceIndexed = $sumReduceIndexed")
    println("-")
    // использование fold с индексами
    println("foldIndexes: ")
    println("numbers = $numbers")
    val sumFoldIndexed = numbers.foldIndexed(0) {
      idx, sumE, element ->
      if (idx % 2 == 0) { // если индекс четный
        println("idx = $idx, sumE = $sumE, element = $element " +
              "sumE + element = ${sumE + element}" )
        sumE + element
      } else { // если индекс нечетный, ничего не делать
        println("ничего не делать, idx = $idx sumE = $sumE")
        sumE
      }
    }
    println("sumFoldIndexed = $sumFoldIndexed")
    println("-")
    println("reduceRightIndexes: ")
    println("----------------------------")
  }
}