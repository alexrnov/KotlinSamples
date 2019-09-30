@file:JvmName("Annotation samples") // аннотирование всего файла
package other

import java.lang.reflect.Field
import kotlin.reflect.KClass


object AnnotationsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    usage()
    constructors()
    lambdas()
    useSiteTarget()
  }

  private fun usage() {
    println("usage(): ")
    val classAnn = ClassAnn("abs")
    println(classAnn.f("abcde"))
    println("----------------------")
  }

  private fun constructors() {
    println("constructors(): ")
    // аннотация с параметром
    @Ann2("ann2") class ClassForAn1()

    // если аннотация используется в качестве параметра другой
    // аннотации, ее имя не имеет префикс @
    @Ann4("", Ann3("")) class ClassForAn2()

    // если необходимо указать класс в качестве аргумента аннотации,
    // используйте класс Kotlin (KClass). Компилятор Kotlin автоматически
    // преобразует его в класс Java, так что код Java сможет нормально
    // видеть аннотации и аргументы.
    @Ann5(String::class, Int::class) class ClassForAn3()
    println("----------------------")
  }

  // аннотации также могут использоваться для лямбд
  private fun lambdas() {
    println("lambdas(): ")
    // они будут применены к методу invoke (), в который генерируется
    // тело лямбды. Это полезно для таких платформ, как Quasar,
    // которые используют аннотации для управления параллелизмом.
    val f = @Ann6 { println("lambda") }
    println("----------------------")
  }

  // при аннотировании свойства или параметров главного конструктора
  // существует несколько элементов Java, которые генерируются из
  // соответствующего элемента Kotlin, и, следовательно, может
  // существовать несколько возможных расположений для аннотации
  // в сгенерированном байт-коде Java. Чтобы указать, как именно должна
  // быть создана аннотация, используйте следующий синтаксис:
  private fun useSiteTarget() {
    println("useSiteTarget(): ")

    class ClassForAnn4(@field:Ann1 val foo: Field, // annotate Java field
                       @get:Ann1 val bar: String, // annotate Java getter
                       @param:Ann1 val quux: String) // annotate Java constructor parameter

    /*
    class Example {
      @set:[Inject VisibleForTesting]
      var collaborator: Collaborator
    }
    */
    println("----------------------")
  }
}

// если нужно аннотировать первичный конструктор класса, нужно
// добавить ключевое слово constructor при объявлении, и добавить
// аннотацию перед ним
@Ann1 class ClassAnn @Ann1 constructor(private val v: String) {

  var x: String? = null
    @Ann1 set // можно также аннотировать методы доступа к свойствам

  @Ann1 fun f(@Ann1 p: String): Int {
    return (@Ann1 p.length)
  }
}
// определить возможные виды для аннотирования (классы, функции,
// переменные, выражения, конструкторы и т.д.)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.EXPRESSION, AnnotationTarget.CONSTRUCTOR,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER,
        AnnotationTarget.FIELD)
// указывает, хранится ли аннотация в скомпилированных файлах
// классов и является ли она видимой посредством рефлексии во
// время выполнения (по умолчанию и то, и другое имеет значение true);
@Retention(AnnotationRetention.SOURCE)
// позволяет использовать одну и ту же аннотацию на одном элементе
// несколько раз;
@Repeatable
// указывает, что аннотация является частью открытого API и должна
// быть включена в сигнатуру класса или метода, показанную в
// созданной документации API.
@MustBeDocumented
annotation class Ann1

// аннотация может иметь конструктор, в который передаются параметры
annotation class Ann2(val why: String)

annotation class Ann3(val expression: String)
annotation class Ann4(val message: String,
                            val ann3: Ann3 = Ann3(""))

annotation class Ann5(val arg1: KClass<*>, val arg2: KClass<out Any>)

annotation class Ann6


