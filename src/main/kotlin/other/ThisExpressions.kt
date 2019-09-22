package other

object ThisExpressionsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    val expB = ExpA().ExpB()
    expB.r
  }
}

class ExpA {
  fun f(l: (s: String) -> Unit) {
    l.invoke("5")
  }
  inner class ExpB {
    var r = 7.foo()
    private fun Int.foo() {
      println("foo()")
      val a = this@ExpA // this ссылается на класс ExpA
      println("a = $a")
      val b = this@ExpB // this ссылается на класс ExpB
      println("b = $b")
      val c = this // this ссылается на приемник foo(), т.е. на число Int (7)
      println("c = $c")
      val c1 = this@foo // this ссылается на приемник foo(), т.е. на число Int (7)
      println("c1 = $c1")
      val funLit1 = lambda@ fun String.() {
        val d = this // ссылается на значение, переданное лямде (5)
        println("funLit1: d = $d")
      }
      f(funLit1)
      val funLit2 = { s: String ->
        val d1 = this // ссылается на приемник foo(), т.е. на число Int (7)
        println("funLit2: d1 = $d1")
      }
      f(funLit2)
    }

  }
}