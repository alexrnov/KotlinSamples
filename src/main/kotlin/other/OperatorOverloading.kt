package other

object OperatorOverloadingSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    var point = Point(10, 20)
    point = -point
    println(point)
    point = +point
    println(point)
    point++
    println(point)
    point++
    println(point)
    point--
    println(point)
  }
}

// перегрузка унарного минуса
operator fun Point.unaryMinus() = Point(-x, -y)

// перегрузка унарного плюса
operator fun Point.unaryPlus(): Point {
  var x1 = 0
  var y1 = 0
  if (x < 0) x1 = -1 * x
  if (y < 0) y1 = -1 * y
  return Point(x1, y1)
}

// перегрузка инкремента
operator fun Point.inc() = Point(x + 1, y + 1)

// перегрузка деркремента
operator fun Point.dec() = Point(x - 1, y - 1)

data class Point(val x: Int, val y: Int)