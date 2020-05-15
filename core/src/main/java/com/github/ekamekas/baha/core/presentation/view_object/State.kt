package com.github.ekamekas.baha.core.presentation.view_object

import androidx.annotation.StringRes

/**
 * Wrapper class to represent state
 *
 * @param <T> Type of object when success
 */
sealed class State<out R> {

    data class Success<out T>(val data: T): State<T>()
    data class Error(@StringRes val message: Int, val exception: Exception = RuntimeException()): State<Nothing>()
    data class Progress(val progress: Float = 0.0F): State<Nothing>()

    override fun toString(): String {
        return when(this) {
            is Success -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            is Progress -> "Progress[progress=$progress]"
        }
    }

}