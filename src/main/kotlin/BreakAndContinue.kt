object BreakAndContinueSample {
  @JvmStatic
  fun main(args: Array<String>) {
    comparisonWithCollection("a")
    comparisonWithCollection("b")
    comparisonWithCollection("w")
    comparisonWithCollection("g")
    println("-----------------")
    println("аналог continue: цифра 3 - пропускается:")
    val returns = ReturnsAndJumps()
    returns.continueSample(5)
    println("-----------------")
    returns.breakSample(2) // break проходит
    println("-----------------")
    returns.breakSample2(2) // break проходит
    println("-----------------")
    returns.breakSample(10) // break не проходит
    println("-----------------")
    returns.breakSample2(10) // break не проходит
    println("-----------------")
    returns.break1()
    returns.break2()
    returns.break4()
    returns.break5()
    //returns.break3()
    returns.continue1()
    returns.continue2()
  }
}

// проверка есть ли входной параметр среди массива значений
// такой метод сравнения хорошо подходит для многофакторных проверок
fun comparisonWithCollection(p: String) {
  val list: List<String> = listOf("a", "b", "c", "d", "x", "w")
  if (p in list) {
    println("Элемент есть в списке")
  } else {
    println("Элемента нет в списке")
  }
}

class ReturnsAndJumps { // пример с continue в цикле, цифра 3 пропускается
  fun continueSample(j: Int) {
    (0..j).forEach loop@{
      if (it == 3) {
        return@loop
      } else {
        println(it)
      }
    }
  }

  fun breakSample(k: Int) { // пример с break для обычного цикла for
    for (i in 0..3) {
      run find@ { // break возвращается к следующей итерации i in 0..5
        (0..3).forEach {
          if (it == k) {
            return@find // break
          }
          println("i1 = $i j1 = $it")
        }
        // эта инструкция выполняется когда перехода по break не произошло
        println("coincide not found")
      }
    }
  }

  // Пример с break для цикла forEach.
  // Результат такой же как и в методе breakSample()
  fun breakSample2(k: Int) {
    val list1 = listOf(0, 1, 2, 3)
    val list2 = listOf(0, 1, 2, 3)
    list1.forEach find@{ i ->
      list2.forEach { j ->
        if (j == k) {
          return@find
        }
        println("j2 = $i j2 = $j")
      }
      println("coincide not found")
    }
  }

  // break в обычных циклах
  fun break1() {
    println("break1()")
    // метка указывает, что break нужно производить за пределы
    // первого цикла если метку не ставить, поток будет возвращаться
    // в i = 2, потом i = 3 и т.д.
    find@ for (i in 1..100) {
      for (j in 1..100) {
        println("i = $i j = $j")
        if (j == 5) {
          println("break")
          break@find
        }
      }
    }
    println("----------------------")
  }

  //break в лямбда-выражении
  fun break2() {
    println("break2(): ")
    run find@{
      listOf(0, 1, 2, 3, 4, 5, 6, 7).forEach {
        if (it == 5) return@find
        print("$it ")
      }
    }
    println("invoke")
    println("----------------------")
  }

  //break в лямбда-выражении
  fun break4() {
    println("break4(): ")

    find@for (i in 0..7) {
      if (i == 5) break@find
      print("$i ")
    }

    println("invoke")
    println("----------------------")
  }

  //break в лямбда-выражении
  fun break5() {
    println("break5(): ")

    run find@{
      (0..7).forEach {
        if (it == 5) return@find
        print("$it ")
      }
    }
    println("invoke")
    println("----------------------")
  }

  // break в лямбда выражении. Выходит из функции break3()!
  fun break3() {
    println("break3(): ")
    listOf(1, 2, 3, 4, 5).forEach {
      if (it == 3) return // выход из лямбда-выражения, а также из функции
      print("$it ")
    }
    println("no invoke") // не будет вызвано!
  }

  // continue в лямбда выражении
  fun continue1() {
    println()
    println("----------------------")
    println("continue1(): ")
    listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).forEach lat@{
      if (it == 3) return@lat
      print("$it ")
    }
    println()
    println("----------------------")
  }

  // continue в лямбда-выражении с использованием анонимной функции
  // результат тот же, что и в continue1()
  fun continue2() {
    println("continue2(): ")
    listOf(1, 2, 3, 4, 5).forEach(fun(value: Int) {
      if (value == 3) return
      print("$value ")
    })
    println()
    println("----------------------")
  }
}