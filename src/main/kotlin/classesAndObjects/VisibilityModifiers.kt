package classesAndObjects

object VisibilityModifiersSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    println("Такой сеттер виден только внутри файла: ")
    v1 = "10"
    println("v1 = $v1")
    println("------------------------------------")
    println("Доступ к членам класса:")
    val classVM4 = ClassVM4()
    println("c is visible: " + classVM4.c)
    println("d is visible: " + classVM4.d)
    println("a is not visible")
    println("b is not visible")
    println("w is visible: " + ClassVM4.ClassVM4in2().w)
    println("------------------------------------")
    println("Доступ к внутреннему классу:")
    val classVM4in2: ClassVM4.ClassVM4in2 = ClassVM4.ClassVM4in2()
    println("classVM4in2.w = ${classVM4in2.w}")
    println("------------------------------------")
    println("Видимость членов суперкласса в подклассе: ")
    val classVM5 = ClassVM5()
    classVM5.f()
    classVM5.f3()
    println("------------------------------------")
  }
}

/**
 * Для объявлений верхего уровня доступы public(везде), который используется
 * по умолчанию; private(внутри файла);
 * internal (везде в одном и том же модуле);
 * protected - не доступен на верхем уровне
 */

fun f() { // функции могут распологаться на верхнем уровне
  println("fun top level")
}

// свойства могут располагаться на верхем уровне
var v1: String = "v1" // геттер виден везде
  private set // сеттер виден только внутри файла

object ObjectVM1 { // объект может распологаться на верхем уровне
  fun f2() {
    println("object f2()")
  }
}

interface InterfaceVM1 { // интерфейс может распологаться на верхем уровне
  fun f()
}

class ClassVM1: InterfaceVM1 { // класс может распологаться на верхнем уровне
  override fun f() {
    println("ClassVM1: override f()")
  }
}

// класс не доступен вне файла, поскольку объявлен как private
private class ClassVM2: InterfaceVM1 {
  override fun f() {
    println("ClassVM2: override f()")
  }
}

// класс доступен везде внутри модуля
internal class ClassVM3: InterfaceVM1 {
  override fun f() {
    println("ClassVM3: override w()")
  }
}

open class ClassVM4 {
  private val a = 1
  // свойство объявленное в суперклассе как protected, при переопределении
  // в подклассах также останется protected
  protected open val b = 2 // переменная будет видна в подклассах
  internal val c = 3 // переменная будет видна в подклассах
  val d = 4 // переменная будет видна в подклассах (по умолчанию public)

  // внутренний класс виден только внутри класса pack1.ClassA, поскольку protected
  protected class ClassVM4in1 {
    public val x: Int = 5 // останется protected
  }

  // к этому внутреннему классу можно получить доступ из вне, поскольку
  // по умолчанию public
  class ClassVM4in2 {
    val w: Int = 6
    private val z: Int = 3 // переменная видна только внутри вложенного класса
  }

  fun f2() {
    val classVM4in2 = ClassVM4in2()
    classVM4in2.w // переменная w
    // переменная z не видна, поскольку объявлена как private
  }
}

class ClassVM5: ClassVM4() {
  override val b = 4

  fun f() {
    println("super.b = ${super.b}") // b is visible
    println("super.c = ${super.c}") // c is visible
    println("super.d = ${super.d}") // d is visible
  }

  fun f3() {
    // вложенный в супер-класс protected-класс виден в этом
    // наследуемом классе
    val classVM4in1 = ClassVM4in1()
    println("classVM4in1.x = ${classVM4in1.x}")
  }
}

// можно получать доступ к private-членам в файле, в котором они объявлены
class ClassVM6 {
  fun f2() {
    f() // можно получать доступ к private-методам
    // можно получать доступ к private-объектам
    val obj = ObjectVM1
    ObjectVM1.f2()
    v1 = "change v1" // здесь можно менять private set свойство
    val classVM1: InterfaceVM1 = ClassVM1() // можно создавать private-классы
    classVM1.f()
  }
}