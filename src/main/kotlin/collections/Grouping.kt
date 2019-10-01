package collections

object GroupingSamples {

  @JvmStatic
  fun main(args: Array<String>) {
    val numbers = listOf("one", "two", "three", "four", "five", "six")
    // группировка по первой букве в слове
    println(numbers.groupBy { it.first().toUpperCase()} )
    // группировка по длине слова
    val numbers2 = listOf("1", "2", "323", "4", "5", "13", "345", "43")
    println(numbers2.groupBy { it.length } )
    // группировка по первой букве в слове; дополнительно меняются
    // значения
    println(numbers.groupBy(keySelector = { it.first() },
            valueTransform = { it.toUpperCase() }))
    // groupingBy() применяется тогда, когда требуется сгруппировать
    // элементы, а затем приминить дополнительную операцию ко
    // всем группам одновременно. Группы создаются непосредственно
    // перед применением операции.
    // подсчитать количество элементов в каждой групппе
    println(numbers.groupingBy { it.first() }.eachCount())
  }

}