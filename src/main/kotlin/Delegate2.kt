interface Producer2 {

  fun produce()
}

class ProducerImpl2(private val service: Service, var r: Int) : Producer2 {
  override fun produce() {
    service.x = 5
    service.y = 5
    r = 50
  }
  fun println() {
    println("producerImpl2: service x = ${service.x}, service y = ${service.y}, r = $r")
  }
}

class EnhancedProducer2(private val service: Service, var r: Int) : Producer2 by ProducerImpl2(service, r) {
  override fun produce() {
    service.x = 100
    service.y = 100
    r = 500
  }

  fun println() {
    println("enchancedProducer2: service x = ${service.x}, service y = ${service.y}, r = $r")
  }
}

fun main() {

  val service = Service(1, 100)
  var r = 10
  val producerImpl = ProducerImpl2(service, r)
  val enhancedProducer = EnhancedProducer2(service, r)

  println("------------")
  producerImpl.produce()
  enhancedProducer.println()
  producerImpl.println()
  println("------------")
  enhancedProducer.produce()
  enhancedProducer.println()
  producerImpl.println()
  println("------------")
  enhancedProducer.produce()
  enhancedProducer.println()
  println("------------")
  producerImpl.produce()
  producerImpl.println()
  println("------------")
  producerImpl.r = 1000
  enhancedProducer.println()
  producerImpl.println()
  enhancedProducer.println()
  producerImpl.println()
}

data class Service(var x: Int, var y: Int)