package com.github.mik629.android.fundamentals.di.movies_list

import com.bsquaredwifi.app.di.scopes.VM
import com.github.mik629.android.fundamentals.di.AppComponent
import com.github.mik629.android.fundamentals.ui.movieslist.MoviesListViewModel
import dagger.Component

@VM
@Component(dependencies = [AppComponent::class])
interface MoviesListViewModelComponent {
    val viewModel: MoviesListViewModel
}
