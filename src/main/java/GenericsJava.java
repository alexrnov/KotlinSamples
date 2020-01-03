import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Обобщения в Java. Аналогичный код на Kotlin расположен в классе
 * GenericsKotlin.kt
 */
public class GenericsJava {

  public static void main(String[] args) {

    PairGeneric<String> pairGeneric1 = new PairGeneric<>("a", "b");
    System.out.println(pairGeneric1.getFirst() + ", " + pairGeneric1.getSecond());
    PairGeneric<Integer> pairGeneric2 = new PairGeneric<>(1, 2);
    System.out.println(pairGeneric2.getFirst() + ", " + pairGeneric2.getSecond());

    String s = NormalClass.f1("one", "two", "three", "four", "five");
    System.out.println("s = " + s);
    Integer i = NormalClass.f1(1, 2, 3, 4, 5);
    System.out.println("i = " + i);
    ClassA classA1 = new ClassA();
    ClassA classA2 = new ClassA();
    ClassB classB1 = new ClassB();
    ClassB classB2 = new ClassB();
    ClassC classC1 = new ClassC();
    ClassC classC2 = new ClassC();
    ClassA[] a = { classA1, classA2};
    ClassB[] b = { classB2, classB2 };
    NormalClass.f2(a);
    System.out.println("---------------------");
    NormalClass.f3(b);
    // не даст скомпилировать, поскольку ограичению нужна
    // реализация интерфейса B2
    //NormalClass.classesAndObjects.f2(a);
    List<ClassA> list = new ArrayList<>();
    // передача объектов как массива аргументов обобщенных типов
    NormalClass.addAll(list, classA1, classA2, classB1, classB2);
    PairGeneric<ClassA> pairGenericA = new PairGeneric(classA1, classA2);
    PairGeneric<ClassB> pairGenericB = new PairGeneric(classB1, classB2);
    PairGeneric<ClassC> pairGenericC = new PairGeneric(classC1, classC2);
    //передать методу f4() можно только объект PairGeneric<ClassA>
    NormalClass.f4(pairGenericA);
    // передать методу f4() объект типа PairGeneric<ClassB> нельзя, поскольку
    // обобщенный тип PairGeneric<ClassB> не связан отношениями наследования
    // с классом PairGeneric<ClassA>
    //NormalClass.f4(pairGenericB);
    // Методу f5() можно передать как объект обобщенного типа PairGeneric<ClassA>,
    // так и объект обобщенного класса PairGeneric<ClassB>, поскольку в методе
    // используется механизм подстановочных типов, при котором
    // и PairGeneric<ClassA>, и PairGeneric<ClassB> относятся к типу PairGeneric<? extends ClassA>
    System.out.println("-----------------------------");
    NormalClass.f5(pairGenericA);
    NormalClass.f5(pairGenericB);
    System.out.println("-----------------------------");
    NormalClass.f6(pairGenericA);
    NormalClass.f6(pairGenericB);
    // запись объекта класса pairGenericC не разрешена, поскольку pairGenericC не
    // является супертипом pairGenericB
    //NormalClass.f6(pairGenericC);
    // Меняет местами элементы пар.
    // При этом используется механизм подстановки
    NormalClass.swap(pairGenericA);
    NormalClass.swap(pairGenericB);
    NormalClass.swap(pairGenericC);
  }

}

/** простой обобщенный класс */
class PairGeneric<T> {
  private T first;
  private T second;

  public PairGeneric() {
    first = null;
    second = null;
  }

  public PairGeneric(T first, T second) {
    this.first = first;
    this.second = second;
  }

  public T getFirst() {
    return first;
  }

  public T getSecond() {
    return second;
  }

  public void setFirst(T newValue) {
    first = newValue;
  }

  public void setSecond(T newValue) {
    second = newValue;
  }
}

/**
 * Обычный класс, в котором есть метод с параметрами типа.
 * Обобщенные методы могут использоваться как в обычных,
 * так и в обобщенных классах
 */
class NormalClass {
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
  public static <T> void addAll(Collection<T> collection, T... elements) {
    for (T element: elements) {
      collection.add(element);
    }
  }

  // параметризованный метод - возвращает первый аргумент,
  // переданный в списке входных параметров
  public static <T> T f1(T... p) {
    return p[0];
  }

