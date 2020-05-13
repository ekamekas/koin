package com.github.ekamekas.baha.core.domain.entity

import com.github.ekamekas.baha.common.ext.exhaustive

/**
 * Wrapper class of result data with state
 * @param <T> Type of result
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()

    fun fold(onError: (Exception) -> Unit, onSuccess: (R) -> Unit) {
        when(this) {
            is Error -> {
                onError.invoke(
                    this.exception
                )
            }
            is Success -> {
                onSuccess.invoke(
                    this.data
                )
            }
        }.exhaustive
    }

    fun <R> foldRight(fnR: () -> Result<R>): Result<R> {
        return when(this) {
            is Error -> {
                this
            }
            is Success -> {
                fnR.invoke()
            }
        }
    }

    suspend fun foldSuspend(onError: suspend (Exception) -> Unit, onSuccess: suspend (R) -> Unit) {
        when(this) {
            is Error -> {
                onError.invoke(
                    this.exception
                )
            }
            is Success -> {
                onSuccess.invoke(
                    this.data
                )
            }
        }.exhaustive
    }

    suspend fun <R> foldRightSuspend(fnR: suspend () -> Result<R>): Result<R> {
        return when(this) {
            is Error -> {
                this
            }
            is Success -> {
                fnR.invoke()
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