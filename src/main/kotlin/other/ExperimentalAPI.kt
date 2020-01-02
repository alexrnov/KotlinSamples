package other


/*
@ExperimentalDateTime // ok: class is marked as experimental
object ExperimentalSamples {
  val d = DateProvider()
  @JvmStatic
  fun main(args: Array<String>) {
    //val dateProvider = DateProvider() // error DateProvider() is experimental

  }

}

*/
// не распростронять экспериментальное состояние на код (non-propagating use)
/*
 * В модулях, которые не предоставляют собственного API, таких как модули
 * приложений, можно использовать экспериментальные API без
 * распространения экспериментального статуса на код.
 */
/*
@UseExperimental(ExperimentalDateTime::class)
class ClassExp1 {
  fun f() {
    val v = DateProvider()
  }
}
*/


// распространить экспериментальное состояние на код (propagating use)
/*
 * При использовании экспериментального API в коде, предназначенном
 * для стороннего использования (библиотека), можно отметить свой API
 * как экспериментальный.
 */
//@Experimental
//@Retention(AnnotationRetention.BINARY)
//@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
//annotation class ExperimentalDateTime // experimental api marker

//@ExperimentalDateTime
//class DateProvider  // experimental class
