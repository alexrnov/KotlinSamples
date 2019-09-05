package functionsAndLambdas

typealias simple = () -> Unit
object LambdasSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    println("sampleFold(): ")
    sampleFold()
    println("--------------------")
    println("sampleDeclaration(): ")
    sampleDeclaration()
    println("--------------------")
    println("functionsAndLambdas.sampleInvoking(): ")
    sampleInvoking()
    println("--------------------")
    println("functionsAndLambdas.samplePassing(): ")
    samplePassing()
    println("--------------------")
    println("functionsAndLambdas.sampleImplicit(): ")
    sampleImplicit()
    println("--------------------")
    println("functionsAndLambdas.sampleReturn(): ")
    sampleReturn()
    println("--------------------")
    println("functionsAndLambdas.sampleUnderscore(): ")
    sampleUnderscore()
    println("--------------------")
    println("functionsAndLambdas.sampleAnonymous(): ")
    sampleAnonymous()
    println("--------------------")
    println("functionsAndLambdas.sampleClosures(): ")
    sampleClosures()
    println("--------------------")
    println("functionsAndLambdas.sampleReceiver(): ")
    sampleReceiver()
    println("--------------------")
    println("sampleLambda(): ")
    sampleLambda()
  }

  // стандартная функция fold() добавляет к каждому последующему
  // элементу значение произедения (сложения, вычитания и т.д.)
  // предыдущего элемента на значение acc
  private fun sampleFold() {
    val items = listOf(1, 2, 3, 4, 5)
    // параметры типов в лямбдах являются опциональными, если
    // они могут быть выведены из контекста
    val joinedToString: String = items.fold("Elenents: ", { acc, i -> acc + " " + i })
    println("joinedToString = $joinedToString")

    // ссылки на функции также могут использоваться для вызовов функций
    // более высоко порядка
    val product: Int = items.fold(1, Int::times)
    println("product = $product")

    items.fold(0, {
      acc: Int, i: Int ->
      print("acc = $acc, i = $i, ")
      val result = acc + i
      println("result = $result")
      // последнее выражение в лямдах считается возвращаемым значением
      result
    })

    //1 + 1/ 2 + 2/ 4 + 3/ 7 + 4/ 11 + 5 = 16
    val v1: Int = items.fold(1, {acc, i -> acc + i})
    println("v1 = $v1")
    //1 - 1/ 0 - 2/ -2 - 3/ -5 -4/ -9 - 5 = -14
    val v2: Int = items.fold(1, {acc, i -> acc - i})
    println("v2 = $v2")
    //5 * 1/ 5 * 2/ 10 * 3/ 30 * 4/ 120 * 5 = 600
    val v3: Int = items.fold(5, {acc, i -> acc * i})
    println("v3 = $v3")
    //6 * 1/ 6 * 2/ 12 * 3/ 36 * 4/ 144 * 5 = 720
    val v4: Int = items.fold(6, {acc, i -> acc * i})
    println("v4 = $v4")
  }

  // различные способы объявления экзепляров функциональных типов
  private fun sampleDeclaration() {
    //есть несколько способов передать экземпляр функционального типа:
    // передача с помощью переменной функционального типа
    val f1: () -> Unit = {
      println("invoke function Unit")
    }

    // аналогично объявлению выше. В качастве функционального типа
    // используется псевдоним (alias)
    val f2: simple = {
      println("invoke function Unit")
    }

    sampleFun1(f1)
    // передача с помощью кодового блока в форме лямбда выражения
    sampleFun2({ a ->
      val b = a + 1
      println("b = $b")
    })
    // передача с помощью кодового блока в форме анонимной функции
    sampleFun2(fun(i: Int) { println("i = $i") })

    // передача с помощью ссылки на объявленную функцию, свойство или конструктор
    sampleFun2(this::callableReference)

    // если один входной параметр - к нему можно обращаться с помощью it
    val f3: (Int) -> String = { // x: Int -> // или так, тогда вместо it нужно поставить x
      val typeOfString = if (it > 0) "positive" else "negative"
      // последняя инструкция в блоке выступает в качестве
      // возвращаемого значения
      typeOfString
    }
    sampleFun3(f3)

    // если входных параметров несколько - они указываются явно
    val sum: (Int, Int) -> Int = { x, y -> // или x: Int, y: Int ->
      val sumXY = x + y
      println("sumXY = $sumXY")
      sumXY
    }
    sampleFun4(sum)

    val intFunction: (Int) -> Int = IntClass()
    // передача с помощью класса, который реализует функциональный
    // тип как интерфейс
    sampleFun5(intFunction)

    // компилятор может сам вывести возвращаемый тип (Int) -> String
    val a = { i: Int -> (i + 1).toString() }
    sampleFun3(a)

    // A.(B) -> C взаимозаменяемо с (A, B) -> C
    val repeatFun: String.(Int) -> String = { times -> this.repeat(times) }
    val twoParameters: (String, Int) -> String = repeatFun // Ok
    val result = runTransformation(repeatFun)
    println("repeatFun = $result")
    val result2 = runTransformation(twoParameters)
    println("twoParameters = $result2")
  }

  // у функции w нет входного и возвращаемого параметров
  private fun sampleFun1(f: () -> Unit) {
    f.invoke() // вызов функции
    f() // вызов функции можно произвести и так
  }

  // у функции w есть входной параметр и нет выходного параметра
  private fun sampleFun2(f: (Int) -> Unit) {
    f.invoke(5)
  }

  private fun callableReference(i: Int) {
    println("callableReference = $i")
  }

  private fun sampleFun4(f: (Int, Int) -> Int) {
    f.invoke(1, 2)
  }
}

