package other

import kotlin.reflect.KClass

object ReflectionSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    classReference()
    boundClassReference()
    functionReferences()
    functionComposition()
  }

  private fun classReference() {
    println("sample(): ")
    // ссылка на Kotlin-класс
    val referenceKotlin: KClass<ClassR1> = ClassR1::class
    // обратите внимание, что ссылка на класс Kotlin отличается от
    // ссылки на класс Java. Чтобы получить ссылку на класс Java,
    // используйте свойство .java в экземпляре KClass
    val referenceJava: Class<ClassR1> = ClassR1::class.java
    println("----------------------------")
  }

  // для работы функции qualifiedName нужно в gradle добавить
  // compile group: 'org.jetbrains.kotlin', name: 'kotlin-reflect', version: '1.2.71'
  private fun boundClassReference() {
    println("boundClassReference(): ")
    fun f(obj: Any) {
      println(if (obj is ClassR2) "obj is ClassR1, path: ${obj::class.qualifiedName}"
                  else "obj is not ClassR1, path: ${obj::class.qualifiedName}")
    }
    f(ClassR3())
    f(ClassR2())
    f(ClassR1())
    println("----------------------------")
  }

  private fun functionReferences() {
    println("functionReferences():")
    fun isOdd(x: Int) = x % 2 != 0 // вернет true если число нечетное
    fun isOdd(x: String) = x == "a" || x =="c" || x == "e" || x == "g"
    println("1 is odd: " + isOdd(1))
    println("2 is odd: " + isOdd(2))
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    // ::isOdd - Значение функционального типа (Int) -> Boolean
    println(numbers.filter(::isOdd))
    // может использоваться с перегрузкой
    val numbers2 = listOf("a", "b", "c", "d", "e", "f", "g")
    println(numbers2.filter(::isOdd))
    // можно также предоставить необходимый контекст, сохранив ссылку
    // на метод в переменной с явно указанным типом:
    val predicate: (String) -> Boolean = ::isOdd
    // При необходимости использования члена класса или расширяемой
    // функции, их необходимо квалифицировать, например: String::toCharArray

    // Следует отметить, что даже если инициализировать переменную со
    // ссылкой на функцию расширения, предполагаемый тип функции не
    // будет иметь получателя (он будет иметь дополнительный параметр,
    // принимающий объект получателя). Для использования типа функции
    // с получателем укажите тип явно:
    val isEmptyStringList: List<String>.() -> Boolean = List<String>::isEmpty
    println("list is empty: " + numbers2.isEmptyStringList())
    val numbers3 = ArrayList<String>()
    println("list is empty: " + numbers3.isEmptyStringList())
    println("----------------------------")
  }

  private fun functionComposition() {
    println("functionComposition(): ")
    // Он возвращает композит из двух переданных ему функций:
    // compose (f, g) = f (g (*)). Теперь можно применить его к
    // вызываемым ссылкам:
    fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
      return { x -> f(g(x))}
    }
    println("----------------------------")
  }
}

class ClassR1

open class ClassR2

class ClassR3: ClassR2()