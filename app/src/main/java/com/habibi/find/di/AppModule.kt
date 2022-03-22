package com.habibi.find.di

import com.habibi.find.ui.detail.DetailViewModel
import com.habibi.find.ui.search.SearchViewModel
import com.habibi.find.ui.search_paging.SearchPagingViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchPagingViewModel(get()) }
}