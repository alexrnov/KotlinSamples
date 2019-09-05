package collections

object SequencesSamples {

  /**
   * Последовательнсоть Sequence выполняет все шаги обработки
   * один за другим для каждого отдельного элемента. В свою очередь,
   * коллекция Iterable завершает каждый шаг для всей коллекции и
   * затем переходит к следующему шагу. Таким образом,
   * последовательности Sequence позволяют избежать построения
   * результатов промежуточных шагов, тем самым улучшая
   * производительность всей цепочки обработки коллекции. Однако
   * ленивая природа последовательностей Sequence добавляет некоторые
   * накладные расходы памяти, которые могут быть существенными
   * при обработке небольших коллекций или выполнении простых
   * вычислений. Следовательно, выбор использования
   * Sequence-последовательности или Iterable-коллекции
   * зависит от конкретного случая.
   */
  @JvmStatic
  fun main(args: Array<String>) {
    constructing()
    processingExample()
  }

  private fun constructing() {
    println("constructing(): ")
    // from elements:
    val numberSequence = sequenceOf(1, 2, 3, 4, 5)
    numberSequence.forEach { print("$it ") }
    println()
    // from iterable:
    val numbers = listOf(1, 2, 3, 4, 5)
    val numberSequence2 = numbers.asSequence()
    numberSequence2.forEach { print("$it ") }
    println()
    // from function:
    val numberSequence3 = generateSequence(1) { if (it < 10) it + 2 else null }
    numberSequence3.forEach { print("$it ")}
    println()
    println("------------------------------------")
  }

  // сравнение процессов для iterable-коллекции и
  // sequence-последовательности. Итерация требует 23 шага,
  // в то время как последовательность требует только 18
  private fun processingExample() {
    val words = "The quick brown fox jumps over the lazy dog".split(" ")
    // итерация требует 23 шага
    val lengthsList = words.filter{ println("filter: it.length > 3 = $it"); it.length > 3 }
            .map{println("map: it.length =  ${it.length}"); it.length}
            .take(4) // получить первые четыре элемента
    println("Lengths of first 4 words longer than 3 chars:")
    println(lengthsList)
    println("-")

    // последовательность требут 18 шагов
    val wordSequence = words.asSequence()
    val lengthSequence = wordSequence.filter{ println("filter: it.length > 3 = $it"); it.length > 3 }
            .map{println("map: it.length =  ${it.length}"); it.length}
            .take(4) // получить первые четыре элемента
    println("Lengths of first 4 words longer than 3 chars:")
    println(lengthSequence.toList())
  }
}