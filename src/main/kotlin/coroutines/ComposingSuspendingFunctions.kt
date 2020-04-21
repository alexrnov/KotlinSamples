package coroutines

import kotlinx.coroutines.*
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
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        println("The answer is ${one + two}")
      }
      println("time = $time")
    }
    f1()
    println("-----------------------")
    // Концептуально, async - это как launch. Он запускает отдельную сопрограмму,
    // которая представляет собой легкую нить, которая работает одновременно со
    // всеми остальными сопрограммами. Разница в том, что запуск возвращает
    // задание и не несет никакого результирующего значения, в то время как async
    // возвращает отложенное - легкое неблокирующее будущее, которое представляет
    // собой обещание предоставить результат позже.
    println("f2(): ")
    fun f2() = runBlocking {
      val time = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        // await() возвращает результат асинхронной задачи
        println("The answer is ${one.await() + two.await()}")
      }
      println("time = $time")
    }
    f2()
    println("--------------")
    // В этом режиме он запускает сопрограмму только тогда, когда его результат
    // требуется await или если вызывается функция запуска его задания.
    println("f3(): ")
    fun f3() = runBlocking {
      val time = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        one.start()
        two.start()
        println("The answer is ${one.await() + two.await()}")
      }
      println("time = $time")
    }
    f3()
    println("--------------------")
    // Этот стиль программирования с асинхронными функциями представлен
    // здесь только для иллюстрации, потому что это популярный стиль в других
    // языках программирования. Использование этого стиля с сопрограммами Kotlin
    // настоятельно не рекомендуется
    println("async fun:")
    val time = measureTimeMillis {
      // мы можем инициировать асинхронные действия вне сопрограммы
      val one = somethingUsefulOneAsync()
      val two = somethingUsefulTwoAsync()
      // но ожидание результата должно включать либо приостановку, либо блокировку.
      // здесь мы используем `runBlocking {...}`, чтобы заблокировать основной поток в ожидании результата
      runBlocking {
        println("The answer is ${one.await() + two.await()}")
      }
    }
    println("time = $time")
    println("--------------------")

  }

  private suspend fun doSomethingUsefulOne(): Int {
    delay(1000) // сделаем вид, что мы делаем что-то полезное здесь
    return 13
  }

  private suspend fun doSomethingUsefulTwo(): Int {
    delay(1300) // сделаем вид, что мы делаем что-то полезное здесь тоже
    return 29
  }


  // Асинхронные функции. Мы можем определить асинхронные функции в стиле,
  // которые вызывают асинхронно doSomethingUsefulOne и doSomethingUsefulTwo,
  // используя асинхронный конструктор сопрограмм с явной ссылкой GlobalScope.
  // Мы называем такие функции суффиксом «… Async», чтобы подчеркнуть тот факт,
  // что они только запускают асинхронные вычисления, и для получения результата
  // необходимо использовать полученное отложенное значение. Обратите внимание,
  // что эти функции xxxAsync не являются функциями приостановки. Их можно использовать
  // откуда угодно. Однако их использование всегда подразумевает асинхронное
  // (здесь означает одновременное) выполнение их действия с вызывающим кодом.
  // The result type of somethingUsefulOneAsync is Deferred<Int>
  private fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
  }
  // The result type of somethingUsefulOneAsync is Deferred<Int>
  private fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
  }

}