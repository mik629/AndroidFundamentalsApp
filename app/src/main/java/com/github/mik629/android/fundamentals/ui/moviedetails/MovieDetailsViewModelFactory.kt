package com.github.mik629.android.fundamentals.ui.moviedetails

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory

@AssistedFactory
interface MovieDetailsViewModelFactory {
    fun create(
        @Assisted movieId: Long
    ): MovieDetailsViewModel
}