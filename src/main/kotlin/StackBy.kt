import classesAndObjects.User2
import kotlin.reflect.KProperty

object StackBy1 {
  var s: String by Delegate5()

  @JvmStatic
  fun main(args: Array<String>) {
    println(s)
    s = "textB"
    println("------------------")
    val class1 = DerivedAB1(BaseClassAB())
    println(class1.value)
    class1.f()
    println("------------------")
    val user = UserB(mapOf("name" to "John", "age" to 30),
            mutableMapOf("address" to "city, street", "id" to 5000L))
    println("name: ${user.name}; age: ${user.age}; " +
            "address: ${user.address}; id: ${user.id}")
  }
}

class Delegate5 {
  // соответствует методу get(). thisRef - ссылка на объект из которого
  // мы считываем свойство p, т.е. объект класса DelegationProperty()
  operator fun getValue(ref: Any?, prop: KProperty<*>) = "textA" //{
    //return "thisRef = $thisRef \nproperty.name = ${property.name}"
    //return "text1"
  //}
  // соответствует методу set(). Первые два параметра такие же как и
  // у метода выше, а третий параметр value хранит присваиваемое значение
  operator fun setValue(ref: Any?, prop: KProperty<*>, v: String) {
      println("value = $v")
  }
}

interface BaseInterfaceA {
  val value: String
  fun f()
}

class BaseClassAB: BaseInterfaceA {
  override val value = "property ClassA"
  override fun f() { println("fun ClassA") }
}

class DerivedAB1(baseClassAB: BaseInterfaceA): BaseInterfaceA by baseClassAB {

}

class UserB(mapA: Map<String, Any?>, mapB: MutableMap<String, Any?>) {
  val name: String by mapA
  val age: Int by mapA
  var address: String by mapB
  var id: Long by mapB
}