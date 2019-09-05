package classesAndObjects

object PropertiesAndFieldsSamples {

  // константы могут распологаться в объекте (object)
  // констанами могут быь только примитивы или String
  const val const1: String = ""

  @JvmStatic
  fun main(args: Array<String>) {
    val properties = Properties()
    println("доступ к параметрам:")
    properties.s1 = "change s1"
    println("properties.s1 = ${properties.s1}")
    println("properties.s2 = ${properties.s2}")
    println("properties.s3 = ${properties.s3}")
    println("------------------------------------")
    println("геттеры и сеттеры: ")
    println("properties.s4 = ${properties.s4}")
    val class1 = DataClassP("a", "b")
    properties.s4 = class1
    println("properties.s4 = ${properties.s4}")
    println("------------------------------------")
    println("Тип вывоится из геттера: ")
    println(if (properties.s5) "s1 is empty" else "s1 is not empty")
    println("------------------------------------")
    println("Закрытый сеттер: ")
    println("properties.s6 = ${properties.s6}")
    // properties.s6 = 9 // закрытый сеттер, - нельзя модифицировать извне
    println("------------------------------------")
    println("Настраиваемый сеттер (backing field): ")
    println("properties.s7 = ${properties.s7}") // значение при инициализации
    properties.s7 = 1
    // вернет 1, т. к. для положительных чисел сеттер не меняет значения
    println("properties.s7 = ${properties.s7}")
    properties.s7 = -8
    // вернет -16 (-8 * 2), т. к. для отрицательных чисел сеттер удваивает значения
    println("properties.s7 = ${properties.s7}")
    println(if (properties.s7isEmpty) "s7 == 0" else "s7 != 0")
    println("------------------------------------")
    println("Свойство поддержки: ")
    println("properties.s8 = ${properties.s8}")
    properties.s8.put("a", 1)
    properties.s8.put("b", 2)
    println("properties.s8 = ${properties.s8}")
    println("------------------------------------")
    println("Отложенная инициализация:")
    properties.late()
    println("------------------------------------")
    println("Константы:")
    properties.printConstant()
    println("------------------------------------")
    println("Класс в файле: ")
    val classFromFile1 = ClassFromFile1()
    classFromFile1.f()
    println("------------------------------------")
    println("Свойство без backing fields:")
    println("valueBF = " + properties.valueBF1)
    properties.valueBF1 = 2
    println("valueBF = " + properties.valueBF1)
  }
}

const val const2: String ="const1"

class Properties {
  var s1: String = "s1" // свойство var имеет get/set по умолчанию
  val s2: String = "s2" // свойство val имеет get по умолчанию
  val s3: String get() = "s3 from get()" // подходит только для val, но не для var
  var s4: DataClassP = DataClassP("name", "lastName")
    get() { // настраиваемый геттер
      println("s4: getter")
      return field
    }
    set(value) { // настраиваемый сеттер
      println("s4: setter")
      field = value
    }
  val s5 get() = s1.isEmpty() // тип выводится из геттера (Boolean)
  var s6: Int = 5
    private set // закрытый сеттер

  var s7 = -1
          // настраиваемый сеттер: для нулевых и положительных чисел
          // установить переданное для записи значение, а для отрицательных
          // чисел установить удвоенное, переданное значение
    set(value) {
       field = if (value >= 0) value else value * 2
    }

  val s7isEmpty: Boolean get() = s7 == 0 // тип выводится из геттера

  // свойство поддержки, тоже, что в Java - доступ к закрытому полю
  private var _s8: MutableMap<String, Int>? = null
  val s8: MutableMap<String, Int>
    get() {
      if (_s8 == null) {
        _s8 = HashMap()
      }
      return _s8 ?: throw AssertionError("Set to null by another thread")
    }

  // отложенная инициализация. Тип должен быть var, ненулевым,
  // и не должен быть примитивным типом
  private lateinit var s11: DataClassP // отложенная инициализация

  fun late() {
    // проверить - инициализировано ли свойство
    println(if(this::s11.isInitialized) "s11 init" else "s11 not init")
    // инициализировать свойство
    s11 = DataClassP("name 1", "last name 1")
    // проверить еще раз
    println(if(this::s11.isInitialized) "s11 init" else "s11 not init")
    // обратиться к свойству
    println("s11.name = ${s11.name} s11.lastName = ${s11.lastName}")
  }

  companion object {
    //константа может быть объявлена в объекте компаньене
    const val const3: String = "const2"
  }

  fun printConstant() {
    println(PropertiesAndFieldsSamples.const1) // константа объекта
    println(const2) // константа файла
    println(const3) // константа компаньона
  }

  var valueBF1 = 0
    // вызов сеттера без использования field, т.е. это свойство без backing field
    set (value) {
      invokeFunInsteadAssignment(value)
    }

  @PublishedApi
  internal fun invokeFunInsteadAssignment(v: Int) {
    println("Вызов функции вместо присваивания значения: $v")
  }

  var valueBF2: Int
    get() {
      return 7
    }
    inline set (value) {
      invokeFunInsteadAssignment(5)
    }

}

data class DataClassP(var name: String, var lastName: String) {}
