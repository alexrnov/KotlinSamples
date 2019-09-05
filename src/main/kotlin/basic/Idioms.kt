package basic

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

object IdiomsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    dataClass()
    filterList()
    classes()
    lazyProperty()
    invokeJavaMethodsAndCheckNull()
    checkOfNullForCollections()
    tryExpression()
    ifExpression()
    fillArrayWithDefaultValues()
    invokeGroupMethods()
    tryWithResources()
    javaDoc()
    manyParams()
    booleanEquals()
    invokeFunctionInInsertedClass()
  }
}

private fun dataClass() {
  println("dataClass(): ")
  // создать объект класса данных
  val data1 = DataClass("parameter1", "parameter2")
  println(data1.v1)
  data1.v2 = "change parameter2"
  println(data1.v2)
  val data2 = DataClass("parameter1", "change parameter2")
  if (data1 == data2) { // метод equals для data-класса уже переопределен
    println("objects is equals")
  } else {
    println("objects is not equals")
  }
  // метод hashCode() для data-класса уже переопределен
  if (data1.hashCode() == data2.hashCode()) {
    println("hash codes equals")
  } else {
    println("hash codes not equals")
  }
  var data3 = data2.copy() // полное клонирование объекта
  data3.v2 = "p"
  println(data2.toString()) // объект data2 не изменился
  println(data3.toString())
  // доступ к компонентам с помощью стандартных функций
  println(data3.component1() + " " + data3.component2())
  println("------------------------")
}

private fun filterList() { // фильтрация списка
  println("filterList(): ")
  val list = listOf(-3, -2, -1, 0, 1, 2, 3)
  val positive = list.filter{x -> x > 0}
  //val positive = list.filter {it > 0} //еще короче
  for (e in positive) print("$e ")
  println()
  println("------------------------")
}

private fun classes() {
  fun checkInstance(obj: Any) { //определение типа
    when (obj) {
      is IdiomsClassB -> println("IdiomsClassB")
      is IdiomsClassA -> println("IdiomsClassA")
      else -> println("Not instance IdiomsClass")
    }
  }
  println("classes(): ")
  val idiomsClass = IdiomsClassB()
  checkInstance(idiomsClass)
  println("------------------------")
}

private fun lazyProperty() {
  println("lazyProperty(): ")
  val i = 5
  val p: String by lazy { // ленивое свойство
    if (i > 0) {
      "i > 0"
    } else {
      "i <=0 "
    }
  }
  println("p = $p")
  println("------------------------")
}

private fun invokeJavaMethodsAndCheckNull() {
  fun f() {
    println("method w(): empty")
  }
  fun transformValue(): String = "5"

  println("javaMethods(): ")
  val javaMethods = JavaMethodsSample()
  var i = javaMethods.value(3) // вызов метода Java
  println("i = $i")
  i = javaMethods.value(-5)
  println(i?.toString()) // проверить, если i = null
  println(i?.toString()?: "empty") // проверить, если i = null, вернуть "empty"
  i?.toString()?: f() //проверить, если i = null, выполнить метод w()

  val v1: Int? = javaMethods.notNullValue // вызов метода Java
  //выполнить, если значение не null
  v1?.let { // пройдет - v1 не null
    println("v1 is not null")
  }
  //выполнить, если значение не null
  val v2: Int? = javaMethods.nullValue
  v2?.let {
    println("v2 is not null") // не пройдет - v1 = null
  }?: println("v2 is null") // пройдет
  val v3 = v1?.let { transformValue() } ?: "v3 is null"
  println("v3 = $v3")
  val v4 = v2?.let { transformValue() } ?: "v4 is null"
  println("v4 = $v4")
  println("------------------------")
}

private fun checkOfNullForCollections() {
  println("checkOfNullForCollections(): ")
  val list = ArrayList<String>()
  //получить первый элемент коллекции, которая может быть пустой
  var s = list.firstOrNull()?: "collection empty"
  println("s = $s")
  list.add("a"); list.add("b"); list.add("c"); list.add("d")
  s = list.firstOrNull()?: "collection empty"
  println("s = $s")
  println("------------------------")
}

