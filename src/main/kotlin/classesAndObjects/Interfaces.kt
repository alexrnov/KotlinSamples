package classesAndObjects

object InterfacesSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    println("InterfaceSamples")
    interfaceSample()
    eliminationNameClash()
    interfaceImplementedInterface()
  }
}

private fun interfaceSample() {
  println("interfaceSamples(): ")
  val class1 = KotlinClassI1(1)
  println("v1 = ${class1.v1}, v2 = ${class1.v2}, v3 = ${class1.v3}, v4 = ${class1.v4}")
  class1.f1()
  class1.f2()
  class1.f3()
  println("----------------------------")
}

private fun eliminationNameClash() {
  println("eliminationNameClash(): ")
  val class3 = KotlinClassI3()
  class3.f2()
  class3.f4()
  println("----------------------------")
}

private fun interfaceImplementedInterface() {
  println("interfaceImplementedInterface(): ")
  val class4 = KotlinClassI4()
  println("v1 = ${class4.v1}, v2 = ${class4.v2}, v3 = ${class4.v3}, " +
          "v4 = ${class4.v4}, v5 = ${class4.v5}")
  class4.f1() // функция переопределена в классе KotlinClassI4
  class4.f2() // функция определена в KotlinInterface1
  class4.f3() // функция переопределена в KotlinInterface2
  class4.f4() // функция переопределена в классе KotlinClassI4
  println("----------------------------")
}

/** переопределение свойства инетрфейса v1 в первичном конструкторе */
class KotlinClassI1(override val v1: Int): KotlinInterface1 {
  override val v2 = 2
  override var v3 = 3
  override fun f1() = println("override interface function f1() in class")
  // метод с реализацией можно также переопределить
  override fun f2() = println("override interface function f2() in class")
  override fun f4() { }
}

interface KotlinInterface1 {
  // все члены интерфейса являются open по умолчанию и их
  // можно переопределить
  // абстрактные свойства интерфейса
  val v1: Int
  val v2: Int
  var v3: Int
  //инициализация в интерфейсе производится через метод get()
  val v4: Int get() = 5 // постоянное свойство (var инициализировать нельзя)

  fun f1() // метод без реализации
  // методы интерфейса могут иметь реализацию (как default-методы
  // начиная с Java8)
  fun f2() { println("interface function f2()") }
  fun f3() { println("interface function f3()") }
  fun f4()
}

open class KotlinClassI2 {
  open fun f1() {
    println("KotlinClassI2: f1()")
  }

  open fun f2() {
    println("KotlinClassI2: f2()")
  }
  open fun f4() {

  }
}

// разрешение конфликта столкновения имен (name clash) при
// переопределении (подходит как для классов, так и для интерфейсов)
class KotlinClassI3: KotlinClassI2(), KotlinInterface1 {
  // переопределить метод w() который объявлен как в классе
  // KotlinClassI2 так и в интерфейсе KotlinInterface1
  override fun f2() {
    println("-")
    super<KotlinClassI2>.f2() // метод w() суперкласса
    super<KotlinInterface1>.f2() // метод w() интерфейса
    println("-")
  }

  override fun f4() {
    println("override f4(). Invoke f4() from KotlinClassI2")
    // конфликта нет, поскольку компилятор определил, что реализация
    // функции есть только в классе, а в интерфейсе нет. Поэтому здесь
    // вызывается функция из суперкласса
    super.f4()
  }

  override val v1 = 1
  override var v2 = 2
  override var v3 = 2
  override fun f1() {}
}

// интерфейс может реализовать интерфейс
interface KotlinInterface2: KotlinInterface1 {
  // переопределение свойств суперинтерфейса
  override val v1 get() = 1
  override val v4 get() = 15
  var v5: String
  override fun f3() {
    println("KotlinInterface2: override f3()")
  }
}

class KotlinClassI4: KotlinInterface2 {
  override val v2: Int = 10
  override var v3: Int =10
  override var v5 = "v5=$v2"

  override fun f1() = println("KotlinClassI4: f1()")
  override fun f4() = println("KotlinClassI4: f1()")
}

