package other

object TypeChecksAndCastsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    isOperators()
    smartCast()
    unsafeCastOperator()
    typeErasureAndGenericTypeChecks()
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
    fun f5(o: Any) {
      when (o) {
        is String -> println(o.length)
        is Int -> println(o + 1)
        is Boolean -> println(!o)
        is IntArray -> println(o.sum())
      }
    }
    println("smartCast()")
    f1("ssss")
    f2("ssss")
    f3("ssss")
    f4("ssss")
    f5("ssss")
    println("---------------")
  }

  private fun unsafeCastOperator() {
    fun f1(o: Any) {
      try {
        val x: Int = o as Int // небезопасное приведение типов
        println("f1(): x = $x")
      } catch(e: ClassCastException) {
        println("Ошибка приведения типов")
      }
    }
    fun f2(o: Any?) {
      val x: Int? = o as Int? // небезопасное приведение типов с обработкой null
      println("f2(): x = $x")
    }
    fun f3(o: Any) {
      // безопасное приведение типов с as?
      // в случае ошибки приведения вернется null
      val x: Int? = o as? Int
      println("f3(): x = $x")
    }
    println("unsafeCastOperator(): ")
    f1("54")
    f1(3.4)
    f1(3)
    f2(null)
    f3("4")
    f3(4)
    println("---------------")
  }

  private fun typeErasureAndGenericTypeChecks() {
    println("typeErasureAndGenericTypeChecks(): ")

    println("---------------")
  }
}