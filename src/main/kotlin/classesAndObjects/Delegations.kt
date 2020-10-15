package classesAndObjects

import java.lang.reflect.Type
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty




val lazyValue: String by lazy {
  println("lazyValue")
  "text"
}

interface BaseInterface {
  val value: String
  fun print1()
  fun print2()
}

class BaseClass1(private val x: Int): BaseInterface {
  override val value = "v"
  override fun print1() { println("print1(): x = $x, value = $value") }
  override fun print2() { println("print2(): BaseClass1, value = $value") }
}

class BaseClass2(private val x: Int): BaseInterface {
  override val value = "v"
  override fun print1() { println("print1(): x = $x, value = $value")}
  override fun print2() { println("print2(): BaseClass2, value = $value") }
}

/**
 * Класс Derived может реализовать интерфейс BaseInterface путем
 * делегирования всех public-членов из указанного объекта baseClass:
 * baseClass будет храниться внутри объекта Derived, и метод
 * интерфейса будет вызываться как baseClass.print()
 */
class Derived1(baseClass: BaseInterface): BaseInterface by baseClass

class Derived2(baseClass: BaseInterface): BaseInterface by baseClass {
  override val value = "change v"
  override fun print2() { // переопределение делегированного метода
    println("print2(): Derived, value = $value")
  }
  // при этом, вызов print1() возьмет значение value из делегированного
  // класса, т.е. просто значение v
}

class DelegateProperty {
  var p: String by Delegate() // делегируемое свойство
}

class Delegate {
  // соответствует методу get(). thisRef - ссылка на объект из которого
  // мы считываем свойство p, т.е. объект класса DelegationProperty()
  operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
    return "thisRef = $thisRef \nproperty.name = ${property.name}"
  }
  // соответствует методу set(). Первые два параметра такие же как и
  // у метода выше, а третий параметр value хранит присваиваемое значение
  operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
    println("value = $value")
  }
}

// делегирование для слушателей
class User {
  /**
   * Delegates.observable () принимает два аргумента: начальное значение
   * и обработчик изменений. Обработчик вызывается каждый раз при
   * присваивании свойства (после выполнения присваивания).
   */
  var name: String by Delegates.observable("init value") {
    prop, old, new -> println("old = $old, new = $new")
  }

  /**
   * Обработчик, переданный в vetoable, вызывается до того, как было
   * выполнено присвоение нового значения свойству.
   */
  var name2: String by Delegates.vetoable("init value") {
    prop, oldValue, newValue ->
      // перехватить значение и наложить вето на пустое значение
      if (newValue.isEmpty()) {
        println("newValue.isEmpty()")
        false // значение не будет сохранено (накладывается вето)
      }
      else {
        println("newValue.isNotEmpty()")
        true // непустое значение будет сохранено
      }
  }
}

// делегирование для хранения свойств в карте
class User2(val map1: Map<String, Any?>, val map2: MutableMap<String, Any?>) {
  val name: String by map1
  val age: Int by map1
  var address: String by map2
  var account: Long by map2
}

// можно объявлять локальные переменные как делегируемые свойства
fun f1(f: () -> ClassForLazy) {
  val m by lazy(f) // свойство будет вычислено при первом доступе
  if (m.isValid()) { // если условие false, lazy-свойство не будет вычислено
    m.print()
  }
}

val lambda1: () -> ClassForLazy = {
  ClassForLazy(5)
}

val lambda2: () -> ClassForLazy = {
  ClassForLazy(-5)
}

class ClassForLazy(private var r: Int) {
  private var b: Boolean = false

  init {
    check()
  }

  private fun check() {
    b = r > 0
  }

  fun isValid(): Boolean {
    return b
  }

  fun print() = println("b = $b")
}

// реализация стандартного интерфейса Kotlin для свойств
// только для чтения (val)
class Delegate2<in R, T>(var t: T) : ReadOnlyProperty<R, T> {
  override fun getValue(thisRef: R, property: KProperty<*>): T {
    println("Delegate2: getValue()")
    return t
  }
}

// реализация стандартного интерфейса Kotlin для мутабельных
// свойств (var)
class Delegate3<in R, T>(var t: T) : ReadWriteProperty<R, T> {
  override fun getValue(thisRef: R, property: KProperty<*>): T {
    println("Delegate3: getValue()")
    return t
  }

  override fun setValue(thisRef: R, property: KProperty<*>, value: T) {
    println("Delegate3: setValue()")
    t = value
  }
}

class DelegateProperty2 {
  val v1: Int by Delegate2(5) // можно подставлять различные типы
  var v2: String by Delegate3("parameter s")
}

/*
class ClassDelegate {
  var p: String by Delegate()
}
// вместо кода выше, компилятор генерирует такой код:
class ClassDelegate {
  private val p$delegate = Delegate()
  var p: String
    get() = p$delegate.getValue(this, this::p)
    set(value: Type) = p$delegate.setValue(this, this::p, value)
}
*/

