package other

object EqualitySamples {

  @JvmStatic
  fun main(args: Array<String>) {
    val e1 = ClassE("a", "b")
    val e2 = ClassE("a", "c")
    if (e1.equals(e2)) println("Объекты равны")
    // переопределение метода equals влияет и на оператор ==
    if (e1 == e2) println("Объекты равны")
    if (e1.hashCode() == e2.hashCode()) {
      println("хеш-коды равны")
    }
    // проверка ссылок на равенство
    if (e1 !== e2) { println("Объекты ссылаются на разные адреса в памяти") }
    val e3 = e2
    if (e3 === e2) { println("Объекты ссылаются на один адрес памяти") }
  }
}

class ClassE(val v1: String, val v2: String) {
  // переопределение метода equals
  override fun equals(other: Any?): Boolean {
    if (other !is ClassE) return false
    return this.v1 == other.v1
  }

  // переопределение метода hashCode()
  override fun hashCode(): Int {
    return v1.hashCode()
  }
}