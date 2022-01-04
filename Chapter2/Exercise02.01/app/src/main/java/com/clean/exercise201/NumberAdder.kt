package com.clean.exercise201

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

private const val DELAY = 5000
class NumberAdder(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val delay: Int = DELAY
) {

    suspend fun add(a: Int, b: Int): Int {
        return withContext(dispatcher) {
            delay(delay.toLong())
            a + b
        }
    }
}