package com.github.mik629.android.fundamentals.di.movie_details

import com.bsquaredwifi.app.di.scopes.VM
import com.github.mik629.android.fundamentals.di.AppComponent
import com.github.mik629.android.fundamentals.ui.moviedetails.FragmentMovieDetails.Companion.ARG_MOVIE_ID
import com.github.mik629.android.fundamentals.ui.moviedetails.MovieDetailsViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Named

@VM
@Component(dependencies = [AppComponent::class])
interface MovieDetailsViewModelComponent {
    val viewModel: MovieDetailsViewModel

    @Component.Builder
    interface Builder {
        fun build(): MovieDetailsViewModelComponent

        fun appComponent(appComponent: AppComponent): Builder

        @BindsInstance
        fun movieId(@Named(ARG_MOVIE_ID) id: Long): Builder
    }
}
