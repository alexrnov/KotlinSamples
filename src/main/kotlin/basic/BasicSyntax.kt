package basic

object BasicSyntaxSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    types()
    arrays()
    strings()
    ternaries()
    whenWithoutArgument()
    whenWithoutReturn()
    whenWithReturn()
    whenCollection()
    whenWithException()
    whileAndDo()
    returnNull()
    anyFunction()
    iteration()
  }

  private fun types() {
    val b: Byte = 1
    val i: Int = b.toInt() // явное преобразование типов
    val l = i + 1L // тип выводится из контекста Int + Long = Long
  }

  private fun arrays() {
    println("arrays(): ")
    val array: Array<String> = arrayOf("1", "2", "3") // создание массива
    for (ind in array.indices) {
      print("${array[ind]} ")
    }
    println()

    array.forEach { print("$it ")} // альтернативный перебор массива
    println()

    // альтернативное создание массива
    val array2 = Array(5, { i -> (i * 2).toString()})
    array2.forEach { print("$it ")}
    println()
    println("-")
    val a: Array<Ca?> = arrayOfNulls<Ca>(5)
    // первоначально элементы заполняются null
    for (element in a) print("$element ")
    println()
    for (i in a.indices) a[i] = Ca(i) // заполнить массив инексами
    for (k in a) print("${k!!.i} ")
    println()
    println("-")
    // массив со значениями Int заполняется нулевыми значениями
    val a2: Array<Int> = IntArray(5).toTypedArray()
    a2.forEach { print("$it ") }
    println()
    println("----------------------")
  }

  private fun strings() {
    println("strings(): ")
    val s1 = "string"
    //перебор элементов строки
    for (symbol in s1) print("$symbol ")
    println()
    println(s1[2]) // доступ к элементу строки

    val text1 = """
    line1
    line2
    line3
    """.trimMargin().trimIndent() // текст как он есть
    println("text1 = $text1")

    // предпочтительный способ многострочного текста(без \n)
    val text2: String =
    """
    line1
    line2
    line3
    """.trimIndent() // исключить внутренние отступы
    println("text2 = $text2")

    val text3: String =
    """
    line1
    line2
    line3
    """.trimMargin()
    println("text3 = $text3") // включить внутренние отступы

    println("${'$'} 9,99") // экранирование символа $
    println("----------------------")
  }

  private fun ternaries() {
    fun ternary1(b: Boolean): Int {
      //в kotlin if является выражением, т.е. возвращает значение
      //тем самым заменяется тернарый оператор в java
      val i = if (b) 1 else 0 // На java было бы: int i = b ? 5 : 3
      return i
    }

    fun ternary2(b: Boolean): Int {
      /**
       * В тернарном выражении могут использоваться скобки, при этом
       * последнее выражение является возвращающим
       */
      val i = if (b) {
        println("1")
        1 // возвращается это значение
      } else {
        println("0")
        0 // возвращается это значение
      }
      return i
    }
    println("ternaries(): ")
    println("ternary1(true) = ${ternary1(true)}, ternary1(false) = ${ternary1(false)}")
    println("ternary2(true) = ${ternary2(true)}, ternary2(false) = ${ternary2(false)}")
    println("----------------------")
  }

  // оператор when без аргумента, возвращается результат первого
  // выполняющегося условия
  private fun whenWithoutArgument() {
    println("whenWithoutArgument(): ")
    val s = "string"
    when {
      s.contains("s") -> println("contains s")
      s.contains("t") -> println("contains t")
      else -> println("not  contains s or t")
    }
    println("----------------------")
  }

  // конструкция when - аналог switch
  // вариант без ключевого слова return
  private fun whenWithoutReturn() {
    fun f(obj: Any): String =
      when (obj) {
        1 -> "one"
        2 -> "two"
        is Long -> "long"
        !is String -> "is not a String"
        else -> "unknown"
      }
    println("whenWithoutReturn(): ")
    println(f(1))
    println(f(2))
    println(f(1L))
    println(f(4.5))
    println(null)
    println("----------------------")
  }

  private fun whenWithReturn() {
    fun f(obj: Any): String {
      var s = "other type"
      when (obj) {
        is String -> s = "string"
        is Double -> s = "double"
      }
      return s
    }
    println("whenWithReturn(): ")
    println(f("1"))
    println(f(4.5))
    println(f(true))
    println("----------------------")
  }

  // проверить содержит ли коллекция объект, используя оператор in и when
  private fun whenCollection() {
    fun f(list: List<Int>): String =
      when {
        1 in list -> "1 in list"
        2 in list -> "2 in list"
        else -> "other number"
      }
    println("whenCollection(): ")
    val list = listOf(1, 2, 3, 4, 5)
    println(f(list))
    println("----------------------")
  }

  private fun whenWithException() {
    //вернуть оператор when
    fun f1(color: String): Int {
      return when(color) {
        "Red" -> 0
        "Green" -> 1
        "Blue" -> 2
        else -> throw IllegalArgumentException("other color")
      }
    }

    //вернуть оператор when (аналог метода выше)
    fun f2(color: String): Int = when (color) {
      "Red" -> 0
      "Green" -> 1
      "Blue" -> 2
      else -> throw IllegalArgumentException("other color")
    }

    println("whenWithException(): ")
    var color = f1("Red")
    println("color1 = $color")
    color = f2("Red")
    println("color2 = $color")
    try {
      f1("Yellow")
    } catch(e: IllegalArgumentException) {
      println("other color")
    }
    println("----------------------")
  }

  private fun whileAndDo() {
    println("whileAndDo(): ")
    var j = 5
    while (j > 0) {
      j--
      print("$j ")
    }
    println()
    do {
      j++
      print("$j ")
    } while (j < 5)
    println()
    println("----------------------")
  }

  private fun returnNull() {
    println("returnNull(): ")
    //Явно указывается, что возвращаемое значение может быть null
    fun f(s: String): Int? {
      if (s.isEmpty()) {
        return null
      }
      return s.length
    }
    val i: Int? = f("")
    println("i = $i")
    println("----------------------")
  }

  private fun anyFunction() {
    //Метод может принимать любой тип. Any - аналог класса Object в Java
    fun f(p: Any): String {
      //используется одно ключевое слово return на все ветвления
      //если тип переменной проверен, нет необходимости его явного приведения:
      return if (p is String && p.isNotEmpty()) {
        "Строка непустая"
      } else if (p is Int && p != 0) {
        "Целое число ненулевое"
      } else if (p is Double && p != 0.0) {
        "Вещественное число ненулевое"
      } else {
        "Неизвестный тип или нулевое число(пустая строка)"
      }
    }
    println("anyFunction(): ")
    var s: String = f("ddw")
    println("s1 = $s")
    s = f(1)
    println("s2 = $s")
    s = f(8.9)
    println("s3 = $s")
    s = f(8.9f)
    println("s4 = $s")
    println("----------------------")
  }

  private fun iteration() {
    println("iteration(): ")
    val list = listOf("a", "b", "c", "d", "x", "w")
    for (element in list) print("$element ") // перебор элементов списочного массива
    println()
    for (index in list.indices) { // перебор индексов списочного массива
      println("index = $index, element = ${list[index]}")
    }
    println()
    var index = 0
    while (index < list.size) { // перебор индексов списочного массива
      println("index = $index, element = ${list[index]}")
      index++
    }
    println()
    // проверить, содержит ли коллекция объект, используя оператор in
    if ("a" in list) {
      println("a contain in list")
    } else {
      println("a not contain in list")
    }
    if ("w" !in list) {
      println("w not contain in list")
    } else {
      println("w contain in list")
    }

    val i = 5
    if (i in 1..10) {
      println("number $i is range 1..10")
    } else {
      println("number $i is not range 1..10")
    }
    //если индекс 3 выходит за диапазон индексов списочного массива
    if (i !in 0..list.lastIndex) {
      println("index is out of range list")
    } else {
      println("index in range list")
    }
    if (list.size !in list.indices) {
      println("размер списка находится за пределами \nдопутимого " +
              "диапазона индексов списка: ${list.size} ${list.indices}")
    }
    for (x in 1..5) print("$x ") // итерация по диапазону
    println()
    for (x in 1..10 step 2) print("$x ") // итерация с шагом
    println()
    // обратная геометрическая прогрессия
    for (x in 100 downTo 0 step 5) print("$x ")
    println()
    //не включать 5
    for (x in 0 until 5) print("$x ")
    println()
    val map = mapOf(1 to "One", 2 to "Two", 3 to "Tree")
    for ((k, v) in map) { // перебор отображения
      println("k: $k v: $v")
    }
    map.forEach { k, v -> println("k: $k v: $v") } // перебор отображения
    println(map[1]) // доступ к элементу отображения по ключу
    println("----------------------")
  }
}

class Ca(val i: Int)
