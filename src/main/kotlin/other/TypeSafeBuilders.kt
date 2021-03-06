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
    val html: HTML = html {
      head {
        title { + "text"}
      }
      body {
        h1 { + "text2"}
        h1 { + "text3"}
      }
      //this.head {}
      //this.body { }
    }
    // вывести html-структуру
    println(html.toString())
  }
}

