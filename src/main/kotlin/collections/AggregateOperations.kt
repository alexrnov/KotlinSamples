package collections

object AggregateOperationsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    operations()
  }

  private fun operations() {
    println("operations(): ")
    val list = listOf(1, 5, 3 ,6)
    println("count = ${list.count()}")
    println("max = ${list.max()}")
    println("min = ${list.min()}")
    println("average = ${list.average()}")
    println("sum = ${list.sum()}")
    println("----------------------------")
  }
}