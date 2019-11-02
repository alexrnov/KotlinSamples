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

    // создается новый экземпляр HTML, затем инициализируется вызов функции
    // которая передается как аргумент (в нашем примере это приводит к вызову
    // head и body экземпляра HTML), и затем возвращает этот экземпляр
    html {
      this.head {}
      this.body { }
    }

  }
}


class HTML {
  fun head(init: Head.() -> Unit): Head {
    val head = Head()
    head.init()
    println("head")
    //children.add(head)
    return head
  }

  fun body(function: () -> Unit) {

  }
}

class Head {}