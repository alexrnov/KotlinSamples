import java.util.HashMap
import java.util.HashSet

object Class120 {

  @JvmStatic
  fun main(args: Array<String>) {
    val map = HashMap<String, String>()
    map["key1"] = "value1"
    map["key2"] = "value2"
    map["key3"] = "value3"
    map.forEach { k, v -> println("k = $k v = $v") }
    println("-----------------------")
    val s = HashSet<String>()
    s.add("key1")
    s.add("key2")
    map.keys.retainAll(s)
    map.forEach { k, v -> println("k = $k v = $v") }
  }
}
