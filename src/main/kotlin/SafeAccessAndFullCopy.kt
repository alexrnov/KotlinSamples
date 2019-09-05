/**
 * Демонстрация того, как осуществлять безпасный доступ к полям-контейнерам
 */
object FullCopyAndSafeAccess {
  @JvmStatic
  fun main(args: Array<String>) {
    val class1 = Class1()
    class1.printList1()

    class1.list1Output.add("4")
    class1.printList1()
    println("______________________________________")
    class1.printList2()
    println("------------------------------")
    class1.list2Output.first()["1"] = "change element 1"
    class1.printList2()
  }
}

class Class1 {
  private val list1: MutableList<String> = mutableListOf("1", "2", "3")
  // возвращаемый список гарантированно не изменится
  val list1Output: MutableList<String> get() = list1.toMutableList()

  private val list2: MutableList<MutableMap<String, String>> = ArrayList()
  // возвращаемый список гарантированно не изменится
  val list2Output get() = copyListWithSubMap(list2)

  init {
    val map1 = mutableMapOf("1" to "one")
    val map2 = mutableMapOf("2" to "two")
    val map3 = mutableMapOf("3" to "three")
    list2.add(map1)
    list2.add(map2)
    list2.add(map3)
  }

  fun printList1() {
    println(list1.size)
  }

  fun printList2() {
    list2.forEach{ println(it) }
  }
}

/**
 * Полностью копирует список со вложенными коллекциями Map<String, String>
 * [inputList] копируемый список с вложенными отображениями
 * Возвращает полную копию списка [inputList]
 */
fun copyListWithSubMap(inputList: MutableList<MutableMap<String, String>>):
        MutableList<MutableMap<String, String>> {
  val copyList = ArrayList<MutableMap<String, String>>()
  inputList.forEach { e ->
    val copyMap = HashMap(e)
    copyList.add(copyMap)
  }
  return copyList
}

