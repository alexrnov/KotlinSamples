object IntervalSample {

  @JvmStatic
  fun main(args: Array<String>) {
    alg1()
    alg2()
    alg3()
  }

  private fun alg1() {
    val start = 0.5
    val end = 3.5
    println("start = $start, end = $end")
    val interval = Math.round((end - start) * 100.0) / 100.0
    val step = Math.round((interval / 5) * 100.0) / 100.0
    val list = ArrayList<Double>()
    var i = start
    while (i <= end) {
      list.add(i)
      i = Math.round((i + step) * 100.0) / 100.0
    }
    list.forEach { print("$it ") }
    println()
    println("--------------------------------------")
  }

  private fun alg2() {
    val start = 0.5
    val end = 3.5
    println("start = $start, end = $end")
    val interval = Math.round((end - start) * 100.0) / 100.0
    val step = Math.round((interval / 5) * 100.0) / 100.0
    println(step)
    val list = ArrayList<Double>()
    var i = start
    while (i <= end) {
      list.add(i)
      i = Math.round((i + step) * 100.0) / 100.0
    }
    list[list.lastIndex] = end
    list.forEach { print("$it ") }
    println()
    println("--------------------------------------")
  }

  private fun alg3() {
    val start = 0.51
    val end = 5.52
    println("start = $start, end = $end")
    val interval = Math.round((end - start) * 100.0) / 100.0
    val step = Math.round((interval / (Math.ceil(interval))) * 100.0) / 100.0
    val list = ArrayList<Double>()
    var i = start
    while (i <= step * Math.ceil(interval) + start) {
      list.add(i)
      i = Math.round((i + step) * 100.0) / 100.0
    }
    list[list.lastIndex] = end
    list.forEach { print("$it ") }
    println()
    println("--------------------------------------")
  }

  private fun alg4() {

  }

}