package other

object NullSafetySamples {
  @JvmStatic
  fun main(args: Array<String>) {
    checkingForNull()
    safeCalls()
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
}