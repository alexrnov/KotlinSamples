package other

/*
 * Стандартная библиотека Kotlin содержит несколько функций, единственной
 * целью которых является выполнение блока кода в контексте объекта. При
 * вызове такой функции для объекта с предоставленным лямбда-выражением
 * она образует временную область действия. В этой области можно получить
 * доступ к объекту без его имени. Такие функции называются функциями
 * области действия. Их пять: let, run, with, apply, and also.
 */
object ScopeFunctionsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    sample()
  }

  private fun sample() {
    println("sample()")
    // типичный пример функции области действия
    ScopeClass("Alice", 20, "Amsterdam").let {
      println(it)
      it.moveTo("London")
      it.incrementAge()
      println(it)
    }
    println("-")
    // Если написать то же самое без let, придется вводить новую
    // переменную и повторять ее имя при каждом использовании.
    val alice = ScopeClass("Alice", 20, "Amsterdam")
    println(alice)
    alice.moveTo("London")
    alice.incrementAge()
    println(alice)
    println("-------------------------")
  }
}

data class ScopeClass(val name: String, var age: Int, var city: String) {
  fun moveTo(city: String) {
    this.city = city
  }

  fun incrementAge() {
    age++
  }
}