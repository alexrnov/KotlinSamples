package classesAndObjects

object ExtensionsSample {

  @JvmStatic
  fun main(args: Array<String>) {
    println("функция расширения swap2, добавленная к MutableList<Int>: ")
    val list1 = mutableListOf(1, 2, 3, 4, 5)
    print("Первоначальный список: ")
    list1.forEach { print("$it ") }
    println()
    // поменять местами элементы с индексами 0 и 2
    // с помощью функии расширения
    list1.swap1(0, 2)
    print("Список изменился: ")
    list1.forEach { print("$it ") }
    println()
    println("-------------------------------")
    println("функция расширения swap2, добавленная к MutableList<T>: ")
    val list2 = mutableListOf("3", "2", "5", "6", "3")
    print("Первоначальный список: ")
    list2.forEach { print("$it ") }
    println()
    // поменять местами элементы с индексами 0 и 2
    // с помощью функции расширения
    list2.swap2(0,2)
    print("Список изменился: ")
    list2.forEach { print("$it ") }
    println()
    println("-------------------------------")
    println("Функция расширения прикрепляется к объекту определенного класса:")
    val classExt2 = ClassExt2()
    // будет напечатано classExt1.f(), потому что вызываемая функция
    // расширения  зависит только от объявляемого параметра classExt1
    // в функции с типом classExt1
    printExtension(classExt2)
    println("-------------------------------")
    println("Функция расширения с различной сигнатурой:")
    val classExt3 = ClassExt3()
    // будет возвращено значение "ClassExt3: f()", поскольку сигнатура
    // исходного и переопределяемого методов совпадает
    classExt3.f()
    // будет возвращено Extension function ClassExt3: f(i), поскольку
    // можно расширять методы с другой сигнатурой
    classExt3.f(5)
    println("-------------------------------")
    println("функция расширения с проверкой объекта на null:")
    var classExt4: ClassExt4?
    classExt4 = ClassExt4(3, 4)
    println("classExt4.f() = ${classExt4.f()}") // вернет v1 = 3, v2 = 4
    classExt4 = null
    println("classExt4.f() = ${classExt4.f()}") // вернет "pack1.Class18 is null"
    println("-------------------------------")
    println("предпоследний элемент списка: ")
    val list3 = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    list3.forEach { print("$it ") }
    println()
    val secondLastElement: Int = list3.secondLast()
    println("secondLastElement = $secondLastElement")
    println("-------------------------------")
    println("геттер в функции расширения: ")
    val classExt5 = ClassExt5()
    println("classExt5.s = ${classExt5.s}") // вернет 5, а не 6
    println("-------------------------------")
    println("Функция расширения в объекте-компаньене: ")
    val classExt6 = ClassExt6()
    classExt6.f2()
    println("-------------------------------")
    val classExt8 = ClassExt8()
    classExt8.f4() // вызов функции расширения из другого класса
    println("-------------------------------")
    println("Функции расширения, помеченные как open: ")
    ClassExt10().caller(ClassExt9()) // вызов функции расширения
    ClassExt11().caller(ClassExt9()) // вызов переопределенной функции расширения
  }
}

// функция расширения без использования наследования
// добавить функцию swap к MutableList<Int>
private fun MutableList<Int>.swap1(index1: Int, index2: Int) {
  val tmp = this[index1]
  this[index1] = this[index2]
  this[index2] = tmp
}

// функция расширения без использования наследования
// добавить функцию swap к MutableList с любым типом
private fun <T> MutableList<T>.swap2(index1: Int, index2: Int) {
  val tmp = this[index1]
  this[index1] = this[index2]
  this[index2] = tmp
}

// функция расширения без использования наследования
// возвращает предпоследний элемент списка
private fun <T> List<T>.secondLast(): T {
  return this[this.size - 2]
}


open class ClassExt1

class ClassExt2: ClassExt1()

fun ClassExt1.f() = println("ClassExt1.f()")

fun ClassExt2.f() = println("ClassExt2.f()")

fun printExtension(classExt1: ClassExt1) = "printExtension: " + classExt1.f()

class ClassExt3 {
  fun f() = println("ClassExt3: f()")
}

// не будет расширятся, поскольку pack1.Class17 содержит метод с той
// же сигнатурой
fun ClassExt3.f() {
  println("Extension function ClassExt3: f()")
}

// но методы с другой сигнатурой доступны для расширения
fun ClassExt3.f(i: Int) {
  println("Extension function ClassExt3: f(i)")
}

class ClassExt4(val v1: Int, val v2: Int) {}

// Класс ClassExt4 может быть null, и на null будет осуществлятся проверка.
// Такие расширения могут вызываться для переменной объекта, даже если
// ее значение null. По такому принципу работает метод toString() в Kotlin.
fun ClassExt4?.f(): String {
  if (this == null) {
    return "ClassExt4 is null"
  }
  return "v1 = ${this.v1}, v2 = ${this.v2}"
}

class ClassExt5 {
  var s: String = "5"
}

val ClassExt5.s: String // не сработает
  get() = "6"

class ClassExt6 {
  companion object {
    fun f1() {
      println("ClassExt6.Companion: f1()")
    }
  }
  fun f2() {
    f1()
    f3() // функция расширения
  }
}

// функция расширения для объекта компаньона
fun ClassExt6.Companion.f3() {
  println("ClassExt6.Companion: extension f3()")
}

class ClassExt7 {
  fun f1() {
    println("ClassExt7: f1()")
  }

  fun f5() {
    println("ClassExt7: f5()")
  }
}

class ClassExt8 {
  fun f2() = println("ClassExt8: f2()")

  // функция расширения может быть объявлена (и вызвана)
  // в другом классе (в данном случае в классе ClassExt8)
  fun ClassExt7.f3() {
    f2()
    f1()
    // разрешение конфликта имен
    f5() // вызвать функцию f5() из класса ClassExt7
    this@ClassExt8.f5() // вызвать функцию f5() из этого класса (ClassExt8)
  }

  fun f4() {
    val classExt7 = ClassExt7()
    classExt7.f3() // вызов функции расширения
  }

  fun f5() = println("ClassExt8: f5()")
}

class ClassExt9

open class ClassExt10 {
  // функции расширения, помеченные как open могут быть
  // переопрелены в подклассах
  open fun ClassExt9.f() {
    println("open extension function ClassExt9.f() in ClassExt10")
  }

  // будут вызываться только: функция расширения и переопределенная
  // функция расширения ClassExt9.f()
  open fun caller(classExt: ClassExt9) {
    classExt.f() // вызов функции расширения
  }
}

class ClassExt11: ClassExt10() {
  override fun ClassExt9.f() { // переопределить функцию расширения
    println("override extension function ClassExt9.f() in ClassExt11")
  }
}
