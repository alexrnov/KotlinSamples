package other

object DestructuringDeclarationsSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    sample()
    returningTwoValuesFromFunction()
    //val person = Person2("jon", 15, true)

    val person = Person2("Adam").apply {
      age = 30 // тоже самое, что и this.age = 30
      code = true
    }
    println("person = ${person.age} + ${person.code} + ${person.name}")
    person.run { name = "Name2"; age = 40; code = false }
    println("person = ${person.age} + ${person.code} + ${person.name}")
  }

  private fun sample() {
    println("sample()")
    val person = Person("jon", 15, true)
    val (name, age) = person
    println("name = $name, age = $age")
    // инструкция val (name, age) = person компилируется в следующий код
    // val name = person.component1()
    // val age = person.component2()

    // деструктурирование работает и для циклов
    println("-")
    val list = listOf(Person("jon", 15, true), Person("denis", 33, true),
            Person("jim", 45, false))
    for ((name2, age2) in list) { println("name2 = $name2, age2 = $age2") }
    println("----------------------")


  }

  private fun returningTwoValuesFromFunction() {
    fun f1(): Person {
      return Person("alice", 10, true)
    }
    fun f2(): Triple<String, Int, Boolean> { // то же самое, что и выше
      return Triple("joy", 10, true)
    }
    println("returningTwoValuesFromFunction()")
    val (name1, age1, code1) = f1()
    println("name1 = $name1, age1 = $age1, code1 = $code1")
    val (name2, age2, code2) = f2()
    println("name2 = $name2, age2 =$age2, code2 = $code2")
    println("----------------------")
  }
}

data class Person(val name: String, var age: Int, var code: Boolean)

class Person2(var name: String, var age: Int = 0, var code: Boolean = false) {


}