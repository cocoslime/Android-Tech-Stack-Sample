package com.cocoslime

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    val flow = flow<Int> {
        emit(1)
        delay(500L)
        emit(2)
        delay(500L)
        emit(3)
    }

    val scope = CoroutineScope(Dispatchers.Default)
    val job = scope.launch {
        /*
        launch {
            val startTime = System.currentTimeMillis()
            flow
                .onEach { println("[${System.currentTimeMillis() - startTime}] number emit >> $it") }
                .collect { value ->
                    println("[${System.currentTimeMillis() - startTime}] number collect >> $value")
                    delay(1300L)
                }
        }
        */

        println("=========================================")
        launch {
            val startTime = System.currentTimeMillis()
            flow
                .onEach {
                    println("[${System.currentTimeMillis() - startTime}] number emit >> $it")
                }
                .collectLatest { value ->
                    delay(1300L)
                    println("[${System.currentTimeMillis() - startTime}] number collect >> $value")
                }
        }
        /*
        println("=========================================")
        launch {
            val startTime = System.currentTimeMillis()
            flow
                .onEach { println("[${System.currentTimeMillis() - startTime}] number emit >> $it") }
                .conflate().collect {
                    println("[${System.currentTimeMillis() - startTime}] number collect >> $it")
                    delay(1300L)
                }
        }

         */
    }

    runBlocking { job.join() }
}
