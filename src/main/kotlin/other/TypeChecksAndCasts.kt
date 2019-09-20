package other

object TypeChecksAndCastsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    isOperators()
    smartCast()
  }

  private fun isOperators() {
    fun f(o: Any) {
      if (o is String) {
        println("object is String")
      }
      if (o !is String) {
        println("other object")
      }
    }
    println("isOperators(): ")
    f("s")
    f(15)
    println("---------------")
  }

  private fun smartCast() {
    fun f1(o: Any) {
      if (o is String) { // проверка типа
        // умное приведение типа (компилятор сам выполняет приведение)
        // после проверки типа
        println("f1() = ${o.length}")
      }
    }
    fun f2(o: Any) { // тот же результат что и выше
      if (o !is String) return
      println("f2() = ${o.length}") // умное приведение типа
    }
    fun f3(o: Any) {
      if (o !is String || o.length == 0) { // приведение с правой стороны от ||
        return
      }
      println("Непустая строка o: $o")
    }
    fun f4(o: Any) {
      if (o is String && o.length > 0) { // приведение с правой стороны от &&
        println("Непустая строка o: $o")
      }
    }
    println("smartCast()")
    f1("ssss")
    f2("ssss")
    f3("ssss")
    f4("ssss")
    println("---------------")
  }

}