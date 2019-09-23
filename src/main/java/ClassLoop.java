public class ClassLoop {
  public static void main(String[] args) {
    Ca[] a = new Ca[5];
    for (int i = 0; i < a.length; i++) {
      a[i] = new Ca(i);
    }
    for (Ca k: a) {
      System.out.println("k = " + k.getI());
    }
  }
}

class Ca {
  private Integer i;
  
  Ca(Integer i) {
    this.i = i;  
  }
  
  public Integer getI() {
    return this.i;
  }
}