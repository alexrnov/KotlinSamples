package other

object AnnotationsSamples {

  @JvmStatic
  fun main(args: Array<String>) {

  }
}

// определить возможные виды для аннотирования (классы, функции,
// переменные, выражения)
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION,
        AnnotationTarget.VALUE_PARAMETER,
        AnnotationTarget.EXPRESSION)
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
annotation class Fancy {

}