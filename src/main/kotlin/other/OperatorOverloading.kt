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

    fun f1(p: Point) {
      val range = Point(0, 100)..Point(5, 40)
      if (p in range) {
        println("Объект входит в диапазон")
      } else {
        println("Объект не входит в диапазон")
      }
    }
    f1(Point(-1, 500))
    f1(Point(0, 500))
    f1(Point(3, 500))
    f1(Point(4, 500))
    f1(Point(5, 500))
    println("----------")
    fun f2(p1: Point, p2: Point) {
      when {
        p1 > p2 -> println("p1 > p2")
        p1 < p2 -> println("p1 < p2")
        else -> println("p1 == p2")
      }
    }
    f2(Point(1, 100), Point(2, 40))
    f2(Point(10, 5), Point(5, 10))
    f2(Point(10, 100), Point(10, 500))
  }
}

// перегрузка унарного минуса
private operator fun Point.unaryMinus() = Point(-x, -y)

// перегрузка унарного плюса
private operator fun Point.unaryPlus(): Point {
  var x1 = 0
  var y1 = 0
  if (x < 0) x1 = -1 * x
  if (y < 0) y1 = -1 * y
  return Point(x1, y1)
}

// перегрузка инкремента
private operator fun Point.inc() = Point(x + 1, y + 1)

// перегрузка деркремента
private operator fun Point.dec() = Point(x - 1, y - 1)

// арифметические операции
// перегрузка оператор a + b
private operator fun Point.plus(increment: Point): Point {
  return Point(x + increment.x, y + increment.y)
}

// перегрузка оператора a - b
private operator fun Point.minus(increment: Point): Point {
  return Point(x - increment.x, y - increment.y)
}

// перегрузка оператора a * b
private operator fun Point.times(b: Point): Point {
  return Point(x * b.x, y * b.y)
}

// перегрузка оператора a / b
private operator fun Point.div(b: Point): Point {
  return Point(x / b.x, y / b.y)
}

// перегрузка опреатора a % b
private operator fun Point.rem(b: Point): Point {
  return Point(x % b.x, y % b.y)
}

// перегрузка оператора a..b
private operator fun Point.rangeTo(b: Point): Array<Point?> {
  val n = b.x - x // диапазон в данном случае определяется по разнице полей x
  val a: Array<Point?> = arrayOfNulls(n)
  for (k in 0 until a.size) a[k] = Point(k, if (k < a.size / 2) y else b.y)
  return a
}

// перегрузка для in и !in (contains) такая же как и для
// арифметических операторов, только аргументы a и b
// меняются местами (a.div(b) против b.contains(a))
private operator fun Array<Point?>.contains(a: Point): Boolean {
  return a.x >= this[0]!!.x && a.x <= this[this.size - 1]!!.x
}

// перегрузка comparison-операций (>, <, >=, <=)
private operator fun Point.compareTo(b: Point): Int = when {
    x > b.x -> 1
    x < b.x -> -1
    else -> 0
}

data class Point(val x: Int, val y: Int)