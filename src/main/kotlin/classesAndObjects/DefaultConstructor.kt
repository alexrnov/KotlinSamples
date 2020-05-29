package classesAndObjects

object DefaultConstructorSample {

  @JvmStatic
  fun main(args: Array<String>) {
    Sample("T","U")
    Sample("T")
  }

}



class Sample {
  private var s = ""

  constructor(t: String)  {
    s = t
    println("constructor 1 = $s")
  }

  constructor(t: String, u: String) {
    s = t + u
    println("constructor 2 = $s")
  }
}