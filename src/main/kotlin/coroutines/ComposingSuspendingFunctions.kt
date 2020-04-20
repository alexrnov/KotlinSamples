package coroutines

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

// В этом разделе рассматриваются различные подходы
// к составлению функций приостановки.
object SuspendingSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    // Последовательный (по умолчанию)
    // Предположим, что у нас есть две функции приостановки, определенные
    // в другом месте, которые делают что-то полезное, например, какой-либо вызов
    // удаленного сервиса или вычисление. Предположим, что они полезны, но на
    // самом деле каждый из них просто задерживается на секунду для целей
    // этого примера: doSomethingUsefulOne() и doSomethingUsefulTwo().
    println("f1(): ")
    // измерить общее время, необходимое для выполнения обеих функций приостановки:
    fun f1() = runBlocking {
      val time = measureTimeMillis {
        val one = doSomethingUserfulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
      }
      println("time = $time")
    }
    f1()
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