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
    println("-")
    var point2 = point + Point(100, 200)
    println("+ overload = $point2")
    point2 -= Point(100, 200)
    println("- overload = $point2")
    point2 *= Point(10, 20)
    println("* overload = $point2")
    point2 /= Point(2, 3)
    println("/ overload = $point2")
    point2 %= Point(2, 3)
    println("% overload = $point2")
    print(".. overload = ")
    (Point(0, 100)..Point(5, 40)).forEach { print("$it ") }
    println()

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

// арифметические операции
// перегрузка оператор a + b
operator fun Point.plus(increment: Point): Point {
  return Point(x + increment.x, y + increment.y)
}

// перегрузка оператора a - b
operator fun Point.minus(increment: Point): Point {
  return Point(x - increment.x, y - increment.y)
}

// перегрузка оператора a * b
operator fun Point.times(b: Point): Point {
  return Point(x * b.x, y * b.y)
}

// перегрузка оператора a / b
operator fun Point.div(b: Point): Point {
  return Point(x / b.x, y / b.y)
}

// перегрузка опреатора a % b
operator fun Point.rem(b: Point): Point {
  return Point(x % b.x, y % b.y)
}

// перегрузка оператора a..b
operator fun Point.rangeTo(b: Point): Array<Int> {
  val n = b.x - x
  var a: Array<Int> = Array(n) { i -> i }
  println("n = $n")
  var a3: Array<Int> = IntArray(5).toTypedArray()
  println("a3 = ${a3.size}")
  for (k in 0 until a3.size) {
    a3[k] = k
    println("a[k] = ${a[k]}")
  }
  for (k in a3.indices) {
    a[k] = k
    println("a2[k] = ${a[k]}")
  }
  return a3
}

data class Point(val x: Int, val y: Int) {

}