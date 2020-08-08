package com.github.ekamekas.baha.core.domain.entity

/**
 * Wrapper class of result data with state
 * @param <T> Type of result
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()

    suspend fun foldSuspend(fnL: suspend (Exception) -> Unit, fnR: suspend (result: R) -> Unit): Result<R> {
        return when(this) {
            is Error -> {
                fnL.invoke(exception)
                this
            }
            is Success -> {
                fnR.invoke(data)
                this
            }
        }
    }

    suspend fun foldRightSuspend(fnR: suspend (result: R) -> Unit): Result<R> {
        return when(this) {
            is Error -> {
                this
            }
            is Success -> {
                fnR.invoke(this.data)
                this
            }
        }
    }

    suspend fun <T> mapSuspend(fnL: suspend (Exception) -> Result<T>, fnR: suspend (result: R) -> Result<T>): Result<T> {
        return when(this) {
            is Error -> {
                fnL.invoke(exception)
            }
            is Success -> {
                fnR.invoke(data)
            }
        }
    }

    suspend fun <T> mapRightSuspend(fnR: suspend (result: R) -> Result<T>): Result<T> {
        return when(this) {
            is Error -> {
                this
            }
            is Success -> {
                fnR.invoke(this.data)
            }
        }
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}