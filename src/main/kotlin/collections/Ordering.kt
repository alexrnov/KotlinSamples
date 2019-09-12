package collections

object OrderingSample {

  @JvmStatic
  fun main(args: Array<String>) {
    comparable()
    comparator()
  }

  private fun comparable() {
    println("comparable(): ")
    println(VersionOrder(1, 2) > VersionOrder(1, 3))
    println(VersionOrder(2, 0) > VersionOrder(1, 5))
    println("------------------------")
  }

  private fun comparator() {
    println("comparator(): ")
    println("new line1")
    println("------------------------")
  }
}

// для сравнения пользовательских объектов, они должны реализовать
// интерфейс Comparable
class VersionOrder(private val major: Int, private val minor: Int):
        Comparable<VersionOrder> {
  override fun compareTo(other: VersionOrder): Int {
    return when {
      this.major != other.major -> this.major - other.major
      this.minor != other.minor -> this.minor - other.minor
      else -> 0
    }
  }
}