class ResourceDelegate<out T>(val t: T) : ReadOnlyProperty<MyUI, T> {
  override fun getValue(thisRef: MyUI, property: KProperty<*>): T {
    println("getValue: property.name = ${property.name}")
    return t
  }
}

class ResourceLoader<T>(val id: ResourceID<T>) {
  operator fun provideDelegate(
          thisRef: MyUI,
          prop: KProperty<*>
  ): ReadOnlyProperty<MyUI, T> {
    checkProperty(thisRef, prop.name)
    // create delegate
    return ResourceDelegate(id.t)
  }

  // Проверка свойств будет осуществляться раньше, чем метод
  // getValue(). Например, здесь проверяется имя свойства перед
  // привязкой. Т.е. метод provideDelegate() будет вызван раньше метода
  // getValue()
  private fun checkProperty(thisRef: MyUI, name: String) {
    println("checkProperty: name = $name")
  }
}

class MyUI {
  fun <T> bindResource(id: ResourceID<T>): ResourceLoader<T> {
    return ResourceLoader(id)
  }
  val image by bindResource(ResourceID("image"))
  val text by bindResource(ResourceID("text"))
}

class ResourceID<T>(val t: T)


object DelegationsSamples {

  @JvmStatic fun main(args: Array<String>) {
    /**
     * Паттерн делегирования доказал, что является хорошей альтернативой
     * наследованию реализации, и Kotlin поддерживает его, изначально не
     * требуя написания специального кода.
     */
    println("Делегирование класса: ")
    val baseClass1 = BaseClass1(10)
    val d1 = Derived1(baseClass1)
    d1.print1()
    d1.print2()
    println("value = " + d1.value)
    println("------------------------------------")
    println("Переопределение метода из делегированного класса: ")
    val baseClass2 = BaseClass2(10)
    val d2 = Derived2(baseClass2)
    d2.print1()
    d2.print2()
    println("value = " + d2.value)
    println("------------------------------------")
    /**
     * Есть определенные общие виды свойств, которые, хотя мы
     * можем реализовать их вручную каждый раз, когда нам они нужны,
     * было бы очень приятно реализовать раз и навсегда, и поместить в
     * библиотеку (например, ленивые свойства: значение вычисляется
     * только при первом доступе; наблюдаемые свойства: прослушиватели
     * получают уведомления об изменениях в этом свойстве; сохранение
     * свойств в отображении вместо отдельного поля для каждого свойства.
     */
    println("Делегируемое свойство: ")
    val properties = DelegateProperty()
    println(properties.p)
    println("-")
    properties.p = "10"
    println(properties.p)
    println("------------------------------------")
    /**
     * Стандартная библиотека Kotlin обеспечивает фабрику методов для
     * нескольких полезных видов делегирования, представленных ниже
     */
    // первый вызов метода get() в lazy-свойстве, передает лямда-выражение
    // на выполнение и запоминает результат, последующие вызовы
    // свойства просто возвращают запомненный результат. Лямбда свойства
    // имеют отложенное выполнение
    println("Lazy-свойства: ")
    val s = lazyValue
    println("s = $s")
    println("------------------------------------")
    println("Наблюдатели: ")
    val user = User()
    user.name = "first" // observable
    user.name = "second"
    println("-")
    user.name2 = "text1" // vetoable
    println(user.name2) // значение станет text1
    user.name2 = "" // пустое значение не будет присвоено
    println(user.name2) // значение останется text1
    println("------------------------------------")
    println("Хранение свойств в карте: ")
    // Для свойств val используется Map, для var - MutableMap
    val user2 = User2(mapOf("name" to "John", "age" to 30),
            mutableMapOf("address" to "city, street, house", "account" to 5000L))
    println("user2.name = ${user2.name}")
    println("user2.age = ${user2.age}")
    println("user2.address = ${user2.address}")
    println("user2.account = ${user2.account}")
    println("------------------------------------")
    println("Локальные делегируемые свойства: ")
    f1(lambda1)
    println("-")
    f1(lambda2)
    println("------------------------------------")
    println("Реализация стандартного интерфейса Kotlin с обобщенными типами: ")
    val properties2 = DelegateProperty2()
    println(properties2.v1)
    println("-")
    println(properties2.v2)
    println("-")
    properties2.v2 = "change parameter"
    println("-")
    println(properties2.v2)
    println("------------------------------------")
    /**
     * Метод provideDelegate() используется для того, чтобы перехватить
     * значение до того, как оно было вызвано в методе getValue(), т.е.
     * перед связыванием имени свойства и его значением
     */
    println("provideDelegate(): ")
    val myUI = MyUI()
    myUI.image
    myUI.text
  }
}

