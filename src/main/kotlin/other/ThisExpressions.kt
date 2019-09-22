package other

object ThisExpressionsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    val expB = ExpA().ExpB()
    expB.r
  }
}

class ExpA {
  inner class ExpB {
    var r = 7.foo()
    private fun Int.foo() {
      println("foo()")
      val a = this@ExpA // this ссылается на класс ExpA
      println("a = $a")
      val b = this@ExpB // this ссылается на класс ExpB
      println("b = $b")
      val c = this // this ссылается на приемник foo(), т.е. на число Int
      println("c = $c")
      val c1 = this@foo // this ссылается на приемник foo(), т.е. на число Int
      println("c1 = $c1")
      val funLit = lambda@ fun String.() {
        val d = this
      }

      val funLit2 = { s: String ->
        val d1 = this
      }
    }

  }
}