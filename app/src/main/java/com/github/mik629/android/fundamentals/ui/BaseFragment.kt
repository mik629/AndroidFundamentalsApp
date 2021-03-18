package com.github.mik629.android.fundamentals.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.github.mik629.android.fundamentals.di.InjectingSavedStateViewModelFactory
import javax.inject.Inject

abstract class BaseFragment(resId: Int) : Fragment(resId), HasDefaultViewModelProviderFactory {
    @Inject
    lateinit var defaultViewModelFactory: dagger.Lazy<InjectingSavedStateViewModelFactory>

    /**
     * This method androidx uses for `by viewModels` method.
     * We can set out injecting factory here and therefore don't touch it again later
     */
    override fun getDefaultViewModelProviderFactory(): ViewModelProvider.Factory =
        defaultViewModelFactory.get().create(this, arguments)
}