package classesAndObjects

const val V: String = "v"

// Файл котлина - может содержать несколько классов
class ClassFromFile1 {
  fun f() {
    println("ClassFromFile1: w()")
    //доступ к геттеру есть, но к сеттеру нет поскольку он объявлен
    //как private
    println("доступ к свойству верхнего уровня из другого класса = $v1")
  }
}

class ClassFromFile2 {
  fun f() {
    println("ClassFromFile2")
  }
}