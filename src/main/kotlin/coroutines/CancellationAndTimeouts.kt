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
    // прервать задачи, которые выполняются одновременно
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
      delay(300L) // если это значение установить > 1000, сопрограммы успеют выполниться
      job.cancelAndJoin()
    }
    f2()
    println("----------------")
    // Отмена сопрограмм является кооперативной. Код сопрограммы должен
    // сотрудничать, чтобы быть отменяемым. Все приостановленные функции
    // в kotlinx.coroutines можно отменить. Они проверяют отмену сопрограммы
    // и выдают CancellationException при отмене. Однако, если сопрограмма
    // работает в вычислении и не проверяет отмену, ее нельзя отменить, как
    // показано в следующем примере:
    println("f3(): ")
    fun f3() = runBlocking {
      val startTime = System.currentTimeMillis()
      val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (i < 5) { // вычислительный цикл, просто тратит впустую процессор
          // print a message twice a second
          if (System.currentTimeMillis() >= nextPrintTime) {
            println("job: I'm sleeping ${i++} ...")
            nextPrintTime += 500L
          }
        }
        println("job end")
      }
      delay(1300L)
      println("main: я устал ждать!")
      job.cancelAndJoin() // cancels the job and waits for its completion
      // выполнение задания продолжится даже после вызова cancelAndJoin()
      println("main: now I can quit.")
    }
    f3()
    println("----------------------")
    // Явно проверить статус отмены. Давайте попробуем последний подход.
    println("f4(): ")
    fun f4() = runBlocking {
      val startTime = System.currentTimeMillis()
      val job = launch(Dispatchers.Default) {
        var nextPrintTime = startTime
        var i = 0
        while (isActive) { // отменяемый цикл вычислений
          // print a message twice a second
          if (System.currentTimeMillis() >= nextPrintTime) {
            println("job: I'm sleeping ${i++} ...")
            nextPrintTime += 500L
          }
        }
      }
      delay(1300L) // delay a bit
      println("main: я устал ждать")
      // цикл будет остановлен
      job.cancelAndJoin() // cancels the job and waits for its completion
      println("Сейчас я могу выйти")
    }
    f4()
    println("------------------------")

    // Отменяемые функции приостановки генерируют исключение CancellationException
    // при отмене, которое может быть обработано обычным способом. Например, выражение
    // try {...} finally {...} и функция использования Kotlin обычно выполняют свои действия
    // завершения при отмене сопрограммы:
    println("f5(): ")
    fun f5() = runBlocking {
      val job = launch {
        try {
          repeat(1000) { i ->
              println("Job: I'm sleeping $i ... ")
              delay(500L)
          }
        } finally {
          println("job: I'm running finally")
        }
      }
      delay(1300L) // delay a bit
      println("main: я устал ждать!")
      job.cancelAndJoin() // cancels the job and waits for its completion
      println("main: сейчас я могу выйти.")

    }
    f5()
    println("-------------------")
    println("f6(): ")
    // любая попытка использовать функцию приостановки в блоке finally предыдущего
    // примера вызывает исключение CancellationException, поскольку сопрограмма,
    // выполняющая этот код, отменяется. Обычно это не проблема, так как все корректные
    // операции закрытия (закрытие файла, отмена задания или закрытие любого канала связи)
    // обычно не блокируют и не включают в себя какие-либо функции приостановки. Однако в редком
    // случае, когда вам нужно приостановить выполнение в отмененной сопрограмме, вы можете
    // обернуть соответствующий код в withContext (NonCancellable) {...}, используя функцию
    // withContext и контекст NonCancellable, как показано в следующем примере:
    fun f6() = runBlocking {
      val job = launch {
        try {
          repeat(1000) { i ->
            println("job: I'm sleeping $i ...")
            delay(500L)
          }
        } finally {
            withContext(NonCancellable) {
            println("job: I'm running finnaly")
            delay(1000L) // теперь в блоке finally можно выполнить функцию приостановки
            println("job: And I've just delayed for 1 sec because I'm non-cancellable")
          }
        }
      }
      delay(1300L)
      println("main: я устал ждать")
      job.cancelAndJoin()
      println("main: теперь я могу выйти")
    }
    f6()
    println("----------------------")
    println("f7(): ")
    // наиболее очевидная практическая причина отменить выполнение сопрограммы
    // состоит в том, что время ее выполнения превысило некоторое время ожидания.
    // Хотя вы можете вручную отследить ссылку на соответствующее задание и запустить
    // отдельную сопрограмму для отмены отслеживаемой после задержки, существует
    // готовая к использованию функция TimeTime, которая это делает. Посмотрите на
    // следующий пример:
    fun f7() = runBlocking {
      withTimeout(1300L) {
        repeat(1000) { i ->
          println("I'm sleeping $i ...")
          delay(500L)
        }
      }
    }
    // вызов f7() выдаст исключение TimeoutCancellationException. Мы не видели
    // его раньше. Это связано с тем, что внутри отмененной сопрограммы исключение
    // CancellationException считается нормальной причиной завершения сопрограммы.
    // Однако в этом примере мы использовали withTimeout прямо внутри главной функции.
    // f7()
    println("------------------")
    println("f8(): ")
    // возвращает null по истечении времени ожидания вместо выдачи исключения:
    fun f8() = runBlocking {
      val result = withTimeoutOrNull(1300L) {
        repeat(1000) { i ->
          println("I'm sleeping $i ...")
          delay(500L)
        }
        "Done" // будет отменен, прежде чем он даст этот результат
      }
      println("result: $result")
    }
    f8()
  }
}