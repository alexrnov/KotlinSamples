package classesAndObjects

object NestedClassesSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    //внутренний класс доступен по ссылке на определение внешнего класса
    Outer1.Nested().f1()
    val outer1 = Outer2()
    outer1.f2()
    outer1.Nested().f1() // переменная изменяется
    outer1.f2() // переменная изменилась
    println("---------------------")
    val outer3 = Outer3()
    println("---------------------")
    val outer4 = Outer4()
  }
}

class Outer1 {
  private var v: String = "v"
  class Nested { // вложенный класс
    fun f1() {
      //v = "v2" // переменные внешнего класса недоступны
      println("classesAndObjects.Outer1 class, f1()")
    }
  }
}

class Outer2 {
  private var v: String = "v2"
  // вложенный класс, помеченный как inner, получает доступ к внешним полям
  inner class Nested {
    fun f1() {
      v = "v5" // ссылка на переменные внешнего класса доступна
      println("classesAndObjects.Outer2 class, f1(), v = $v")
    }
  }
  fun f2() {
    println("classesAndObjects.Outer2 class, classesAndObjects.f2(), v = $v")
  }
}

class Outer3 {
  class Nested(val v1: String, val v2: String) {
    var v3 = "before"
  }

  // может быть созданно несколько объектов внутренних классов
  private val n1 = Nested("1", "1")
  private val n2 = Nested("2", "2")
  private val n3 = Nested("3", "3")

  init {
    n3.v3 = "after"
    println(n1.v1 + " " + n1.v2 + " " + n1.v3)
    println(n2.v1 + " " +n2.v2 + " " + n2.v3)
    println(n3.v1 + " " +n3.v2 + " " + n3.v3)
  }
}

class Outer4 {
  inner class Nested(val v1: String, val v2: String) {
    var v3 = "before"
  }

  // может быть создано несколько объектов внутренних классов
  private val n1 = Nested("1", "1")
  private val n2 = Nested("2", "2")
  private val n3 = Nested("3", "3")

  init {
    n3.v3 = "after"
    println(n1.v1 + " " + n1.v2 + " " + n1.v3)
    println(n2.v1 + " " +n2.v2 + " " + n2.v3)
    println(n3.v1 + " " +n3.v2 + " " + n3.v3)
  }
}