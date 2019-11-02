package other



object TypeSafeBuildersSamples {
  
  @JvmStatic
  fun main(args: Array<String>) {

    // Html - это фактически вызов функции, которая принимает
    // лямбда-выражение в качестве аргумента.
    fun html(init: HTML.() -> Unit): HTML {
      val html = HTML()
      html.init()
      return html
    }

    html {
      this.head {}
      this.body { }
    }

  }
}


class HTML {
  fun head(function: () -> Unit) {

  }

  fun body(function: () -> Unit) {

  }
}