// использование экземпляра пользовательского класса, который
// реализует функциональный тип как интерфейс
class IntClass: (Int) -> Int {
  override operator fun invoke(x: Int): Int = invokeFun(x)
}

private fun invokeFun(x: Int): Int {
  println("functionsAndLambdas.invokeFun() = $x")
  return x + 1
}
// две эквивалентные записи функций, отличающиеся формой записи
// возвращаемого значения
private fun sampleReturn() {
  // неявное указание возвращаемого значения: последний оператор
  // является возвращаемым значением
  sampleFun5({ i: Int -> i })
  // явное объявление возвращаемого значения
  sampleFun5({ i: Int ->
    return@sampleFun5 i
  })
}

private fun sampleFun5(f: (Int) -> Int) {
  // вернет 6, поскольку в functionsAndLambdas.invokeFun() происходит прибавление на единицу
  val i = f.invoke(5)
  println("functionsAndLambdas.sampleFun5() = $i")
}

 private fun runTransformation(f: (String, Int) -> String): String {
  return f("hello ", 5)
}

// пример вызова функций
private fun sampleInvoking() {
  val stringPlus: (String, String) -> String = String::plus
  val intPlus: Int.(Int) -> Int = Int::plus
  println(stringPlus.invoke("<-", "->"))
  println(stringPlus("Hello, ", "world!"))
  println(intPlus.invoke(1, 1))
  println(intPlus(1, 2))
  println(2.intPlus(3))
}

private fun sampleImplicit() {
  sampleFun3({ i: Int -> i.toString() })
  // если имеется единственный параметр и если компилятор сам может
  // вывести сигнатуру, то можно не объявлять единственный параметр и
  // пропустить символ ->. Параметр будет неявно объявлен под именем it
  sampleFun3 { it.toString() } // аналогично приведенному выше
}

// у функции w есть входной и выходной параметры
private fun sampleFun3(f: (Int) -> String) {
  var s = f.invoke(5)
  println("s = $s")
  s = f.invoke(-5)
  println("s = $s")
}

private fun samplePassing() {
  sampleFun6("functionsAndLambdas.sampleFun6 string", { x -> x + 1 })
  // в Kotlin есть соглашение: если последний параметр функции сам
  // является функцией, тогда лямбда-выражение, переданное в качестве
  // соответствующего аргумента, может быть помещено вне скобок
  // также известно, как trailing lambda
  sampleFun6("functionsAndLambdas.sampleFun6 string") { x -> x + 1 }
  // если функция является единственным передаваемым параметром,
  // скобки могут быть вообще опущены
  sampleFun5 { x -> x + 1 }
}

