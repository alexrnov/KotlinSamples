package classesAndObjects

object EnumClassesSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    /* простое перечисление */
    println(Direction.EAST)
    println("-------------------")
    /* анонимные классы в перечислениях */
    Anonim.TYPE1.f()
    Anonim.TYPE2.f()
    println("-------------------")
    /* реализация интерфейсов в перечислениях */
    Im.TYPE1.f1()
    Im.TYPE2.f1()
    Im.TYPE1.f2()
    Im.TYPE2.f2()
    println("-------------------")
    /* работа с константами перечислений */
    printAllValues<Direction>()
  }
}

// простое перечисление
enum class Direction {
  NORTH, SOUTH, WEST, EAST
}

// анонимные классы в перечислениях
enum class Anonim {
  TYPE1 {
    override fun f() {
      println("TYPE1 w()")
    }
  },
  TYPE2 {
    override fun f() {
      println("TYPE2 w()")
    }
  };
  abstract fun f()
}

// реализация интерфейсов в перечислениях
enum class Im: Inter1, Inter2 {
  TYPE1 {
    override fun f1() {
      println("TYPE1 f1()")
    }
  },
  TYPE2 {
    override fun f1() {
      println("TYPE2 f1()")
    }
  };
  override fun f2() {
    f1()
  }
}

interface Inter1 {
  fun f1()
}

interface Inter2 {
  fun f2()
}

inline fun <reified T: Enum<T>> printAllValues() {
  print(enumValues<T>().joinToString {it.name})
}