package coroutines

import kotlinx.coroutines.*
import kotlin.concurrent.thread

object CoroutinesCancellationSamples {
  @JvmStatic
  fun main(args: Array<String>) {
    // В долго работающем приложении вам может понадобиться детальный контроль
    // над фоновыми сопрограммами. Например, пользователь мог закрыть страницу,
    // которая запустила сопрограмму, и теперь его результат больше не нужен, и его
    // действие можно отменить. Функция запуска возвращает задание, которое можно
    // использовать для отмены запущенной сопрограммы:
    fun f1() = runBlocking {
      val job = launch {
        repeat(1000) { i -> // по очереди запустить
          println("job: I'm sleeping $i ...")
          delay(500L)
        }
      }
      delay(1300L)
      println("main: я устал ждать")
      job.cancel() // отменить задание
      job.join() // ждать завершения работы
      // job.cancelAndJoin()
      println("main: сейчас я могу выйти")
    }
    f1()
    println("-------------")
    println("f2(): ")
    fun f2() = runBlocking {
      val job = launch {
        repeat(10) { i ->
          // одновременно запустить 10 сопрограмм
          launch {
            delay(1000L)
            println("$i")
          }
        }
      }
      delay(300L)
      job.cancelAndJoin()
    }
    f2()
    println("----------------")

  }
}