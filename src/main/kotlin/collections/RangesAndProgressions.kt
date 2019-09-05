package collections

object RangesAndProgressions {

  @JvmStatic
  fun main(args: Array<String>) {
    iterations()
    rangeOfObjects()
    println("--------------------------------")
    println("progressions: ")
    // прогрессии могут быть Int, Long и Char
    (1..10).filter{ it > 5 }.forEach { print("$it ") }
  }

  // Итерация диапазона
  private fun iterations() {
    println("iterations(): ")
    for (i in 1..4) print("$i ")
    println()
    // итерация в обратном порядке
    for (i in 4 downTo 1) print("$i ")
    println()
    // итерация с шагом
    for (i in 1..10 step 2) print("$i ")
    println()
    for (i in 10 downTo 1 step 2) print("$i ")
    println()
    // исключение последнего элемента
    for (i in 1 until 10) print("$i ")
    println()
    println("--------------------------------")
  }

  // диапазон с объектами
  private fun rangeOfObjects() {
    println("range(): ")
    // для создания интервала из объектов класса, в этом классе
    // должнен быть реализован метод rangeTo()
    val versionRange = Version(1, 10)..Version(2, 40)
    // для проверки диапазона должна быть реализована функция
    // contains()
    println(Version(0, 9) in versionRange)
    println(Version(0, 20) in versionRange)
    println(Version(0, 41) in versionRange)

    for (i in versionRange) println(i)
   }
}

private operator fun Array<Int>.contains(version: Version): Boolean {
  return version.v2 >= this[0] && version.v2 <= this[1]
}

class Version(val v1: Int, val v2: Int) {
  operator fun rangeTo(version: Version): Array<Int> {
    return arrayOf(v2, version.v2)
  }
}