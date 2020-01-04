import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.file.Paths
import java.util.*
import java.io.File.separator as s

typealias printWord = (word: Pair<String, String>) -> Unit
object DictionaryMain {

  @JvmStatic
  fun main(args: Array<String>) {
    val path = Paths.get("D:${s}info${s}words.xls")
    val sheet = getSheetOfWebResource(path.toFile())
    val table = getTableWithPairsOfWords(sheet)
    //table.forEach { pair -> println(pair.component1() + " : " + pair.component2()) }
    //val englishWords = table.stream().map {it.component1()}.collect(Collectors.toSet())
    printWordsToConsole(table, { print(it.component1())}, { println(it.component2())})
    //printWordsToConsole(table, { print(it.component2())}, { println(it.component1())})
  }
}

private fun printWordsToConsole(table: List<Pair<String, String>>, f1: printWord, f2: printWord) {
  val r = Random()
  val scanner = Scanner(System.`in`)
  var result: String
  var success = 0
  var number = 0
  var newWord = true
  var word: Pair<String, String> = table[r.nextInt(table.size)]
  do {
    if (newWord) {
      f1(word)
      result = scanner.nextLine()
      newWord = false
    } else {
      f2(word)
      println("Success (y/n): ?")
      result = scanner.nextLine()
      if (result == "y") success++ else if (result == "n") success--
      word = table[r.nextInt(table.size)]
      newWord = true
      println(success)
    }
    number++
  } while (result != "e")
  println(success)
}
/**
 * Получить объект листа excel из файла с минералогическими пробами,
 * загруженного с web-ресурса ИСИХОГИ.
 * [Sheet] - объект листа excel
 */
private fun getSheetOfWebResource(excelFile: File): Sheet {
  val workbook = getWorkBook(excelFile)
  println(workbook.getSheetName(0))
  return workbook.getSheetAt(0)
}

@Throws(IOException::class)
private fun getWorkBook(excelFile: File): Workbook {
  FileInputStream(excelFile).use { input -> // try с ресурсами
    val extension = getExtensionOfFile(excelFile.name)
    return if (extension == "xls") HSSFWorkbook(input) else XSSFWorkbook(input)
  }
}

private fun getExtensionOfFile(fileName: String): String {
  val extensionOfFile = fileName.substring(fileName.lastIndexOf(".") + 1)
  return extensionOfFile.toLowerCase()
}

private fun getTableWithPairsOfWords(sheet: Sheet): List<Pair<String, String>> {
  val table: MutableList<Pair<String, String>> = ArrayList()
  // считать все строки листа
  (0..sheet.lastRowNum).forEach { row ->
    val currentRow: Row = sheet.getRow(row)
    val pair = getCurrentLineOfSheet(currentRow)
    table.add(pair)
  }
  return table
}

// получить отображение с данными текущей строки (row) листа
// excel-файла, загруженного с web-ресурса. Заголовок (title)
// - это ассоциативный массив, где в качестве ключа хранятся
// индексы ячеек, а в качестве значений - их имена.
private fun getCurrentLineOfSheet(row: Row): Pair<String, String> {
  fun isCellsCorrect(cell0: Cell?, cell1: Cell?): Boolean = (cell0 != null && cell0.toString().isNotEmpty() && cell0.toString() != " "
          && cell1 != null && cell1.toString().isNotEmpty() && cell1.toString() != " ")
  var pair = Pair("Нет данных", "Нет данных")
  val cell0: Cell? = row.getCell(0)
  val cell1: Cell? = row.getCell(1)
  if (isCellsCorrect(cell0, cell1)) pair = Pair(cell0.toString(), cell1.toString())
  return pair
}