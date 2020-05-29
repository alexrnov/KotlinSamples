package classesAndObjects

import kotlin.math.min

object ClassFactoryCompanionSample {

  @JvmStatic
  fun main(args: Array<String>) {
    val b1 = Building.createWoodcutter()
    println("b1 = ${b1.age}")
    val b2 = Building.createWoodcutter2()
    println("b2 = ${b2.age}")
    val b3 = Building("cg", 78)
    println("b1 = ${b3.age}")
    val v = min(0.4, 0.5)
    println("v = $v")
  }


  class Building(val name: String, val age: Int) {

    init {
      println("init")
    }
    companion object {
      fun createWoodcutter(): Building = Building("Woodcutter5", 56)
      fun createWoodcutter2(): Building = Building("Woodcutter7", 76)
    }
  }
}