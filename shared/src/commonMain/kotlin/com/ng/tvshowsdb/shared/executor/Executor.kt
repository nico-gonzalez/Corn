package com.ng.tvshowsdb.shared.executor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Executor(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun <T> invoke(block: suspend () -> T) =
        withContext(dispatcher) { runCatching {  block() } }
}