// функция, входной параметр которой сам является функцией,
// называется функцией высшего порядка (higher-order)
private fun sampleFun6(s: String, f: (Int) -> Int) {
  println(s + ": " + f.invoke(10))
}

// если какой либо входно параметр не используется вместо него
// может быть указан символ нижнего подчеркивания (underscore)
// начиная с версии Kotlin 1.1
private fun sampleUnderscore() {
  fun sample(f: (Int, String) -> Unit) {
    f.invoke(1, "one")
  }
  val map: Map<String, Int> = mapOf("a" to 1, "b" to 2, "c" to 3)
  map.forEach { k, v -> println("k = $k : v = $v") }
  map.forEach { _, v -> println("v = $v") } // первый элемент не используется

  sample( {i: Int, s: String -> println("i = $i s = $s")} )
  // эквивалентный вариант без использования круглых скобок
  sample {i: Int, s: String -> println("i = $i s = $s")}
  sample {_, s: String -> println("s = $s")} // первый элемент не используется
}

// использование анонимных функций с ключевым словом fun без
// указания названия функции
private fun sampleAnonymous() {
  fun sample(f: (Int, String) -> Unit) {
    f.invoke(1, "one")
  }
  sample(fun(i: Int, s: String) = println("i = $i s = $s"))
  // круглые скобки обязательны
  // sample {fun(i: Int, s: String) = println("i = $i s = $s")}

  fun sample2(f: (Int, Int) -> Int) {
    val v = f.invoke(1, 2)
    println("v = $v")
  }
  // анонимная функция может быть использована, когда нужно явно
  // определить возвращаемый тип
  sample2(fun(i1: Int, i2: Int): Int {
    return i1 + i2
  })

  // типы параметров могут быть опущены, если их можно вывести из
  // контекста: результат такой же как и у записи выше
  sample2(fun(i1, i2) = i1 + i2)
}

/*
 * Лямбда-выражение или анонимная функция (а также локальная функция
 * и объектное выражение) могут получить доступ к замыканиям (closures),
 * то есть переменным, объявленным во внешней области.
 * В отличие от Java, захваченные переменные могут быть изменены
 */
private fun sampleClosures() {
  var i = 0
  fun sample(f: () -> Unit) {
    f.invoke()
  }

  sample {
    i += 1 // можно модифицировать переменную
  }
  println("i = $i")
}

private fun sampleReceiver() {
  /*
   * литерал функции с приемником вместе с его типом, где функция plus()
   * вызывается на объекте-приемнике:
   */
  val sum: Int.(Int) -> Int = { other -> plus(other) }
  val s = sum.invoke(3, 4)
  println("s = $s")
  /*
   * синтаксис анонимной функции позволяет указать непосредственно
   * тип приемника литерала функции . Это может быть полезно, если
   * необходимо объявить переменную типа функции с приемником и
   * использовать ее позже.
   */
  val sum2 = fun Int.(other: Int): Int = this + other
  val s2 = sum2.invoke(3, 4)
  println("s2 = $s2")

  // функция changeK() вызывается на объекте-приемнике:
  // будет 3 в степени 2 = 9 (а не 2 в степени 3 = 8)
  val rFun: ReceiverClass.(ReceiverClass) -> Int = { other -> changeK(other)}
  val rc1 = ReceiverClass(3)
  val rc2 = ReceiverClass(2)
  val i = rFun.invoke(rc1, rc2)
  println("i = $i")
}

private fun sampleLambda() {
  val fruits = listOf("banana", "avocado", "apple", "kiwifruit")
  fruits.filter { it.startsWith("a") } // отобрать слова, начинающие на "a"
          .sortedBy { it } // отсортировать
          //выполнить операцию над каждым элементом(приведение к верхему регистру)
          .map { it.toUpperCase() }
          .forEach { println(it) } // вывести слова
}

class ReceiverClass(private var k: Int) {
  fun changeK(v: ReceiverClass): Int {
    return Math.pow(k.toDouble(), (v.k).toDouble()).toInt()
  }
}


