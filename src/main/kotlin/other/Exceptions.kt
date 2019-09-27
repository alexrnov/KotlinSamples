package other

import java.lang.IllegalArgumentException

object ExceptionsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    throwSample()
    tryExpression()
    nothingType()
  }

  private fun throwSample() {
    println("throwSample(): ")
    try {
      throw Exception("error message") // генерация исключения
    } catch (e: Throwable) { // все иключения наследуются от Throwable
      println(e.message)
    } finally {
      println("finally block")
    }
    println("-----------------------")
  }

  private fun tryExpression() {
    println("tryExpression(): ")
    fun f(v: Int?) {
      val a: Int? = try {
        if (v == null) throw NumberFormatException("")
        else v * 2
      } catch (e: NumberFormatException) { null }
      println("a = $a")
    }
    f(null)
    f(5)
    println("-")
    fun f2(s: String?) {
      val a: Int? = try {
        s?.length ?: throw IllegalArgumentException()
      } catch (e: IllegalArgumentException) { null }
      println("a = $a")
    }
    f2(null)
    f2("abc")
    println("-----------------------")
  }

  private fun nothingType() {
    println("nothingType(): ")
    val excClass = ExcClass()
    try {
      // элвис-выражение с throw. При этом тип throw имеет
      // специальный тип Nothing. Этот тип не имеет значений и
      // используется для пометки кода, которого невозможно достичь
      val s = excClass.v1 ?: throw IllegalArgumentException("name required")
    } catch (e: IllegalArgumentException) {
      println(e.message)
    }

    println("-----------------------")
  }
}

data class ExcClass(val v1: String? = null)