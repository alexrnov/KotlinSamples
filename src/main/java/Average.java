import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TransferQueue;
import java.util.stream.Collectors;

public class Average {
  public static void main(String[] args) {
    Inter inter = new Inter((short) 3);
    float f;
    f = inter.f(1.0f);
    System.out.println("w = " + f);
    f = inter.f(2.0f);
    System.out.println("w = " + f);
    f = inter.f(3.0f);
    System.out.println("w = " + f);
    f = inter.f(4.0f);
    System.out.println("w = " + f);
    f = inter.f(5.0f);
    System.out.println("w = " + f);
    f = inter.f(6.0f);
    System.out.println("w = " + f);
    inter.print();
  }
}
class Inter {
  private short numberValues;

  public Inter(short numberValues) {
    this.numberValues = numberValues;
  }

  private Queue<Float> q = new ArrayDeque<>();

  public float f(float v) {
    q.add(v);
    if (q.size() == 1) {
      return v;
    }

    if (q.size() > numberValues) {
      q.poll();
    }

    /**
     * kotlin
     * q.stream().collect(Collectors.averagingDouble{x -> x.toDouble()}).toFloat()
     */

    /*
    return q.stream()
            .collect(Collectors.averagingDouble(Float::valueOf))
            .floatValue();
    */
    return average();
  }

  private float average() {
    float f = 0.0f;
    for (Float element: q) {
      f += element;
    }
    return f / q.size();
  }

  public void print() {
    q.forEach(System.out::println);
  }
}
