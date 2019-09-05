package classesAndObjects

object InheritanceSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    initClass()
    propertiesClass()
    differentConstructors()
    defaultParameter()
    overrideSamples()
    initSample()
    initBasicClass()
    accessToSuperClass()
    abstractSamples()
  }
}

/** пример инициализации класса: свойства и блоки инициализации */
private fun initClass() {
  println("initClass(): ")
  KotlinClass1("a")
  println("----------------------------")
}

private fun propertiesClass() {
  println("propertiesClass(): ")
  var class2 = KotlinClass2(1, 2)
  class2.f()
  //kotlinClass2.i1 = 4 // свойство нельзя изменить, поскольку оно объявлено val
  class2.i2 = 3 // свойство можно изменить, поскольку оно объявлено var
  class2.f()
  class2 = KotlinClass2(1,2,"text")
  class2.f()
  println("----------------------------")
}

// порядок вызова различных конструкторов
private fun differentConstructors() {
  println("differentConstructors(): ")
  //разные конструкторы
  KotlinClass3("a", "b")
  println("-")
  KotlinClass3("a", "b", "c")
  println("-")
  KotlinClass3("a", "b", "c", "d")
  println("----------------------------")
}

// параметр по умолчанию в конструкторе. Такие параметры помогают
// избежать излишней перегрузки конструкторов
private fun defaultParameter() {
  println("defaultParameter(): ")
  var class4 = KotlinClass4() // параметр по умолчанию можно не указывать
  println(class4.defaultParameter)
  // параметр по умолчанию можно изменить
  class4 = KotlinClass4("change value")
  println(class4.defaultParameter)
  class4 = KotlinClass4("change value", "value")
  println(class4.defaultParameter)
  println("----------------------------")
}

private fun overrideSamples() {
  println("overrideSamples(): ")
  val class5 = KotlinClass5("1", "2")
  println("defaultParameter = ${class5.defaultParameter}")
  println("p2 = ${class5.p2}")
  class5.f()
  println("-")
  var class6 = KotlinClass6("1")
  println("defaultParameter = ${class6.defaultParameter}")
  println("p2 = ${class6.p2}")
  println("-")
  class6 = KotlinClass6("1", "2")
  println("defaultParameter = ${class6.defaultParameter}")
  println("p2 = ${class6.p2}")
  println("-")
  // изменить значение свойства, которое изначально в суперклассе
  // было отмечено как val
  class6.v2 = 5
  println(class6.v2)
  println("-")
  // переопределение свойства (v2 = 10) можно осуществить в конструкторе
  val class7 = KotlinClass7("change value", 10)
  println("v2 = ${class7.v2}")
  println("----------------------------")
}

private fun initSample() {
  println("initSample(): ")
  val class8 = KotlinClass8("a", "b", "c")
  class8.f()
  println("----------------------------")
}

private fun initBasicClass() {
  println("initBasicClass(): ")
  KotlinClass10("name", "lastName")
  println("----------------------------")
}

private fun accessToSuperClass() {
  println("access(): ")
  val class12 = KotlinClass12("name2")
  class12.f1()
  class12.f2()
  println("class12.v = ${class12.v}")
  class12.f3()
  println("----------------------------")
}

private fun abstractSamples() {
  println("abstractSamples: ")
  val class13 = KotlinClass13()
  class13.f1()
  println("-")
  val class15 = KotlinClass15()
  class15.f1()
  println("----------------------------")
}

// private - обозначает, что зона видимости данного класса - только
// этот файл.
// пример инициализации
private class KotlinClass1(name: String) {
  var firstProperty = "первое свойство: $name".also(::println)

  init {
    println("первый блок инициализации $name")
  }

  val secondProperty = "второе свойство: ${name.length}".also(::println)
  private val parameter3: String = f()
  private val parameter4: Int = name.length

  init {
    println("второй блок инициализации ${name.length}")
    println("parameter3: $parameter3")
    println("parameter4: $parameter4")
  }

  private fun f(): String {
    return "5"
  }
}

private class KotlinClass2(val i1: Int, var i2: Int) {
  init {
    println("init ClassKT2: i1 = $i1, i2 = $i2")
  }

  private var s: String? = null

  constructor(i1: Int, i2: Int, s: String): this(i1, i2) {
    println("secondary constructor")
    this.s = s
  }

  fun f() {
    println("i1 = $i1, i2 = $i2 s = ${s?.also {"$s"}?: "null"}")
    //аналог выражения выше
    // ${s?.also {"$s"}?: "null"} можно записать и как
    // ${if (s != null) "s = $s" else "s = null")}
    }
  }

// порядок инициализации: сначала параметры, потом блок init, далее
// конструктор и затем вспомогательный конструктор
private class KotlinClass3 {
  private var p: Int = 4.also { println("p = $it") }
  /**
   * Код в блоке инициализатора фактически становиться частью
   * основного конструктора
   */
  init {
    p = 5
    println("init, p = $p")
  }

  //порядок вызова: init, constructor 1
  constructor(s1: String, s2: String) {
    println("constructor 1")
  }

  //порядок вызова: init, constructor 2
  constructor(s1: String, s2: String, s3: String) {
    println("constructor 2")
  }