  // ОГРАНИЧЕНИЕ НА ПЕРЕМЕННЫЕ ТИПА
  // тип Т должен быть подтипом ограничивающего типа ClassA
  public static <T extends ClassA> T f2(T[] p) {
    p[0].method1();
    p[1].method2();
    return p[0];
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
  public static <T extends ClassA & IA & IB> T f3(T[] p) {
    p[0].method1();
    p[0].method2();
    p[0].method3();
    return p[0];
  }

  /**
   * Передять этому методу можно отлько объект типа PairGeneric<ClassA>, но не
   * объект типа PairGeneric<ClassB>, поскольку PairGeneric<ClassA> не является
   * суперклассом для класса PairGeneric<ClassB>
   * @param pairGeneric
   */
  public static void f4(PairGeneric<ClassA> pairGeneric) {
    ClassA classA = pairGeneric.getFirst();
  }

  /**
   * ПОДСТАНОВОЧНЫЕ ТИПЫ
   * Можно передать как объект обобщенного типа PairGeneric<ClassA>,
   * так и объект обобщенного класса PairGeneric<ClassB>, поскольку в методе
   * используется механизм подстановочных типов, при котором
   * и PairGeneric<ClassA>, и PairGeneric<ClassB> относятся к типу PairGeneric<? extends ClassA>
   * @param pairGeneric
   */
  public static void f5(PairGeneric<? extends ClassA> pairGeneric) {
    ClassA a = pairGeneric.getFirst();
    System.out.println("f5() = ");
    // если в качестве параметра передан объект PairGeneric<ClassB> -
    // будут вызваны методы класса ClassB
    a.method1();
    a.method2();
    ClassA a2 = new ClassA();
    ClassB b2 = new ClassB();

    // Нельзя вызвать метод setFirst() ни с аргументом a2, ни с аргументом
    // b2, иначе он бы привел к ошибке соблюдения типов.
    // компилятору требуется какой-либо подтип ClassA, но неизвесто
    // какой именно. Он отказывается передать любой конкретный тип,
    // , так как знак подстановки ? в методе setFirst(? extends ClassA),
    // может и не совпасть с этим типом. В этом, собственно, и состоит
    // главный смысл ограниченных подстановок. С их помощью можно
    // теперь различать безопасные методы доступа от небезопасных
    // модифицирующих методов.
    // pairGeneric.setFirst(a2);
    // pairGeneric.setFirst(b2);
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
   * @param pairGeneric
   */
  public static void f6(PairGeneric<? super ClassB> pairGeneric) {
    // ClassA classA = pairGeneric.getFirst(); // методы доступа не разрешены
    ClassB classB = new ClassB(); // модифицирующие методы разрешены
    pairGeneric.setFirst(classB);
    ClassC classC = new ClassC();
    pairGeneric.setFirst(classC);
    // модифицирующий метод для суперкласса (только ClassA)
    // не разрешен. Не понял почему.
    ClassA classA = new ClassA();
    //pairGeneric.setFirst(classA);
  }

  /**
   * НЕОГРАНИЧЕННЫЕ ПОДСТАНОВКИ. Неограниченный тип удобен
   * для выполнения очень простых операций. Например метод проверяет,
   * содержит ли пара пустую ссылку на объект. Такому методу вообще
   * не требуется конкретный тип.
   * @param pairGeneric
   * @return
   */
  public static boolean f7(PairGeneric<?> pairGeneric) {
    return pairGeneric.getFirst() == null || pairGeneric.getSecond() == null;
  }

  /** метод демонстрирует механизм захвата подстановки */
  public static void swap(PairGeneric<?> pairGeneric) { // необобщенный метод
    swapHelper(pairGeneric); // захврат подстановки
  }

  /** меняет местами элементы пары */
  private static <T> void swapHelper(PairGeneric<T> p) { // обобщенный метод
    T t = p.getFirst();
    p.setFirst(p.getSecond());
    p.setSecond(t);
  }
}

class ClassA implements IA {
  @Override
  public void method1() {
    System.out.println("classA method1");
  }

  @Override
  public void method2() {
    System.out.println("classA method2");
  }
}

class ClassB extends ClassA implements IA, IB {
  @Override
  public void method1() {
    System.out.println("classB method1");
  }

  @Override
  public void method2() {
    System.out.println("classB method2");
  }

  public void method3() {
    System.out.println("classB method3");
  }
}

class ClassC extends ClassB {
  public void method4() {
    System.out.println("ClassC method1");
  }
}

interface IA {
  void method1();
  void method2();
}

interface IB {
  void method3();
}


