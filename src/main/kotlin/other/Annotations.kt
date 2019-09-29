package other

object AnnotationsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    usage()
    constructors()
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
    @Ann4("", ReplaceWith("")) class ClassForAn2()
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
        AnnotationTarget.PROPERTY_SETTER)
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
                            val replaceWith: ReplaceWith = ReplaceWith(""))