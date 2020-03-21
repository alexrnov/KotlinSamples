interface Producer {

  fun produce()
  var v10: Int
  val v1: Int
  val v4: Int get() = 5
  val v5: ClassDelegate1 get() = ClassDelegate1()
  val v7: ClassDelegate1
  var v8: ClassDelegate1
}

class ProducerImpl : Producer {
  override var v10: Int = 10
  override var v8 = ClassDelegate1("10")
  override val v1: Int
    get() = 10 //To change initializer of created properties use File | Settings | File Templates.

  override val v7: ClassDelegate1
    get() = ClassDelegate1("10")

  override fun produce() {
    // service.doSomething() how to access service here
    println( "ProducerImpl")
  }
}

class EnhancedProducer(private val delegate: Producer) : Producer by delegate {

  // how to share this with delegate
  //private val service = Service()

  fun test() {
    println("this.v1 = " + this.v1)
    println("this.v2 = " + this.v4)
    println("this.v10 = " + this.v10)
    println("delegate.v2 = " + delegate.v1)
    println("delegate.v4 = " + delegate.v4)
    println("delegate.v10 = " + delegate.v10)
    produce()
  }

  fun delegateFun() {
    this.v10 = 100
    println("v10 = " + this.v10)
  }


  fun changeV7() {
    this.v7.v = "100"
    println("this.v7.v = " + this.v7.v)
  }

}

fun main() {
  val producerImpl = ProducerImpl()
  producerImpl.v5.v = "10"
  val producer = EnhancedProducer(producerImpl)

  producer.test()
  producer.delegateFun()
  producer.changeV7()
  println("producerImpl.v10 = " + producerImpl.v10)
  producerImpl.v10 = 500
  println("producer.v10 = " + producer.v10)
  println("producer.v10 =" + producer.v10)
  producer.v10 = 1000
  println("producer.v10 = " + producer.v10)
  println("producerImpl.v10 = " + producerImpl.v10)
  producerImpl.v10 = 5000
  println("producer.v10 = " + producer.v10)
  println("producerImpl.v10 = " + producerImpl.v10)
  println("------------")
  producer.v8.v ="100"
  println("producer.v8 = " + producer.v8.v)
  println("producerImpl.v8 = " + producerImpl.v8.v)
  producer.v8.v="1000"
  println("producer.v8 = " + producer.v8.v)
  println("producerImpl.v8 = " + producerImpl.v8.v)
  println("------------")
  val classDelegate1 = ClassDelegate1("5")
  println(classDelegate1.v)
  classDelegate1.v = "10"
  println(classDelegate1.v)
  println("------------")
  val id = ClassID()

  id.cdv.v = "100"
  println(id.cdv.v)
}

class ClassDelegate1(var v: String = "5") {

}

class ClassID: InterDelegate1 {

}

interface InterDelegate1 {
  val cdv: ClassDelegate1 get() = ClassDelegate1("5")
}