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
import java.io.File.separator as s

object DictionaryMain {

  @JvmStatic
  fun main(args: Array<String>) {
    println("DictionaryMain")
    val path = Paths.get("D:${s}info${s}words.xls")
    println("fileName = " + path.fileName)
    val sheet = getSheetOfWebResource(path.toFile())
    val table = getTableOfProbesWithMSD(sheet)
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
  FileInputStream(excelFile).use { input ->
    // try с ресурсами
    val extension = getExtensionOfFile(excelFile.name)
    return if (extension == "xls") HSSFWorkbook(input) else XSSFWorkbook(input)
  }
}

private fun getExtensionOfFile(fileName: String): String {
  val extensionOfFile = fileName.substring(
          fileName.lastIndexOf(".") + 1)
  return extensionOfFile.toLowerCase()
}

fun getTableOfProbesWithMSD(sheet: Sheet):
        MutableList<MutableMap<String, String>> {
  val table: MutableList<MutableMap<String, String>> = ArrayList()
  // считать все строки листа
  var i = 0
  (0..sheet.lastRowNum).forEach { row ->
    val currentRow: Row = sheet.getRow(row)
    println(i++)
  }
  return table
}

// получить отображение с данными текущей строки (row) листа
// excel-файла, загруженного с web-ресурса. Заголовок (title)
// - это ассоциативный массив, где в качестве ключа хранятся
// индексы ячеек, а в качестве значений - их имена.
private fun getCurrentLineOfSheet(row: Row, title: Map<Int, String>):
        MutableMap<String, String> {
  var emptyString = true
  val map = HashMap<String, String>()
  title.forEach { indexColumn, nameColumn ->
    val cell: Cell? = row.getCell(indexColumn)
    var valueCell = "Нет данных"
    if (cell != null && cell.toString().isNotEmpty() && cell.toString() != " ") {
      valueCell = cell.toString()
      emptyString = false
    }
    map[nameColumn] = valueCell
  }
  //если пустая строка (все значения = "Нет данных"), вернуть пустой массив
  if (emptyString) map.clear()
  return map
}
