package functionsAndLambdas

object DemoEma {
  @JvmStatic
  fun main(args: Array<String>) {

    val emaAlg: averageAlg = { a, b, historyEma ->
      val calcValue = a.length + b
      historyEma.add(calcValue)
      println(historyEma)
      calcValue
    }

    val maAlg: averageAlg = { a, b, historyMa ->
      val calcValue = a.length - b
      historyMa.add(calcValue)
      println(historyMa)
      calcValue
    }

    val averageClass = AverageClass(emaAlg, maAlg)
    averageClass.calculation()
    averageClass.calculation()
    println("-------------------")
    if (ema4("C", 4) > ma4("V", 6)) {
      println(">")
    } else {
      println("<")
    }
    if (ema4("C", 10) > ma4("V", 1)) {
      println(">")
    } else {
      println("<")
    }
  }
}

typealias averageAlg = (a: String, b: Int, history: MutableList<Int>) -> Int
class AverageClass(var emaAlg: averageAlg, var maAlg: averageAlg) {

  private val historyEma = ArrayList<Int>()
  private val historyMa = ArrayList<Int>()

  fun calculation() {
    if (ema("C", 20) < ma("L", 100)) {} else {}
    if (ema("B", 100) < ma("L", 5)) {} else {}
  }
  private fun ema(a: String, b: Int): Int = emaAlg(a, b, historyEma)
  private fun ma(a: String, b: Int): Int = maAlg(a, b, historyMa)
}

private fun ema4(a: String, b: Int): Int {
  val list = ArrayList<Int>()
  fun sample(f: averageAlg): Int {
    return f.invoke(a, b, list)
  }

  return sample(fun(a: String, b: Int, list: MutableList<Int>): Int {
    list.add(b)
    println("list = " + list)
    return a.length + b
  })
}

private fun ma4(a: String, b: Int): Int {
  val list = ArrayList<Int>()
  fun sample(f: averageAlg): Int {
    return f.invoke(a, b, list)
  }

  return sample(fun(a: String, b: Int, list: MutableList<Int>): Int {
    list.add(b)
    println("list = " + list)
    return a.length + b
  })
}