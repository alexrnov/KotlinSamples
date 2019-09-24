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
    println(".. overload for Point(0, 100)..Point(5, 40) = ")
    (Point(0, 100)..Point(5, 40)).forEach { println(it) }
    println("-")
    val range = Point(0, 100)..Point(5, 40)
    fun f(range: Array<Point?>) {

    }
    if (Point(-1, 500) in range) {
      println("Объект входит в диапазон")
    }
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
operator fun Point.rangeTo(b: Point): Array<Point?> {
  val n = b.x - x // диапазон в данном случае определяется по разнице полей x
  var a: Array<Point?> = arrayOfNulls(n)
  for (k in 0 until a.size) a[k] = Point(k, if (k < a.size / 2) y else b.y)
  return a
}

operator fun Array<Point?>.contains(a: Point): Boolean {
  return a.x >= this[0]!!.x && a.x <= this[this.size - 1]!!.x
}
data class Point(val x: Int, val y: Int) {

}