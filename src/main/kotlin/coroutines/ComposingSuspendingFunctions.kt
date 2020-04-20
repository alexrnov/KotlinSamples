package coroutines

import kotlinx.coroutines.delay

// В этом разделе рассматриваются различные подходы
// к составлению функций приостановки.
object SuspendingSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    // Последовательный по умолчанию
    // Предположим, что у нас есть две функции приостановки, определенные
    // в другом месте, которые делают что-то полезное, например, какой-либо вызов
    // удаленного сервиса или вычисление. Предположим, что они полезны, но на
    // самом деле каждый из них просто задерживается на секунду для целей
    // этого примера: doSomethingUsefulOne() и doSomethingUsefulTwo()
  }

  suspend fun doSomethingUserfulOne(): Int {
    delay(1000) // сделаем вид, что мы делаем что-то полезное здесь
    return 13
  }

  suspend fun doSomethingUsefulTwo(): Int {
    delay(1300) // сделаем вид, что мы делаем что-то полезное здесь тоже
    return 29
  }
}