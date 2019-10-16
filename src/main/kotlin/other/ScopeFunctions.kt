package other

import java.util.*

/*
 * Стандартная библиотека Kotlin содержит несколько функций, единственной
 * целью которых является выполнение блока кода в контексте объекта. При
 * вызове такой функции для объекта с предоставленным лямбда-выражением
 * она образует временную область действия. В этой области можно получить
 * доступ к объекту без его имени. Такие функции называются функциями
 * области. Их пять: let, run, with, apply, and also.
 */
object ScopeFunctionsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    sample()
    // так как все функции области похожи, важно понимать различия
    // между ними. Есть два основных различия между такими функциями:
    // - способ обращения к объекту контекста (this or it)
    // и -возвращаемое значение
    contextObjectThisOrIt()
    lambdaResult()
    // Чтобы помочь вам выбрать правильную функцию области, мы подробно
    // опишем их и предоставим рекомендации по использованию.
    // Технически функции во многих случаях взаимозаменяемы,
    // поэтому в примерах показаны соглашения, определяющие общий
    // стиль использования.
    letExample()
    withExample()
    runExample()
    applyExample()
    alsoExample()
    takeIfAndTakeUnless()
  }

  private fun sample() {
    println("sample()")
    // типичный пример функции области области
    ScopeClass("Alice", 20, "Amsterdam").let {
      println(it)
      it.moveTo("London")
      it.incrementAge()
      println(it)
    }
    println("-")
    // Если написать то же самое без let, придется вводить новую
    // переменную и повторять ее имя при каждом использовании.
    val alice = ScopeClass("Alice", 20, "Amsterdam")
    println(alice)
    alice.moveTo("London")
    alice.incrementAge()
    println(alice)
    println("-------------------------")
  }

  private fun contextObjectThisOrIt() {
    println("contextObjectThisOrIt(): ")
    val str = "Text"
    println("run:")
    str.run { // this
      println("The receiver string length: $length")
      println("The receiver string length: ${this.length}") // делает тоже самое
    }
    println("let:")
    str.let { // it
      println("The receiver string's length is ${it.length}")
    }
    println("-")
    // this (run, with, apply). Рекомендуется для лямбд, которые работают с членами объекта -
    // присваивание свойств или вызов методов
    val adam = ScopeClass("Adam").apply {
      age = 30 // тоже самое, что и this.age = 30
      this.city = "London"
      println(code())
    }
    println("adam = $adam")
    // it (let, also). Рекомендуется, когда контекстный объект используется,
    // в основном, как аргумент в вызываемых функциях
    fun getRandomInt(): Int {
      return Random().nextInt(100).also {
        writeToLog("getRandomInt() generated value $it")
      }
    }
    val i = getRandomInt()
    // дополнительно, когда вы передаете контекстный объект как аргумент,
    // вы можете обеспечить пользоваельское имя для контекстного объекта
    // внутри области
    fun getRandomInt2(): Int {
      return Random().nextInt(100).also { value ->
        writeToLog("getRandomInt() generated value $value")
      }
    }
    val i2 = getRandomInt2()
    println("-------------------------")
  }

  private fun lambdaResult() {
    println("lambdaResult(): ")
    // let, run и with возвращают результат лямбды. Так, их можно использовать,
    // когда результат присваивается переменной, в цепочке вызовов и так далее.
    val numbers = mutableListOf("one", "two", "three")
    val countEndsWithE = numbers.run {
      add("four")
      add("five")
      count { it.endsWith("e") }
    }
    println("countEndsWithE = $countEndsWithE")
    println("numbers: $numbers")
    // Кроме того, можно игнорировать возвращаемое значение и использовать
    // функцию области для создания временной области для переменных.
    with(numbers) {
      val first = first()
      val last = last()
      println("first item: $first, last item: $last")
    }
    println("-------------------------")
  }

  private fun letExample() {
    println("letExample():")
    // контекстный объект доступен как аргумент it. Возвращаемое значение
    // - результат лямбды. Let() можно использовать для вызова одной или
    // более функций на результатах цепочек вызовов:
    val numbers = mutableListOf("one", "two", "three", "four", "five")
    val resultList = numbers.map { it.length }.filter { it > 3 }
    println("resultList = $resultList")
    // с let() это можно переписать следующим образом:
    numbers.map { it.length }.filter { it > 3 }.let {
      println(it)
      // если нужно, можно вызвать еще функции
    }
    // если кодовый блок содержит одиночную функцию с it как аргументом,
    // можно использовать метод ссылки (::) вместо лямбды:
    numbers.map {it.length}.filter {it > 3}.let(::println)
    // let часто используют для для выполнения кодового блока только с
    // non-null значениями.
    val str: String? = "Text"
    val length = str?.let {
      println("let() called on $it")
      processNonNullString(str)
      it.length
    }
    println("length = $length")
    println("-")
    // Другим примером использования let является введение локальных
    // переменных для улучшения читаемости кода:
    val numbers2 = listOf("one", "two", "three", "four")
    val modifiedFirstItem = numbers2.first().let { firstItem ->
      println("the first item of the list is '$firstItem'")
      if (firstItem.length >= 5) firstItem else "!$firstItem!"
    }.toUpperCase()
    println("modifiedFirstItem = $modifiedFirstItem")
    println("-------------------------")
  }

  private fun withExample() {
    println("withExample(): ")
    // нерасширяемая функция: контекстный объект передается как аргумент,
    // но внутри лямбды, это доступно как приемник this. Возвращаемое
    // значение - результат лямбды. Мы рекомендуем with для вызова
    // для вызова функций на контекстном объекте без обеспечения
    // результата лямбды. В коде with может читаться как
    // "с этим объектом, делать следующее"
    val numbers = mutableListOf("one", "two", "three")
    with(numbers) {
      println("'with' is called with argument $this")
      println("It contains $size elements")
    }
    // Другим примером использования with является введение
    // вспомогательного объекта, свойства или функции, который будет
    // использоваться для вычисления значения.
    val firstAndLast = with(numbers) {
      "the first element is ${first()}, the last element is ${last()}"
    }
    println("firstAndLast = $firstAndLast")
    println("-------------------------")
  }

  private fun runExample() {
    println("runExample():")
    // контекстный объект доступен как приемник (this). Возвращаемое
    // значение - результат лямбды. Run делает тоже самое что и when,
    // но вызывается как let - как функция расширения на контекстном объекте
    // Используется, когда лямбда содержит как инициализацию объекта,
    // так и вычисление возвращаемого значения.
    val service = MultiportService("https://example.kotlinlang.org", 80)
    val result = service.run {
      port = 8080
      query(prepareRequest() + " to port $port")
    }
    println("result = $result")
    // тот же самый код, записанный с помощью let()
    val result2 = service.let {
      it.port = 80
      it.query(it.prepareRequest() + " to port ${it.port}")
    }
    println("result2 = $result2")
    println("-")
    // Помимо вызова run для объекта-получателя, его можно использовать в
    // качестве функции, не связанной с расширением. Run без расширения
    // позволяет выполнить блок из нескольких операторов, где требуется
    // выражение.
    val hexNumberRegex = run {
      val digits = "0-9"
      val hexDigits = "A-Fa-f"
      val sign ="+-"
      // ? превращает исходное максимальное совпадение в минимальное
      // + превращает исходное минимальное совпадение в максимальное
      Regex("[$sign]?[$digits$hexDigits]+")
    }
    for (match in hexNumberRegex.findAll("+1234 -FFFF not-a-number")) {
      println(match.value)
    }
    println("-------------------------")
  }

  private fun applyExample() {
    println("applyExample(): ")
    // контекстный объект доступен как объект-получатель (this),
    // возвращаемое значение - сам объект. Используется когда не нужно
    // возвращать значение, а только управлять членами объекта-получателя.
    // Общий случай для apply - это конфигурация объекта. Такой вызов
    // может быть прочитан, как: "добавить следующие присвоения объекту"
    val adam = ScopeClass("Adam").apply {
      age = 32
      city = "London"
    }
    // Имея в качестве возвращаемого типа - объект-получатель, можно
    // использовать его в цепочке вызовов при более сложных вычислениях
    println("-------------------------")
  }

  private fun alsoExample() {
    println("alsoExample(): ")
    // контекстный объект доступен как аргумент it. Возвращаемое значение -
    // сам объект. also хорошо подходит для некоторых действий, которые
    // предусматривают передачу контекстного объекта в качестве аргумента.
    // Исполбзуйте also при действиях, которые не предусматривают изменение
    // объекта, такие как логгирование или вывод отладочной информации
    // Обычно вы можете удалить вызовы also из цепочки вызовов без
    // ломания программной логики. Когда вы смотрите на also в коде, вы
    // можете прочитать: "и сделать также следующее"
    val numbers = mutableListOf("one", "two", "three")
    numbers
            .also { println(it) }
            .add("four")
    println("-------------------------")
  }

  // Функции takeIf() и TakeUnless() позволяют внедрить проверки состояния
  // объекта в цепочки вызовов. Функция TakeUnless() противоположна takeIf()
  private fun takeIfAndTakeUnless() {
    println("takeIfAndTakeUnless(): ")
    fun f1(number: Int) {
      val takeIf = number.takeIf { it % 2 == 0 }
      val takeUnless = number.takeUnless { it % 2 == 0 }
      println("number: $number, takeIf: $takeIf, takeUnless: $takeUnless")
    }
    f1(4)
    f1(5)

    println("-------------------------")
  }
}

data class ScopeClass(val name: String, var age: Int = 0, var city: String = "") {
  fun moveTo(city: String) {
    this.city = city
  }

  fun incrementAge() {
    age++
  }

  fun code() = Random().nextInt()
}

fun writeToLog(message: String) {
  println("INFO: $message")
}

fun processNonNullString(str: String) {}

class MultiportService(var url: String, var port: Int) {
  fun prepareRequest(): String = "Default request"
  fun query(request: String): String = "Result for query '$request'"
}