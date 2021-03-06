package other

object NullSafetySamples {
  @JvmStatic
  fun main(args: Array<String>) {
    checkingForNull()
    safeCalls()
    elvisOperator()
    exclamation()
    safeCasts()
    collectionsOfNullableType()
  }

  private fun checkingForNull() {
    println("checkingForNull(): ")
    var a: String = "abc"
    // a = null // ошибка компиляции
    var b: String? = "abc"
    b = null
    val l = if (b != null) b.length else -1 //  явная проверка на null
    if (b != null && b.length > 0) println("Непустая строка") else println("Пустая строка")
    println("--------------------")
  }

  // безопасные вызовы полезны в цепочках вызовов
  private fun safeCalls() {
    println("safeCalls(): ")
    val a = "Kotlin"
    val b: String? = null
    println(a?.length)
    println(b?.length) // безопасный вызов
    val listWithNulls: List<String?> = listOf("Kotlin", null)
    for (item in listWithNulls) {
      item?. let { println(it) } // выполнить операция для не null значений
    }
    println("--------------------")
  }

  private fun elvisOperator() {
    println("elvisOperator(): ")
    var b: String? = null
    val l1: Int = if (b != null) b.length else -1
    // элвис-выражение: аналогично выражению выше
    val l2 = b?.length ?: -1
    println("l1 = $l1, l2 = $l2")
    println("-")
    // использование throw и return в элвис-выражении
    // Это может быть полезно при проверке аргументов в функции
    fun f(o: ClassNS): String? {
      val p1 = o.p1 ?: return null
      val p2 = o.p2 ?: throw IllegalArgumentException("p2 expected")
      return "$p1 $p2"
    }
    var s = f(ClassNS(null, null))
    println("s = $s")
    try {
      s = f(ClassNS("", null))
    } catch (e: IllegalArgumentException) {
      println("exception: s = $s")
    }
    s = f(ClassNS("s1", "s2"))
    println("s = $s")
    println("--------------------")
  }

  private fun exclamation() {
    println("exclamation(): ")
    val s: String? = null
    try {
      // вернет ненулевое значение если s неравно null или сгенерирует
      // NullSafetySamples exception
      val l: Int = s!!.length
    } catch (e: Throwable) {
      println("NullSafetySamples error")
    }
    println("--------------------")
  }

  // безопасное приведение типов, позволяющее избежать ClassCastException.
  // Если попытка приведения типов оказалась неуспешной, будет
  // возвращено значение null
  private fun safeCasts() {
    println("safeCasts(): ")
    fun f(o: Any) {
      val l: ClassNS? = o as? ClassNS
      println("l = $l")
    }
    f("")
    f(ClassNS("s1", "s2"))
    println("--------------------")

  }

  private fun collectionsOfNullableType() {
    println("collectionsOfNullableType(): ")
    val nullableList: List<Int?> = listOf(1, 2, 3, 4, null, 5)
    // отфильтровать значения null
    val intList: List<Int> = nullableList.filterNotNull()
    println(intList)
    println("--------------------")
  }
}

data class ClassNS(val p1: String?, val p2: String?)