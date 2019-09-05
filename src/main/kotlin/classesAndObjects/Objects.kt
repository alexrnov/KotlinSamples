package classesAndObjects

import java.util.*

object SamplesObject {
  private var i: Int = 0
  @JvmStatic
  fun main(args: Array<String>) {
    simpleObject()
    // объект является наследником обычного класса и наследует интерфейс
    Object1.f()
    // анонимный объект можно определить непосредственно при
    // обявлении переменной (только локальной или приватной).
    // В данном случае объект также является наследником
    // обычного класса и наследует интерфейс
    val ob1 = object: ClassAForObject(3, 4), InForObject {
      override fun f() {
        println("x = $x, y = $y")
      }
    }
    ob1.f()
    println(f2().x)
    f3(object : ClassBForObject(1, 2) {
      override fun f() {
        // Как и в анонимных вложенных классах java, есть доступ к
        // переменным класса. При этом, они не обязательно должны
        // быть помечены как финальные.
        i++
      }
    })
    println("i = ${i}")
    println("-----------------------------")
    // доступ к методам объекта-компаньона осуществляется по
    // ссылке на класс
    val c1 = ClassWithObj.create()
    val c12 = ClassWithObj.create()
    val c2 = ClassWithObj2.create()
    val c22 = ClassWithObj2.create()
    val c3: Factory<ClassWithFactory> = ClassWithFactory
    c3.create()
    ClassWithObj.s
    println("-----------------------")
    // в объекте есть доступ к полям
    O1.create(1.0f)
    O1.create(2.0f)
    O1.create(3.0f)
    O1.print()
    O1.print2()
    O1.c = 5
    O1.print2()
    O1.printValue()
  }
}

fun simpleObject() {
  // Object expression - выражения объектов выполняются и
  // инициализируются немедленно, когда они используются.
  val ob = object { // объявление объекта
    var x: Int = 0
    var y: Int = 1
  }
  println("x = ${ob.x}" + ", y = ${ob.y}")
}

open class ClassAForObject(val x: Int, val y: Int)
open class ClassBForObject(val x: Int, val y: Int) {
  open fun f() {

  }
}

interface InForObject { fun f() }

// Object declaration - при объявлении объектов, инициализация
// происходит при первом доступе к объекту, т.е. с замедлением
// или "лениво".
// Объект может наследоваться от обычного класса и реализовывать интерфейсы
object Object1: ClassAForObject(1, 2), InForObject {
  override fun f() {
    println("x = $x, y = $y")
  }
}

private fun f2() = object { // возвращает анонимный объект
  val x = "10"
}

private fun f3(c: ClassBForObject) {
  c.f()
}

// объект-компаньон инициализируется, когда соответствующий класс
// загружен (разрешен)
class ClassWithObj {
  // объявление объекта внутри класса может быть помечено
  // ключевым словом companion. Методы такого объекта становятся
  // доступными по ссылке на класс, например
  // val c = classesAndObjects.ClassWithObj.create(). Для кадого класса может быть
  // определен только один объект-компаньон
  companion object Factory {
    // методы и поля в объекте-компаньоне являются аналогом
    // статических методов и полей в java
    fun create(): ClassWithObj = ClassWithObj()
    val s = "string"
  }
}

class ClassWithObj2 {
  // имя объекта может быть опущено,  этом случае доступ к объекту
  // осуществляется следующим образом:
  // val c2 = classesAndObjects.ClassWithObj2.Companion.create()
  companion object {
    fun create(): ClassWithObj2 = ClassWithObj2()
  }
}

interface Factory<out T> {
  fun create(): T
}

class ClassWithFactory {
  // объект компаньон может реализовывать интерфейс
  companion object: Factory<ClassWithFactory> {
    override fun create(): ClassWithFactory = ClassWithFactory()
  }
}

object O1 {
  // в объекте могут быть объявлены поля
  private val q: Queue<Float> = ArrayDeque<Float>()
  var c: Int = 0
  fun create(f: Float) {
    q.add(f)
  }
  fun print() {
    q.forEach {
      println("q = " + it)
    }
  }
  fun print2() {
    println("c = " + c)
  }

  fun printValue(v: Float = 4f) { // значение по умолчанию
    println(v)
  }
}