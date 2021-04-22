package com.github.mik629.android.fundamentals.ui


/**
 * Describes state of the view at any
 * point of time.
 */
sealed class ViewState<out S : Any?, out E : Any?> {

    data class Success<out S : Any?>(val result: S) : ViewState<S, Nothing>()

    object Loading : ViewState<Nothing, Nothing>()

    data class Error<out E : Any?>(val result: E) : ViewState<Nothing, E>()
}