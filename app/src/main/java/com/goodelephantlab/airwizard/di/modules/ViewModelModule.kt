package com.goodelephantlab.airwizard.di.modules

import com.goodelephantlab.airwizard.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(
            get(),
            get(),
            get()
        )
    }
}
