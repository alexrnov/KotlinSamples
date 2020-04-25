package coroutines


import kotlinx.coroutines.*

// Сопрограммы всегда выполняются в некотором контексте, представленном
// значением типа CoroutineContext, определенного в стандартной библиотеке
// Kotlin. Контекст сопрограммы представляет собой набор различных элементов.
// Основными элементами являются Job сопрограммы, которую мы видели ранее,
// и ее диспетчер, который рассматривается в этом разделе.
object ContextSamples {


  @JvmStatic
  fun main(args: Array<String>) {
    println("f1(): ")
    // Диспетчеры и темы. Контекст сопрограммы включает диспетчер сопрограмм
    // (см. CoroutineDispatcher), который определяет, какой поток или потоки использует
    // соответствующая сопрограмма для своего выполнения. Диспетчер сопрограмм
    // может ограничить выполнение сопрограммы конкретным потоком, отправить его
    // в пул потоков или разрешить запуск без ограничений. Все компиляторы сопрограмм,
    // такие как launch и async, принимают необязательный параметр CoroutineContext,
    // который можно использовать для явного указания диспетчера для новой сопрограммы
    // и других элементов контекста.
    fun f1() = runBlocking {
      // наследует контекст главной сопрограммы runBlocking, которая выполняется в основном потоке.
      launch { // context of the parent, main runBlocking coroutine
        println("main runBlocking: I'm working in thread: ${Thread.currentThread().name}")
      }
      // Dispatchers.Unconfined - это специальный диспетчер, который также запускается в основном потоке,
      // но на самом деле это другой механизм, который будет объяснен позже.
      launch(Dispatchers.Unconfined) { // не ограничен - будет работать с основным потоком
        println("Unconfined: I'm working in thread: ${Thread.currentThread().name}")
      }
      // Диспетчер по умолчанию, который используется при запуске сопрограмм
      // в GlobalScope, представлен Dispatchers.Default и использует общий фоновый
      // пул потоков, поэтому для запуска (Dispatchers.Default) {...} используется тот
      // же диспетчер, что и GlobalScope.launch {.. .}.
      launch(Dispatchers.Default) { // будет отправлен в DefaultDispatcher
        println("Default: I'm working in thread: ${Thread.currentThread().name}")
      }
      // newSingleThreadContext создает поток для запуска сопрограммы. Выделенная
      // нить - очень дорогой ресурс. В реальном приложении оно должно быть либо
      // освобождено, когда оно больше не требуется, с использованием функции close,
      // либо сохранено в переменной верхнего уровня и повторно использовано во всем приложении.
      launch(newSingleThreadContext("MyOwnThread")) { // получит свою новую тему
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
      }
    }
    f1()
    println("----------------------------")
    println("f2(): ")
    // ограниченные и неограниченные диспетчеры.
    // Диспетчер неопределяемых сопрограмм Dispatchers.Unconfined запускает
    // сопрограмму в потоке вызывающей стороны, но только до первой точки приостановки.
    // После приостановки это возобновляет сопрограмму в потоке, который
    // полностью определен вызывающей функцией, которая была вызвана.
    // Неограниченный диспетчер подходит для сопрограмм, которые не
    // потребляют процессорное время и не обновляют какие-либо общие данные
    // (например, пользовательский интерфейс), ограниченные конкретным потоком.

    // С другой стороны, диспетчер наследуется от внешнего CoroutineScope по умолчанию.
    // Диспетчер по умолчанию для сопрограммы runBlocking, в частности, ограничен потоком-инициатором,
    // поэтому его наследование приводит к ограничению выполнения этого потока с помощью
    // предсказуемого планирования FIFO.
    fun f2() = runBlocking {
      launch(Dispatchers.Unconfined) { // не ограничен - будет работать с основным потоком
        println("Unconfined: I'm working in thread ${Thread.currentThread().name}")
        delay(500L)
        println("Unconfined: After delay in thread ${Thread.currentThread().name}")
      }
      launch {
        println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
        delay(500L)
        println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
      }
    }
    // Таким образом, сопрограмма с контекстом, унаследованным от runBlocking {...},
    // продолжает выполняться в главном потоке, в то время как неограниченная
    // возобновляется в потоке исполнителя по умолчанию, который использует функция задержки.
    // Неограниченный диспетчер является продвинутым механизмом, который может
    // быть полезен в определенных угловых случаях, когда отправка сопрограммы для ее выполнения
    // позже не требуется или приводит к нежелательным побочным эффектам, потому что некоторая
    // операция в сопрограмме должна быть выполнена
    f2()
    println("---------------------")
    println("f3(): ")
    fun f3() = runBlocking {
      val a = async {
        log("one")
        2
      }
      val b = async {
        log("two")
        5
      }
      log("The answer is ${a.await() * b.await()}")
    }
    f3()
    println("-------------------------")
    println("f4(): ")
    // Это демонстрирует несколько новых методов. Один использует runBlocking
    // с явно заданным контекстом, а другой использует функцию withContext, чтобы
    // изменить контекст сопрограммы, оставаясь в той же подпрограмме.
    // Обратите внимание, что в этом примере также используется функция use из
    // стандартной библиотеки Kotlin для освобождения потоков, созданных
    // с помощью newSingleThreadContext, когда они больше не нужны.
    newSingleThreadContext("Ctx1").use { ctx1 ->
      newSingleThreadContext("Ctx2").use { ctx2 ->
        runBlocking(ctx1) {
          log("Started in ctx1")
          withContext(ctx2) {
            log("Working in ctx2")
          }
          log("Back to ctx1")
        }
      }
    }

    println("---------------")
    println("f5(): ")
    // выведет My job is BlockingCoroutine{Active}@5bb21b69
    // {Active} значит active = true
    fun f5() = runBlocking {
      println("My job is ${coroutineContext[Job]}")
    }
    f5()
    println("--------------")
    println("f6(): ")
    // Дети сопрограммы. Когда сопрограмма запускается в CoroutineScope другой
    // сопрограммы, она наследует свой контекст через CoroutineScope.coroutineContext,
    // и задание новой сопрограммы становится дочерним по отношению к заданию
    // родительской сопрограммы. Когда родительская сопрограмма отменяется,
    // все ее дочерние элементы также рекурсивно удаляются. Однако, когда GlobalScope
    // используется для запуска сопрограммы, родительский элемент для задания новой
    // сопрограммы отсутствует. Поэтому он не привязан к области действия, из которого
    // был запущен, и работает независимо.
    fun f6() = runBlocking {
      val request = launch {
        // it spawns two other jobs, one with GlobalScope
        // он порождает две работы, одна с GlobalScope
        GlobalScope.launch {
          println("job1: I run in GlobalScope and execute independently!")
          delay(1000)
          println("job1: I am not affected by cancellation of the request")
        }
        // and the other inherits the parent context
        // а другая наследует родительский контекст
        launch {
          delay(100)
          println("job2: I am a child of the request coroutine")
          delay(1000)
          println("job2: I will not execute this line if my parent request is cancelled")
        }
      }
      delay(500)
      request.cancel() // cancel processing of the request
      // delay a second to see what happens
      // задержаться на секунду, чтобы увидеть, что происходит
      delay(1000)
      // Кто пережил отмену запроса
      println("main: Who has survived request cancellation?")
    }
    f6()
    println("-------------------------")
    println("f7():")
    // Родительские обязанности. Родительская сопрограмма всегда ждет завершения
    // всех своих детей. Родителю не нужно явно отслеживать все дочерние элементы,
    // которые он запускает, и ему не нужно использовать Job.join, чтобы ждать их в конце:
    fun f7() = runBlocking {
      // запустить сопрограмму для обработки какого-либо входящего запроса
      val request = launch {
        repeat(3) { i -> // launch a few children jobs
          launch {
            delay((i + 1) * 200L)
            println("Coroutine $i is done")
          }
        }
        println("request: Я закончил, и я явно не присоединяюсь к своим детям, которые все еще активны")
      }
      request.join() // wait for completion of the request, including all its children
      println("Now processing of the request is complete")
    }
    f7()
    println("---------------------------")
    println("f8(): ")
    // Автоматически назначенные идентификаторы хороши, когда сопрограммы
    // регистрируются часто, и вам просто нужно сопоставить записи журнала,
    // поступающие из одной сопрограммы. Однако, когда сопрограмма связана
    // с обработкой определенного запроса или выполнением некоторой конкретной
    // фоновой задачи, лучше назвать ее явно для целей отладки. Элемент контекста
    // CoroutineName служит той же цели, что и имя потока. Он включен в имя потока,
    // который выполняет эту сопрограмму, когда включен режим отладки.
    fun f8() = runBlocking {
      log("started main coroutine")
      val v1 = async(CoroutineName("v1coroutine")) {
        delay(500)
        log("Computing v1")
        252
      }
      val v2 = async(CoroutineName("v2coroutine")) {
        delay(1000)
        log("Computing v2")
        6
      }
      log("The answer for v1 / v2 = ${v1.await() / v2.await()}")

    }
    f8()
    println("--------------------")
    println("f9():")
    // Давайте объединим наши знания о контексте, детях и работе. Предположим,
    // что у нашего приложения есть объект с жизненным циклом, но этот объект
    // не является сопрограммой. Например, мы пишем приложение для Android и
    // запускаем различные сопрограммы в контексте действия Android для выполнения
    // асинхронных операций по извлечению и обновлению данных, анимации и т. д.
    // Все эти сопрограммы должны быть отменены при разрушении действия, чтобы
    // избежать утечки памяти . Конечно, мы можем манипулировать контекстами и
    // заданиями вручную, чтобы связать жизненные циклы действия и его сопрограмм,
    // но kotlinx.coroutines предоставляет абстракцию, заключающую в себе следующее: CoroutineScope.
    // Вы должны быть уже знакомы с областью действия сопрограмм, поскольку все создатели
    // сопрограмм объявлены как расширения для нее. Мы управляем жизненными циклами
    // наших сопрограмм, создавая экземпляр CoroutineScope, связанный с жизненным циклом
    // нашей деятельности. Экземпляр CoroutineScope может быть создан с помощью фабричных функций
    // CoroutineScope() или MainScope(). Первый создает область общего назначения, а второй создает область
    // для приложений пользовательского интерфейса и использует Dispatchers.Main в качестве диспетчера по умолчанию
    // Или в качестве альтернативы, в нашей основной функции мы создаем
    // действие, вызываем нашу тестовую функцию doSomething и уничтожаем
    // действие через 500 мс. Это отменяет все сопрограммы, которые были запущены
    // с doSomething. Мы можем видеть это, потому что после уничтожения действия
    // больше не печатаются сообщения, даже если мы немного подождем.

    fun f9() = runBlocking {
      val activity = Activity()
      activity.doSomething() // run test function
      println("Launched coroutines")
      delay(500L) // delay for half a second
      println("Destroying activity!")
      activity.destroy() // cancels all coroutines
      // визуально подтвердить, что они не работают
      delay(1000) // visually confirm that they don't work
    }
    f9()
    println("-----------------------------")
  }

}

fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")