package functionsAndLambdas

/**
 *  Использование функций более высокого порядка налагает
 *  определенные штрафы при выполнении: каждая функция является
 *  объектом, и она захватывает замыкания, то есть те переменные,
 *  которые доступны в теле функции. Выделение памяти
 *  (как для функциональных объектов, так и для классов) и виртуальные
 *  вызовы создают накладные расходы (overhead) во время выполнения.
 *  Но, судя по всему, во многих случаях такого рода накладные расходы
 *  могут быть устранены путём встраивания лямбда-выражений.
 *  То есть функция блокировки lock() может быть легко внедрена
 *  в местах вызова. Рассмотрим следующий случай: lock(l) { foo() }
 *  Вместо создания объекта функции для параметра и генерации вызова
 *  компилятор мог бы эмулировать следующий код:
 *  l.lock()
 *  try {
 *    foo()
 *  }
 *  finally {
 *    l.unlock()
 *  }
 * Чтобы компилятор сделал это, необходимо пометить функцию lock ()
 * с помощью встроенного модификатора:
 * inline fun <T> lock(lock: Lock, body: () -> T): T { ... }
 * Встроенный модификатор inline влияет как на саму функцию, так и на
 * переданные ей лямбды: все они будут встроены в сайт вызова.
 * Встраивание может привести к росту генерируемого кода, однако,
 * если мы сделаем это разумным способом (то есть избегать встраивания
 * больших функций), это окупится производительностью, особенно
 * на "мегаморфных" узлах вызовов внутри циклов. Начиная с Kotlin 1.3
 * inline модификатор также можно использовать перед словом class,
 * для обозначения того, что класс является оберткой. Такой класс может
 * содержать одно свойство в конструкторе. Inline-классы являются
 * экспериментальными, и их совместимость с более ранними версиями
 * не гарантирована.
 */
object InlineFunctionsSamples {

  private val s1: String = "text"

  @JvmStatic
  fun main(args: Array<String>) {
    funInline(s1, { it -> println(it) })
    funNoinline({ it -> println(it) }, { it -> println(it) })
    funCrossinline { println("text") }
    println("Пример использования модификатора reified: ")
    println("Без использования reified: ")
    val list: List<Any> = listOf(1, 2, 3, 4, 5, this)
    // вызов не очень красивый
    list.findObjectThisClass(InlineFunctionsSamples::class.java)
    println("С использованием reified: ")
    // чтобы просто передать функции тип InlineFunctionsSamples,
    // можно записать вызов в такой форме:
    list.findObjectThisClass2<Any, InlineFunctionsSamples>()
    println("------------------------------------")
    println("inline в свойствах: ")
    println("valueBF1 = " + valueBF1)
    println("-")
    println("valueBF2 = " + valueBF2)
    valueBF2 = 10
    println("valueBF2 = " + valueBF2)
    println("-")
    println("valueBF3 = " + valueBF3)
    valueBF3 = 10
    println("valueBF3 = " + valueBF3)
    println("-")
    println("valueBF4.v1 = " + valueBF4.v1)
    b = true
    println("valueBF4.v1 = " + valueBF4.v1)
    println("-")
    // если присвоенное значение, т.е. объект дата-класса,
    // имеет отрицательный параметр, будет возрващен экземпляр
    // с другим значением, но также отрицательным. Аналогично и
    // для присвоенного положительного значения вернется объект с
    // другим положительным параметром.
    val classB = ClassB()
    classB.v = ClassA(-5)
    println(classB.v)
    classB.v = ClassA(5)
    println(classB.v)
  }

  /**
   * Встроенный модификатор inline влияет как на саму функцию, так и на
   * переданные ей лямбды: все они будут встроены в место вызова
   */
  private inline fun funInline(s1: String, f: (String) -> Unit) {
    println("funInline(): ")
    f.invoke(s1)
    println("------------------------------------")
  }

  /**
   * Если нужно, чтобы только некоторые лямбды были помечены
   * как inline, остальные можно пометить модификатором noinline
   */
  private inline fun funNoinline(f1: (String) -> Unit, noinline f2: (String) -> Unit) {
    println("funNoinline(): ")
    f1.invoke("inlineFunction")
    f2.invoke("noinlineFunction")
    println("------------------------------------")
  }
}

/**
 * Некоторые встроенные функции могут вызывать лямбды, переданные им,
 * как параметры не непосредственно из тела функции, а из другого
 * контекста выполнения, например, локального объекта
 * (как в данном примере) или вложенной функции. В таких случаях
 * нелокальный поток управления также не допускается в лямбдах и
 * параметр лямбда должен быть помечен модификатором crossinline:
 */
private inline fun funCrossinline(crossinline f: () -> Unit) {
  println("funCrossinline(): ")
  val f2 = object {
    fun run() = f()
  }
  f2.run()
  println("------------------------------------")
}

// функция расширения сравнивает тип каждого элемента списка (T) с
// типом входного параметра (U)
fun <U, T> List<T>.findObjectThisClass(clazz: Class<U>) {
  for (element in this) { // перебор элементов коллекции
    if (clazz.isInstance(element)) { // используется рефлексия
      println("element: object instance this class")
    } else {
      println("element: $element, object instance other class")
    }
  }
}

// функция решает ту же задачу, что и функция выше, но здесь
// используется модификатор reified (овеществление типа), это
// позволяет вызвать функцию с помощью формы
// list.findObjectThisClass2<Any, InlineFunctionsSamples>(). Т.е. когда
// объект класса нужен только для того, чтобы извлечь тип, такая
// форма позволяет вообще не передавать объект, а просто указать
// необходимый тип. Для использования модификатора reified
// функция обязательно должна быть помечена как inline
inline fun <U, reified T> List<U>.findObjectThisClass2() {
  for (element in this) { // перебор элементов коллекции
    if (element is T) { // использование рефлексии не требуется
      println("element: object instance this class")
    } else {
      println("element: $element, object instance other class")
    }
  }
}

/**
 * Встроенный модификатор можно использовать для методов доступа
 * к свойствам, не имеющим обратных полей (backing fields), т.е. в этих
 * методах нельзя использовать слово field. Получается, что методы
 * не хранят какого-то определенного значения, а производят какие-либо
 * действия.
 */
// геттер не имеет backing field
val valueBF1: Int
  inline get() = 8

// сеттер не имеет backing field
var valueBF2: Int
  get() {
    return 7
  }
  // инлайн обозначает, что сеттер не может иметь backing field, т.е.
  // нельзя использовать слово field
  inline set (value) {
    invokeFunInsteadAssignment(value)
  }

/**
 * Можно также создать inline-аннотацию для всего свойства, которое
 * отмечает оба способа доступа как inline:
 */
inline var valueBF3: Int
  get() {
    return 99
  }
  set(value) {
    invokeFunInsteadAssignment(value)
  }

/**
 * internal функция может быть аннотирована с помощью
 * PublishedApi, что позволяет использовать функцию в public inline
 * функциях API. Когда функция отмечена этой аннотацией, ее тело
 * проверено также, как если бы это была public-функция.
 */
@PublishedApi
internal fun invokeFunInsteadAssignment(v: Int) {
  println("Вызов функции вместо присваивания значения: $v")
}

@PublishedApi
internal var b: Boolean = false

val valueBF4: ClassA
  inline get() {
    return if (b) ClassA(10) else ClassA(5)
  }

data class ClassA(var v1: Int)

class ClassB {
  @PublishedApi
  internal var positive = false
  var v: ClassA
    get() {
      return if (positive) ClassA(10) else ClassA(-10)
    }
    inline set(value) {
      positive = (value.v1 > 0)
    }
}