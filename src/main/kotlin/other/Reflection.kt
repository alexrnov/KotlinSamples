package other

import kotlin.reflect.KClass

object ReflectionSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    classReference()

  }

  private fun classReference() {
    println("sample(): ")
    // ссылка на Kotlin-класс
    val referenceKotlin: KClass<ClassR1> = ClassR1::class
    // обратите внимание, что ссылка на класс Kotlin отличается от
    // ссылки на класс Java. Чтобы получить ссылку на класс Java,
    // используйте свойство .java в экземпляре KClass
    val referenceJava: Class<ClassR1> = ClassR1::class.java
    println("-------------")
  }

}

class ClassR1()