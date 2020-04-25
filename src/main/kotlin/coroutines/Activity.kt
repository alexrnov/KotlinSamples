package coroutines

/*
 * Copyright 2016-2020 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

import kotlin.coroutines.*
import kotlinx.coroutines.*

class Activity : CoroutineScope by CoroutineScope(Dispatchers.Default) {

  fun destroy() {
    cancel() // Extension on CoroutineScope
  }
  // to be continued ...

  // class Activity continues
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