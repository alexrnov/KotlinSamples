package coroutines

/*
 * Copyright 2016-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

import kotlin.coroutines.*
import kotlinx.coroutines.*

// Мы можем реализовать интерфейс CoroutineScope в этом классе Activity.
// Лучший способ сделать это - использовать делегирование с заводскими функциями
// по умолчанию. Мы также можем объединить требуемый диспетчер (в этом примере
// мы использовали Dispatchers.Default) с областью действия:
class Activity : CoroutineScope by CoroutineScope(Dispatchers.Default) {

  fun destroy() {
    cancel() // Extension on CoroutineScope
  }
  // to be continued ...

  // class Activity continues
  // Теперь мы можем запускать сопрограммы в рамках этого действия без
  // необходимости явно указывать их контекст. Для демонстрации мы
  // запускаем десять сопрограмм, которые задерживаются на другое время:
  fun doSomething() {
    // launch ten coroutines for a demo, each working for a different time
    repeat(10) { i ->
      launch {
        delay((i + 1) * 200L) // variable delay 200ms, 400ms, ... etc
        println("Coroutine $i is done")
      }
    }
  }
}