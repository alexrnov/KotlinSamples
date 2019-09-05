package classesAndObjects

// Псевдонимы используются для сокращения объявлений типов.
// Они должны распологаться на верхнем уровне.

// псевдонимы полезны при объявлении дженериков
typealias stringGen = AliasesClass<String>
typealias intGen = AliasesClass<Int>
typealias booleanGen = AliasesClass<Boolean>
typealias anyGen<T> = AliasesClass<T> // псевдоним с обобщенным типом

// различные псевдонимы для функциональных типов
typealias fType1 = (Int, String) -> Unit // псевдоним для функционального типа
// псевдоним для функционального типа с обобщенным типом
typealias fType2<T> = (T) -> Unit

typealias Nested = AliasesClass.Nested // псевдоним для вложенного класса
typealias Inner = AliasesClass<String>.Inner // псевдоним для внутреннего класса

object AliasesSimples {

  // псевдоним может использоваться вместо конструктора
  private val s1: stringGen = stringGen("")
  private val i1: intGen = AliasesClass(5)
  private val b1 = booleanGen(true)
  private val f1 = anyGen(4.5f)

  private val ft1: fType1 = { i: Int, s: String -> println("i = $i, s = $s" ) }
  private val ft2: fType2<Int> = { i: Int -> println("i = " + i.toString()) }
  private val ft3: fType2<Double> = { println("d = $it") }

  private val nestedClass: Nested = AliasesClass.Nested()
  private val innerClass: Inner = AliasesClass("").Inner()

  @JvmStatic
  fun main(args: Array<String>) {
    println("Псевдонимы в дженериках: ")
    s1.print()
    i1.print()
    b1.print()
    f1.print()
    println("------------------------------------")
    println("Псевдонимы для функциональных типов: ")
    invokeF1(ft1)
    invokeF2(ft2)
    invokeF3(ft3)
    println("------------------------------------")
    println("Псевдонимы для вложенных и внутренних классов: ")
    nestedClass.f() // вызов функции вложенного класса
    innerClass.f() // вызов функции внутреннего класса
    println("------------------------------------")
  }

  private fun invokeF1(f: fType1) {
    f.invoke(1, "s")
  }

  private fun invokeF2(f: fType2<Int>) {
    f.invoke(2)
  }

  private fun invokeF3(f: fType2<Double>) {
    f(4.5)
  }
}

class AliasesClass<T>(private val argument: T) {
  private val v = "v"

  class Nested { // вложенный класс (переменные внешнего класса недоступны)
    fun f() = println("Nested class")
  }

  inner class Inner { // внутренний класс (доступны переменные внешнего класса)
    fun f() = println("Inner class, v = $v")
  }

  fun print() {
    when (argument) {
      is String -> println("argument is String")
      is Int -> println("argument is Int")
      is Boolean -> println("argument is Boolean")
      else -> println("other type")
    }
  }
}

