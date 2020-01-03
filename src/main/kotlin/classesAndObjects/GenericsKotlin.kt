package classesAndObjects

import java.util.ArrayList

/**
 * Обобщения в Kotlin. Аналогичный код на Java расположен в классе
 * GenericsJava.java
 */
object GenericsKotlinSamples {

  @JvmStatic
  fun main(args: Array<String>) {

    val pair1 = PairKotlin("a", "b")
    println(pair1.first + ", " + pair1.second)
    val pair2 = PairKotlin(1, 2)
    println(pair2.first.toString() + ", " + pair2.second)

    val s = NormalClassKotlin.f1("one", "two", "three", "four", "five")
    println("s = " + s!!)
    val i = NormalClassKotlin.f1(1, 2, 3, 4, 5)
    println("i = " + i!!)
    val classA1 = ClassAKotlin()
    val classA2 = ClassAKotlin()
    val classB1 = ClassBKotlin()
    val classB2 = ClassBKotlin()
    val classC1 = ClassCKotlin()
    val classC2 = ClassCKotlin()
    val a = arrayOf(classA1, classA2)
    val b = arrayOf(classB2, classB2)
    NormalClassKotlin.f2(a)
    println("---------------------")
    NormalClassKotlin.f3(b)
    // не даст скомпилировать, поскольку ограичению нужна
    // реализация интерфейса B2
    //NormalClass.classesAndObjects.f2(a);
    val list = ArrayList<ClassAKotlin>()
    // передача объектов как массива аргументов обобщенных типов
    NormalClassKotlin.addAll(list, classA1, classA2, classB1, classB2)
    val pairA = PairKotlin(classA1, classA2)
    val pairB = PairKotlin(classB1, classB2)
    val pairC = PairKotlin(classC1, classC2)
    //передать методу f4() можно только объект PairGeneric<ClassA>
    NormalClassKotlin.f4(pairA)
    // передать методу f4() объект типа PairGeneric<ClassB> нельзя, поскольку
    // обобщенный тип PairGeneric<ClassB> не связан отношениями наследования
    // с классом PairGeneric<ClassA>
    //NormalClass.f4(pairB);
    // Методу f5() можно передать как объект обобщенного типа PairGeneric<ClassA>,
    // так и объект обобщенного класса PairGeneric<ClassB>, поскольку в методе
    // используется механизм подстановочных типов, при котором
    // и PairGeneric<ClassA>, и PairGeneric<ClassB> относятся к типу PairGeneric<? extends ClassA>
    println("-----------------------------")
    NormalClassKotlin.f5(pairA)
    NormalClassKotlin.f5(pairB)
    println("-----------------------------")
    NormalClassKotlin.f6(pairA)
    NormalClassKotlin.f6(pairB)
    // запись объекта класса pairC не разрешена, поскольку pairC не
    // является супертипом pairB
    //NormalClass.f6(pairC);
    // Меняет местами элементы пар.
    // При этом используется механизм подстановки
    NormalClassKotlin.swap(pairA)
    NormalClassKotlin.swap(pairB)
    NormalClassKotlin.swap(pairC)
  }

}

/** простой обобщенный класс  */
internal class PairKotlin<T> {
  var first: T? = null
  var second: T? = null

  constructor() {
    first = null
    second = null
  }

  constructor(first: T, second: T) {
    this.first = first
    this.second = second
  }
}

/**
 * Обычный класс, в котором есть метод с параметрами типа.
 * Обобщенные методы могут использоваться как в обычных,
 * так и в обобщенных классах.
 */
internal object NormalClassKotlin {
  /**
   * Аннотация позваляет подавить предупреждение при вызове метода
   * с аргументами обобщенных типов (Java 7). Также можно использовать
   * аннотацию @SuppressWarning. Предупреждение вызвано тем, что
   * в качестве массива аргументов elements могут быть переданы
   * обобщенные типы, например коллекции, при этом массивы
   * обобщенных типов создавать нельзя, но в случае с аргументами
   * обобщенных типов это правило было ослаблено.
   */
  @SafeVarargs
  fun <T> addAll(collection: MutableCollection<T>, vararg elements: T) {
    for (element in elements) {
      collection.add(element)
    }
  }

  // параметризованный метод - возвращает первый аргумент,
  // переданный в списке входных параметров
  fun <T> f1(vararg p: T): T? {
    return p[0]
  }

  // ОГРАНИЧЕНИЕ НА ПЕРЕМЕННЫЕ ТИПА
  // тип Т должен быть подтипом ограничивающего типа ClassA
  fun <T : ClassAKotlin> f2(p: Array<T>): T {
    p[0].method1()
    p[1].method2()
    return p[0]
  }

