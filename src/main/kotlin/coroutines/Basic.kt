package coroutines
import kotlinx.coroutines.*

object CoroutinesBasicSample {
  @JvmStatic
  fun main(args: Array<String>) {
    GlobalScope.launch { // запустить новую сопрограмму в фоновом режиме и продолжить
      delay(1000L) // неблокирующая задержка на 1 секунду (единица времени по умолчанию - мс)
      println("World") // print after delay
    }
    println("Hello, ") // основной поток продолжается, пока сопрограмма задерживается
    // блокировать основной поток на 2 секунды, чтобы поддерживать JVM
    Thread.sleep(2000L)
  }
}