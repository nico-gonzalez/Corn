package com.ng.tvshowsdb.domain.common

class Result<out T> private constructor(val result: T? = null, val error: Throwable? = null) {

  companion object Factory {

    fun <T> success(result: T): Result<T> = Result(result)

    fun <T> error(error: Throwable): Result<T> = Result(error = error)
  }
}