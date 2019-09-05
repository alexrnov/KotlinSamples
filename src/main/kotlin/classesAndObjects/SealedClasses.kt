package classesAndObjects

object SealedClasses {

  @JvmStatic
  fun main(args: Array<String>) {
    //val c = `Sealed class`() // нельзя создать напрямую sealed-класс
    val c1 = ClassS1()
    val c2 = ClassS2()
    val c3 = ObjectS3
    println(eval(c1))
    println(eval(c2))
    println(eval(c3))
  }
}

// герметичный класс является абстрактным, и его нельзя создать напрямую
sealed class SealedClass {
  abstract fun f1()
}

// подклассы sealed-класса должны быть объявлены в том же файле
class ClassS1: SealedClass() {
  override fun f1() {}
}
class ClassS2: SealedClass() {
  override fun f1() {}
}
object ObjectS3: SealedClass() {
  override fun f1() {}
} // но класс, который наследуется уже от подкласса sealed-класса,
// может размещаться где угодно

// главное преимущество sealed-классов вступает в игру при использовании
// when-выражений
fun eval(s: SealedClass): Int = when(s) {
  is ClassS1 -> 0
  is ClassS2 -> 1
  is ObjectS3 -> 2
// else не требуется, потому что перечислены все случаи
}