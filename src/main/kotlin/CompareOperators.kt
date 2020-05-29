import other.Point

typealias averageAlg = (a: String, b: Int, historyEma: MutableList<Int>) -> Int
object ObSamples {
  @JvmStatic
  fun main(args: Array<String>) {
    val ema = EMA { a, b, history ->
      // here your algorithm, for example:
      val calcValue = a.length + b
      history.add(calcValue)
      println(history)
      calcValue
    }
    val ma = MA { a, b, history ->
      // here your algorithm, for example:
      val calcValue = a.length - b
      history.add(calcValue)
      println(history)
      calcValue
    }
    if (ema.f("C", 24) < ma.f("L", 100)) {
      println("ema < ma")
    } else {
      println("ema > ma")
    }

    println("----------------")
    if (ema.f("A", 50) < ma.f("C", 70)) {
      println("ema < ma")
    } else {
      println("ema > ma")
    }

    val ema2: (a: String, b: Int) -> Int = { a, b ->
      5
    }


  }

}


class EMA(private var emaF: averageAlg) {
  val list = ArrayList<Int>() // store history
  fun f(a: String, b: Int): Int = emaF(a, b, list)
}

class MA(private var maF: averageAlg) {
  val list = ArrayList<Int>() // store history
  fun f(a: String, b: Int): Int = maF(a, b, list)
}