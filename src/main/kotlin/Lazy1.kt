import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

object LazySample {

  @JvmStatic
  fun main(args: Array<String>) {
    //val lc = LazyClass()
    //lc.f()
    val s: String = " fddf fdg  htg "
    //val two = s.trim().split("\t".toRegex()).get(0)

    val two = s.split("\t".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    println("two = ${two[0]}")
  }
}

class LazyClass {

  private var b = true
  private var v = ArrayList<String>()

  val v5: () -> Unit = {

  }
/*
  val p: (ArrayList<String>) -> Unit by lazy {

  }
*/
  fun f() {
    b = false
    //p.invoke(v)
  }
  /*
  private operator fun Any.getValue(lazyClass: LazyClass, property: KProperty<*>): (s: ArrayList<String>) -> Unit {
    val sum: (ArrayList<String>) -> Unit = { a -> // или x: Int, y: Int ->
      a.add("sdgddf")
      println(a.toString())
    }
    return sum
  }
  */
}






