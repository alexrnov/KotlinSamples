package coroutines
import kotlinx.coroutines.*
import kotlin.concurrent.thread

object CoroutinesBasicSample {
  @JvmStatic
  fun main(args: Array<String>) {
    println("id1 = " + Thread.currentThread().name)
    GlobalScope.launch { // запустить новую сопрограмму в фоновом режиме и продолжить
      // неблокирующая задержка на 1 секунду (единица времени по умолчанию - мс)
      // не блокирует поток а приостанавливает сопрограмму
      delay(1000L)
      println("World") // print after delay
    }
    println("Hello, ") // основной поток продолжается, пока сопрограмма задерживается
    // блокировать основной поток на 2 секунды, чтобы поддерживать JVM
    // и выполнить код в GlobalScope.launch {}, т.к. время жизни сопрограммы
    // ограничено только временем жизни всего приложения
    Thread.sleep(2000L)

    thread {
      //delay(1000L) // функция delay() доступна только в сопрограмме GlobalScope.launch { }
    }

    println("----------------------")
    // при таком смешивании delay() и Thread.sleep() как в примере выше легко
    // потерять кто блокирует а кто нет. Поэтому давайте подробно расскажем
    // о блокировке с использованием компоновщика сопрограмм runBlocking
    // Результат такой же как в коде в начале , но этот код использует только неблокирующую задержку.
    GlobalScope.launch { // запустить новую сопрограмму в фоновом режиме и продолжить
      delay(1000L)
      println("World")
    }
    println("Hello, ") // основной поток продолжается здесь немедленно
    runBlocking {
      // но это выражение блокирует основной поток
      delay(2000L) // мы задерживаемся на 2 секунды, чтобы поддержать JVM
    }

    println("----------------------")
    println("f(): ")
    // более идиоматическая запись кода выше. Здесь runBlocking <Unit> {...} работает как
    // адаптер, который используется для запуска главной сопрограммы верхнего уровня.
    fun f() = runBlocking<Unit> { // start main coroutine
      GlobalScope.launch { // запустить новую сопрограмму в фоновом режиме и продолжить
        delay(1000L)
        println("World")
      }
      println("Hello, ") // основной поток продолжается здесь немедленно
      delay(2000L) // мы задерживаемся на 2 секунды, чтобы поддержать JVM
    }
    f()

    //Это также способ написания модульных тестов с приостановками:
    /*
    class MyTest {
    @Test
    fun testMySuspendingFunction() = runBlocking<Unit> {
        // here we can use suspending functions using any assertion style that we like
    }
    */

    // Временная задержка, пока работает другая сопрограмма, не очень
    // хороший подход. Давайте явно подождем (неблокирующим образом),
    // пока не завершится запущенное нами фоновое задание:
    println("----------------------")
    println("f2(): ")
    fun f2() = runBlocking<Unit> {
      val job = GlobalScope.launch { // запустить новую сопрограмму и сохранить ссылку на ее работу
        delay(1000L)
        println("World!")
      }
      println("Hello,")
      job.join() // ждать, пока ребенок сопрограммы завершит работу
    }
    f2()
    // Теперь результат остается прежним, но код основной сопрограммы
    // никак не связан с длительностью фонового задания. Намного лучше.
    println("--------------------------")
    println("f3(): ")
    // функция f3() превращается в сопрограмму с помощью компоновщика
    // runBlocking. Мы можем запускать сопрограммы в этой области без
    // явного присоединения к ним, поскольку внешняя сопрограмма (в нашем
    // примере runBlocking) не завершается, пока не завершатся все сопрограммы,
    // запущенные в этой области. Таким образом, мы можем упростить наш пример:
    fun f3() = runBlocking { // this: CoroutineScope
      launch { // launch a new coroutine in the scope of runBlocking
        delay(1000L)
        println("World!")
      }
      println("Hello, ")
    }
    f3()
    println("-----------------------")

    // В дополнение к области сопрограмм, предоставляемой различными компоновщиками,
    // можно объявить собственную область с помощью компоновщика coroutineScope.
    // Он создает область сопрограммы и не завершается, пока не завершатся все запущенные
    // дочерние элементы. RunBlocking и coroutineScope могут выглядеть одинаково,
    // поскольку они оба ждут завершения его тела и всех его потомков. Основное различие между
    // этими двумя заключается в том, что метод runBlocking блокирует текущий поток для ожидания,
    // в то время как coroutineScope просто приостанавливает работу, освобождая базовый поток для
    // других применений. Из-за этой разницы runBlocking является обычной функцией, а coroutineScope - функцией приостановки.
    println("f4(): ")
    fun f4() = runBlocking { // this: CoroutineScope
      launch {
        delay(2000L)
        println("Task from runBlocking; key = ${CoroutineName.Key}")
      }

      coroutineScope { // Creates a coroutine scope
        launch {
          delay(3000L)
          println("Task from nested launch: key = ${CoroutineName.Key}")
        }

        delay(1000L)
        println("Task from coroutine scope: key = ${CoroutineName.Key}") // This line will be printed before the nested launch
      }
      println("Coroutine scope is over: key = ${CoroutineName.Key}") // This line is not printed until the nested launch completes
    }
    f4()
    println("--------------------------")
    // Давайте выделим блок кода внутри launch {...} в отдельную функцию. Когда
    // вы выполняете рефакторинг «Извлечь функцию» для этого кода, вы получаете
    // новую функцию с модификатором suspend. Функции приостановки могут использоваться
    // внутри сопрограмм так же, как обычные функции, но их дополнительная особенность
    // заключается в том, что они, в свою очередь, могут использовать другие функции приостановки,
    // например, задержку в этом примере, чтобы приостановить выполнение сопрограммы.
    println("f5(): ")
    fun f5() = runBlocking {
      println("currentThread3 = key = ${CoroutineName.Key}")
      launch {
        println("currentThread1 = key = ${CoroutineName.Key}")
        doWorld()
      }
      println("Hello, ")
    }
    f5()
    println("----------------------------")
    println("f6(): ")
    // запускает 100K сопрограмм, и через секунду каждая сопрограмма печатает
    // точку. Теперь попробуйте это с потоками. Что случилось бы? (Скорее всего,
    // ваш код выдаст ошибку нехватки памяти)
    fun f6() = runBlocking {
      repeat(100_000) { // одновременно запустить 100_000 сопрограмм
        launch {
          delay(1000L)
          println(".")
        }
      }
    }
    f6()
    println("--------------------")
    println("f7(): ")
    // Следующий код запускает долго выполняющуюся сопрограмму в GlobalScope,
    // которая печатает «Я сплю» дважды в секунду, а затем через некоторое время
    // возвращается из основной функции:
    fun f7() = runBlocking {
      GlobalScope.launch {
        repeat(1000) { i ->
          println("I'm sleeping $i ... ")
          delay(500L)
        }
      }
      delay(1300L) // просто уйти после задержки
    }
    f7()
  }

  private suspend fun doWorld() {
    println("currentThread2 = key = ${CoroutineName.Key}")
    delay(2000L)
    println("World!")
  }
}