  // ОГРАНИЧЕНИЕ НА ПЕРЕМЕННЫЕ ТИПА
  // тип Т должен быть подтипом ограничивающего типа ClassA и должен
  // реализовать интерфейсы IA и IB. Как и в механизме наследования
  // только один ограничивающий тип может быть кассом, а подтипов
  // интерфейсов может быть сколько угодно. При этом класс должен
  // быть первым в списке ограничений, дальше уже идут интерфейсы.
  // Ограничивающие типы разделяются знаком &. Ради эффективности
  // в конце списка ограничений следует размещать отмечающие
  // интерфейсы (т. е. интерфейсы без методов).
  fun <T : ClassAKotlin> f3(p: Array<T>): T where T : IAKotlin, T: IBKotlin {
    p[0].method1()
    p[0].method2()
    p[0].method3()
    return p[0]
  }

  /**
   * Передять этому методу можно отлько объект типа PairGeneric<ClassA>, но не
   * объект типа PairGeneric<ClassB>, поскольку PairGeneric<ClassA> не является
   * суперклассом для класса PairGeneric<ClassB>
   * @param pair
  </ClassB></ClassA></ClassB></ClassA> */
  fun f4(pair: PairKotlin<ClassAKotlin>) {
    val classA = pair.first
  }

  /**
   * ПОДСТАНОВОЧНЫЕ ТИПЫ
   * Можно передать как объект обобщенного типа PairGeneric<ClassA>,
   * так и объект обобщенного класса PairGeneric<ClassB>, поскольку в методе
   * используется механизм подстановочных типов, при котором
   * и PairGeneric<ClassA>, и PairGeneric<ClassB> относятся к типу PairGeneric
   * @param pair
  </ClassB></ClassA></ClassB></ClassA> */
  fun f5(pair: PairKotlin<out ClassAKotlin>) {
    val a = pair.first
    println("f5() = ")
    // если в качестве параметра передан объект PairGeneric<ClassB> -
    // будут вызваны методы класса ClassB
    a!!.method1()
    a.method2()
    val a2 = ClassAKotlin()
    val b2 = ClassBKotlin()

    // Нельзя вызвать метод setFirst() ни с аргументом a2, ни с аргументом
    // b2, иначе он бы привел к ошибке соблюдения типов.
    // компилятору требуется какой-либо подтип ClassA, но неизвесто
    // какой именно. Он отказывается передать любой конкретный тип,
    // , так как знак подстановки ? в методе setFirst(? extends ClassA),
    // может и не совпасть с этим типом. В этом, собственно, и состоит
    // главный смысл ограниченных подстановок. С их помощью можно
    // теперь различать безопасные методы доступа от небезопасных
    // модифицирующих методов.
    // pair.setFirst(a2);
    // pair.setFirst(b2);
  }

  /**
   * ОГРАНИЧЕНИЕ СУПЕРТИПА НА ПОДСТАНОВКИ
   * Эти подстановки подобны ограничениям на переменные типа
   * (методы classesAndObjects.f2() и classesAndObjects.f3()), но позволяет ограничиться всеми супертипами
   * ClassB (т.е. методу можно передать объект класса ClassA и ClassB,
   * но не класс ClassC). Подстановка с ограничением супертипа дает
   * поведение, обратное подстановочным типам (метод f5()). В частности,
   * методам можно передавать параметры, но нельзя использовать
   * возвращаемые ими значения
   * @param pair
   */
  fun f6(pair: PairKotlin<in ClassBKotlin>) {
    // classesAndObjects.ClassAKotlin classA = pair.getFirst(); // методы доступа не разрешены
    val classB = ClassBKotlin() // модифицирующие методы разрешены
    pair.first = classB
    val classC = ClassCKotlin()
    pair.first = classC
    // модифицирующий метод для суперкласса (только ClassA)
    // не разрешен. Не понял почему.
    val classA = ClassAKotlin()
    //pair.setFirst(classA);
  }

  /**
   * НЕОГРАНИЧЕННЫЕ ПОДСТАНОВКИ. Неограниченный тип удобен
   * для выполнения очень простых операций. Например метод проверяет,
   * содержит ли пара пустую ссылку на объект. Такому методу вообще
   * не требуется конкретный тип.
   * @param pair
   * @return
   */
  fun f7(pair: PairKotlin<*>): Boolean {
    return pair.first == null || pair.second == null
  }

  /** метод демонстрирует механизм захвата подстановки  */
  fun swap(pair: PairKotlin<*>) { // необобщенный метод
    swapHelper(pair) // захврат подстановки
  }

  /** меняет местами элементы пары  */
  private fun <T> swapHelper(p: PairKotlin<T>) { // обобщенный метод
    val t = p.first
    p.first = p.second
    p.second = t
  }
}

internal open class ClassAKotlin : IAKotlin {
  override fun method1() {
    println("classA method1")
  }

  override fun method2() {
    println("classA method2")
  }
}

internal open class ClassBKotlin : ClassAKotlin(), IAKotlin, IBKotlin {
  override fun method1() {
    println("classB method1")
  }

  override fun method2() {
    println("classB method2")
  }

  override fun method3() {
    println("classB method3")
  }
}

internal class ClassCKotlin : ClassBKotlin() {
  fun method4() {
    println("ClassC method1")
  }
}

internal interface IAKotlin {
  fun method1()
  fun method2()
}

internal interface IBKotlin {
  fun method3()
}


