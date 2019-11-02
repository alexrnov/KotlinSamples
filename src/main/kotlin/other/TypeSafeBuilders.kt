package other

val children = ArrayList<String>()
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
      head {
        title { + "text"}
      }
      body {

      }
      //this.head {}
      //this.body { }
    }
    println(children)

  }
}


class HTML {

  fun head(init: Head.() -> Unit): Head {
    val head = Head()
    head.init()
    println("head")
    children.add("head")
    return head
  }

  fun title(init: Title.() -> Unit): Title {
    val title = Title()
    title.init()
    println("title")
    children.add("title")
    return title
  }

  fun body(init: Body.() -> Unit): Body {
    val body = Body()
    body.init()
    println("body")
    children.add("body")
    return body
  }
}

class Head {}

class Body {}

class Title {}

operator fun String.unaryPlus() {
  children.add(this)
}