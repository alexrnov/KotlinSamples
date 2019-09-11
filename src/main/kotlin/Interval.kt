import java.util.*
import java.util.stream.Collectors

object IntervalSample {

  @JvmStatic
  fun main(args: Array<String>) {
    alg1()
    alg2()
    alg3()
    alg4()
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
  }
}