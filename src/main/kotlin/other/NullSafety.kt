package other

object NullSafetySamples {
  @JvmStatic
  fun main(args: Array<String>) {
    checkingForNull()
  }

  private fun checkingForNull() {
    println("checkingForNull()")
    var a: String = "abc"
    // a = null // ошибка компиляции
    var b: String? = "abc"
    b = null
    val l = if (b != null) b.length else -1 //  явная проверка на null
    
    println("--------------------")
  }
}