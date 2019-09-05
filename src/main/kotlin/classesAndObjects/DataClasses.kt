package classesAndObjects

object DataClassesSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    println("Значения устанавливаются по умолчанию: ")
    var dataClass1 = DataClass1()
    println("dataClass1.v1 = ${dataClass1.v1}")
    println("dataClass1.v2 = ${dataClass1.v2}")
    println("-------------------------------")
    println("Значения задаются явно: ")
    dataClass1 = DataClass1(3, 4)
    println("dataClass1.v1 = ${dataClass1.v1}")
    println("dataClass1.v2 = ${dataClass1.v2}")
    println("-------------------------------")
    println("В toString() нет параметра v3, поскольку он не объявлен " +
            "в конструкторе: ")
    println(dataClass1.toString())
    println("dataClass1.v3 = ${dataClass1.v3}")
    println("-------------------------------")
    val otherDataClass1 = DataClass1(3, 4)
    otherDataClass1.v3 = 10
    if (dataClass1 == otherDataClass1) {
      println("Классы все равно равны, поскольку \nпараметр v3 " +
              "не объявлен в конструкторе.")
    }
    if (dataClass1.hashCode() == otherDataClass1.hashCode()) {
      println("По этой же причине хеш-коды совпадают")
    }
    val otherDataClass1Copy = otherDataClass1.copy()
    // параметр v3 будет 3 а не 10
    println("v1 = " + otherDataClass1Copy.v1 + ", v2 = "
            + otherDataClass1Copy.v2 + ", v3 = " + otherDataClass1Copy.v3)
    // будет только два метода компонент
    otherDataClass1Copy.component1()
    otherDataClass1Copy.component2()
    println("-------------------------------")
    println("Полное копирование: ")
    // изменение в классе otherDataClass1Copy не приведут к изменениям
    // в классе otherDataClass1 (полное копирование)
    otherDataClass1Copy.v1 = 20
    otherDataClass1Copy.v2 = 40
    println(otherDataClass1.toString())
    println(otherDataClass1Copy.toString())
    println("-------------------------------")
    println("Копирование определенных параметров: ")
    // изменить при копировании один из параметров (v2)
    val otherDataClass1Copy2 = otherDataClass1Copy.copy(v2 = 50)
    println(otherDataClass1Copy.toString())
    println(otherDataClass1Copy2.toString())
    println("-------------------------------")
    println("Множественное присваивание из объекта данных: ")
    var (p1, p2) = otherDataClass1Copy2
    println("p1 = $p1, p2 = $p2")
    p1 = 10  // изменить параметры
    p2 = 10
    println("p1 = $p1, p2 = $p2")
    // при этом параметры объекта не меняются
    println(otherDataClass1Copy2)
  }
}

/**
 * Класс данных имеет конструкторы по умолчанию, что позволяет
 * создавать объекты классов данных с пустым конструктором
 */
data class DataClass1(var v1: Int = 1, var v2: Int = 2) {
  // свойство, не объявленное в конструкторе исключается
  // из сгенерированных реализаций(toString(), hashCode(), equals(), сopy()).
  // И будет только два метода component1() и component2()
  var v3: Int = 3
}
