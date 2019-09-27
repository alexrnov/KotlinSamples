package other

object ExceptionsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    throwSample()
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
}