private fun tryExpression() {
  fun f1(color: String) {
    //вернуть оператор when (аналог метода выше)
    fun f2(color: String): Int = when (color) {
      "Red" -> 0
      "Green" -> 1
      "Blue" -> 2
      else -> throw IllegalArgumentException("other color")
    }
    val value = try { // выражение try/catch
      f2(color)
    } catch(e: IllegalArgumentException) {
      "error"
    }
    println("value = $value")
  }
  println("tryExpression(): ")
  f1("Red")
  f1("Yellow")
  println("------------------------")
}

private fun ifExpression() {
  // выражение if
  fun f(p: Int) {
    val result = if (p == 1) "one" else if (p == 2) "two" else "other"
    println(result)
  }
  println("ifExpression(): ")
  f(1)
  f(2)
  f(3)
  println("------------------------")
}

private fun fillArrayWithDefaultValues() {
  // заполнить массив значениями по умолчанию
  fun f(size: Int): IntArray {
    return IntArray(size).apply { fill(-1) }
  }
  println("fillArrayWithDefaultValues(): ")
  val a:IntArray = f(5)
  for (element in a) print("$element ")
  println()
  for (index in a.indices) print("${a[index]} ") // альтернативная запись
  println()
  println("------------------------")
}

// вызов группы методов экземпляра
private fun invokeGroupMethods() {
  println("invokeGroupMethods(): ")
  val idiomsClass = IdiomsClassB()
  with(idiomsClass) {
    f1()
    for (i in 0..2) {
      f2()
    }
    f3()
  }
  println("------------------------")
}

// чтение файла в стиле try с ресурсами java 7
private fun tryWithResources() {
  println("tryWithResources(): ")
  val pathFile: String = "." + File.separator + "input" + File.separator + "file.txt"
  val stream = Files.newInputStream(Paths.get(pathFile))
  stream.buffered().reader().use { reader -> println(reader.readText())}
  println("------------------------")
}

private fun javaDoc() {
  val idiomsClass = IdiomsClassB()
  //javadoc оформляется в стиле [value], вместо param и return
  idiomsClass.f4(5)
}

//использование синтексиса именованных аргументов. Можно использовать
// если значения всех параметров не является абсолютно ясным из контекста
private fun manyParams() {
  fun f(s: String, b1: Boolean, b2: Boolean, c1: IdiomsClassB) {
    println("s = $s")
    println("b1 = $b1")
    println("b2 = $b2")
    c1.f1()
  }
  println("manyParams(): ")
  f(s = "1", b1 = true, b2 = true, c1 = IdiomsClassB())
  println("------------------------")
}

private fun booleanEquals() {
  println("booleanEquals(): ")
  // если логическое значение может быть либо true либо false, то
  // следует использовать конструкцию (b1) или (!b1)
  val b1: Boolean = true
  if (b1) {
    println("b1 = true")
  } else {
    println("b1 = false")
  }
  // если логическое значение может быть null, то следует
  // использовать конструкцию (b == )
  val b2: Boolean? = null
  if (b2 == true) {
    println("b2 = true")
  } else {
    println("b2 = false")
  }
  println("------------------------")
}

// вызов функции вложенного класса
private fun invokeFunctionInInsertedClass() {
  println("invokeFunctionInInsertedClass(): ")
  val idiomsClass = IdiomsClassB()
  idiomsClass.f5()
  println("------------------------")
}

/**
 * DTOs (POJOs/POCOs). Класс данных. Обеспечивает следующую
 * функциональность: get()/set(); equals(); hashCode(); toString()
 * copy(); component1(), component2()
 */
data class DataClass(val v1: String, var v2: String) {}

/**
 * Ключевое слово open означает, что класс можно расширить.
 * Без ключевого слова класс является нерасширяемым (final)
 */
open class IdiomsClassA {}

class IdiomsClassB: IdiomsClassA() {
  fun f1() = println("f1()")
  fun f2() = println("f2()")
  fun f3() = println("f3()")
  /** Метод в качестве входного параметра берет [i] */
  fun f4(i: Int) {}
  fun f5() {
    InsertedClass.f6() // вызов функции вложенного класса
    f6() // альтернативный вызов функции вложенного класса
  }

  /* вложенный класс */
  companion object InsertedClass {
    fun f6() {
      println("f6() in inserted class")
    }
  }
}