  //порядок вызова: init, constructor 2, constructor3
  constructor(s1: String, s2: String, s3: String, s4: String): this(s1, s2, s3) {
    println("constructor 3")
  }
}

/**
 * Ключевое слово open означает, что класс можно расширить.
 * Без ключевого слова класс является нерасширяемым (final).
 * В конструкторе используется параметр по умолчанию
 */
private open class KotlinClass4(val defaultParameter: String = "default value") {
  open var v1: Int = 5 // открытое свойство
  open val v2: Int = 7
  open var v3: Int = 9

  var p2: String? = null

  constructor(defaultParameter: String, p2: String): this(defaultParameter) {
    this.p2 = p2
  }

  open fun f() {}

  fun f2() {}

  open fun f3() {}
}

/**
 * Класс наследуется от класса KotlinClass4. Т.к. класс KotlinClass5 имеет
 * первичный конструктор, базовый класс инициализируется
 * здесь же используя параметры первичного конструктора.
 */
private class KotlinClass5(a: String, b: String): KotlinClass4(a, b) {
  override fun f() {
    println("override w()")
  }
}

/**
 * Если у класса нет первичного конструктора, то каждый вторичный
 * конструктор должен инициализировать базовый тип с помощью
 * ключевого слова super
 */
private open class KotlinClass6: KotlinClass4 {
  override var v1: Int = 6 // переопределить свойство из класса KotlinClass4
  // переопределить val свойство из класса KotlinClass4 в var-свойство
  override var v2: Int = 8
  //override val v3: Int = 10 // но var нельзя переопределить как val
  constructor(a: String): super(a) {}
  constructor(a: String, b: String): super(a, b) {}

  // нельзя объявить метод с таким же именем, как и у метода в
  // суперклассе даже если метод в суперклассе не помечен как open
  //fun f2() {}

  /**
   * Этот метод нельзя переопределить в классе, который будет наследоваться
   * от этого класса. Если бы слова final не было, тогда слово override
   * подразумевает, что метод является open, и явно писать ключевое слово
   * open не нужно.
   */
  final override fun f3() {}
}

/**
 * Можно использовать ключевое слово override для переопределия
 * свойства в первичном конструкторе
 */
private class KotlinClass7(a: String, override var v2: Int): KotlinClass6(a) {
  // override fun f3() {} // нельзя переопределить
}

class KotlinClass8(val s1: String, s2: String, var s3: String) {

  private var s2: String? = null

  init {
    //проинициализировать свойство можно и в блоке init()
    //но более простой способ используется для переменных s1 и s2
    this.s2 = s2
  }

  fun f() {
    println("s1 = $s1")
    println("s2 = $s2")
    println("s3 = $s3")
  }
}

// ко времени выполнения конструктора базового класса свойства,
// объявленные или переопределенные в производном классе,
// еще не инициализированы. При проектировании базового класса
// следует избегать использования открытых членов в конструкторах,
// инициализаторах свойств и блоках инициализации.
open class KotlinClass9(val name: String) {
  // вторая инициализация
  init { println("блок инициализации в базовом классе") }
  // третья инициализация
  open val size: Int =
          name.length.also { println("размер name в базовом классе: $it") }
}

class KotlinClass10(name: String, val lastName: String):
        // первая инициализация
        KotlinClass9(name.capitalize().also { println("name for KotlinClass9: $it") } ){
  // четвертая инициализация
  init {
    println("блок инициализации в наследуемом классе")
  }

  // пятая инициализация
  override val size: Int = (super.size + lastName.length).also {
    println("размер lastName в наследуемом классе: $it")
  }

  fun f2() {
    var v = ""
    println(v)
  }
}

open class KotlinClass11(name: String) {
  val x = 1
  open var v: Int = 2

  open fun f1() {
    println("KotlinClass11: f1()")
  }
}

// доступ к полям и методам суперкласса
class KotlinClass12(s: String): KotlinClass11(s) {
  override var v: Int = super.x * 10

  override fun f1() {
    println("KotlinClass12: override f1()")
  }

  fun f2() {
    super.f1() // доступ к методу суперкласса
    println("super.x = ${super.x}") // доступ к свойству суперкласса
    println("super.v = ${super.v}")
  }

  fun f3() {
    InnerKotlinClass12().f4() // вызов метода внутреннего класса
  }

  // доступ к методам и свойствам суперкласса из внутреннего класса
  inner class InnerKotlinClass12 {
    fun f4() {
      println("InnerKotlinClass12: ")
      // доступ к методу суперкласса (будет "KotlinClass11: f1()"
      // а не "KotlinClass12: override f1()")
      super@KotlinClass12.f1()
      //доступ к свойству суперкласса (будет два а не десять)
      println("super${'$'}ClassKT14.v = ${super@KotlinClass12.v}")
    }
  }
}

open class KotlinClass13 {
  open fun f1() = println("KotlinClass13: f1()")
}

//можно сделать абстрактным как класс, так и его метод
abstract class KotlinClass14: KotlinClass13() {
  override abstract fun f1()
}

class KotlinClass15: KotlinClass14() {
  override fun f1() = println("KotlinClass15: f1()")
}

