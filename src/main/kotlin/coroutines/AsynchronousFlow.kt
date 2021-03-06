package coroutines

import kotlinx.coroutines.delay

/**
 * Приостановка функций асинхронно возвращает одно значение, но как мы
 * можем вернуть несколько асинхронно вычисленных значений? Вот тут и
 * приходят потоки (flow) Котлина.
 */
object AsynchronousFlowSamples {

  fun f1(): List<Int> = listOf(1, 2, 3)

  fun f2(): Sequence<Int> = sequence { // sequence builder
    for (i in 1..3) {
      Thread.sleep(100) // притвориться, что мы это вычисляем
      yield(i) // yield next value (дать следующее значение)
    }

    suspend fun f3(): List<Int> {
      delay(1000) //притвориться, что мы делаем что-то асинхронное здесь
      return listOf(1, 2, 3)
    }
  }

  @JvmStatic
  fun main(args: Array<String>) {
    // несколько значений могут быть представлены с использованием коллекций
    f1().forEach { value -> print("$value ") }
    //Если мы вычисляем числа с некоторым блокирующим кодом, потребляющим
    // процессор (каждое вычисление занимает 100 мс), то мы можем представить
    // числа, используя Sequence:

    // Этот код выводит те же числа, но он ждет 100 мс перед печатью каждого.
    f2().forEach { value -> print("$value ")}
    // Однако это вычисление блокирует основной поток, на котором выполняется код.
    // Когда эти значения вычисляются с помощью асинхронного кода, мы можем пометить
    // функцию модификатором suspend, чтобы она могла выполнять свою работу без
    // блокировки и возвращать результат в виде списка:

  }
}