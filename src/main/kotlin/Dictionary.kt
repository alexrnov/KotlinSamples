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
import java.util.stream.Collectors
import java.io.File.separator as s

object DictionaryMain {

  val r: Random = Random()

  @JvmStatic
  fun main(args: Array<String>) {
    println("DictionaryMain")
    val path = Paths.get("D:${s}info${s}words.xls")
    println("fileName = " + path.fileName)
    val sheet = getSheetOfWebResource(path.toFile())
    val table = getTableWithPairsOfWords(sheet)
    table.forEach { pair ->
        //println(pair.component1() + " : " + pair.component2())
    }

    val list = ArrayList<String>()
    table.map {e -> list.add(e.component1())}
    val englishWords = table.stream().map {it.component1()}.collect(Collectors.toSet())


    for (k in 0..10) {
      val v = table[r.nextInt(table.size)]
      println(v.component1() + " : " + v.component2())
    }

    println("enter:")
    val in2 = Scanner(System.`in`)
    val v3 = in2.nextInt()
    println("v3 = $v3")
  }
}

/**
 * Получить объект листа excel из файла с минералогическими пробами,
 * загруженного с web-ресурса ИСИХОГИ.
 * [Sheet] - объект листа excel
 */
fun getSheetOfWebResource(excelFile: File): Sheet {
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
  val extensionOfFile = fileName.substring(
          fileName.lastIndexOf(".") + 1)
  return extensionOfFile.toLowerCase()
}

fun getTableWithPairsOfWords(sheet: Sheet):
        MutableList<Pair<String, String>> {
  val table: MutableList<Pair<String, String>> = ArrayList()
  // считать все строки листа
  var i = 0
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
private fun getCurrentLineOfSheet(row: Row):
        Pair<String, String> {
  var pair = Pair("Нет данных", "Нет данных")
  val cell0: Cell? = row.getCell(0)
  val cell1: Cell? = row.getCell(1)
  if (cell0 != null && cell0.toString().isNotEmpty() && cell0.toString() != " "
          && cell1 != null && cell1.toString().isNotEmpty() && cell1.toString() != " ") {
    pair = Pair(cell0.toString(), cell1.toString())
  }
  return pair
}



