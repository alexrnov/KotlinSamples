import java.util.*
import java.util.stream.Collectors

object IntervalSample {

  @JvmStatic
  fun main(args: Array<String>) {
    alg1()
    alg2()
    alg3()
    alg4()
    alg5()
    alg6()
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
    val start = 62.8
    val end = 68.7
    println("start = $start, end = $end")
    val interval = Math.round((end - start) * 100.0) / 100.0
    println("interval = $interval")
    println("ceil = " + Math.ceil(interval))
    val step = Math.round((interval / (Math.ceil(interval))) * 100.0) / 100.0
    println("step = $step")
    val list = ArrayList<Double>()
    var i = start
    while (i <= step * Math.ceil(interval) + start) {
      list.add(i)
      i = Math.round((i + step) * 100.0) / 100.0
      println("i = $i")
    }
    println(list)
    list[list.lastIndex] = end
    list.forEach { print("$it ") }
    println()
    println("--------------------------------------")
  }

  private fun alg4() {
    val list = mutableListOf(mutableMapOf("id" to 1, "a" to 0.0),
            mutableMapOf("id" to 1, "a" to 0.4), mutableMapOf("id" to 1, "a" to 0.6),
            mutableMapOf("id" to 1, "a" to 0.6), mutableMapOf("id" to 1, "a" to 0.8),
            mutableMapOf("id" to 1, "a" to 1.0), mutableMapOf("id" to 1, "a" to 1.2),
            mutableMapOf("id" to 1, "a" to 1.2), mutableMapOf("id" to 1, "a" to 1.4),
            mutableMapOf("id" to 1, "a" to 1.6), mutableMapOf("id" to 1, "a" to 1.6),
            mutableMapOf("id" to 1, "a" to 1.8), mutableMapOf("id" to 1, "a" to 2.0))
    list.forEach { println(it) }

    val list2 = list.groupBy { it["a"] }.values.filter { it.size == 2 }
    val list3 = list2.toMutableList()
    list3.forEach {
      it[0]["a"] = (it[0]["a"] as Double) - 0.01
      it[1]["a"] = (it[1]["a"] as Double) + 0.01
    }
    println("-------------------------")
    Collections.replaceAll(list as List<Any>?, list2, list3)
    list.forEach { println(it) }
    println("-------------------------")
  }

  private fun alg5() {
    //val start = 62.8
    //val end = 68.7

    val start = 62.74
    val end = 68.76
    println("start = $start, end = $end")
    val interval = Math.round((end - start) * 100.0) / 100.0
    println("interval = $interval")
    println("ceil = " + Math.ceil(interval))
    var step = Math.round((interval / (Math.ceil(interval))) * 100.0) / 100.0
    println("step = $step")
    val list = ArrayList<Double>()
    var i = start
    list.add(i)
    while (i < step * Math.ceil(interval) + start) {
      i = Math.round((i + step) * 100.0) / 100.0
      list.add(i)
    }
    list[list.lastIndex] = end
    println(list)
    println()
    println("--------------------------------------")
  }

  private fun alg6() {
    //val start = 61.8
    //val end = 62.7
    //val start = 62.8
    //val end = 68.7
    //val start = 62.74
    //val end = 63.75
    //val start = 62.74
    //val end = 68.29
    val start = 49.4
    val end = 55.6
    println("start = $start, end = $end")
    val interval = Math.round((end - start) * 100.0) / 100.0
    println("interval = $interval")
    val ceil = Math.ceil(interval)
    println("ceil = $ceil")
    var step = Math.round((interval / ceil) * 100.0) / 100.0
    println("step = $step")
    val list = ArrayList<Double>()
    var i = start
    list.add(i)
    var k = 0
    while (i <= step * ceil + start) {
      i = Math.round((i + step) * 100.0) / 100.0
      println("i = $i")
      list.add(i)
      k++
    }
    println("k = $k")
    if (k > ceil.toInt()) list.removeAt(list.lastIndex)
    list[list.lastIndex] = end
    println(list)
    println()
    println("--------------------------------------")
  }

}