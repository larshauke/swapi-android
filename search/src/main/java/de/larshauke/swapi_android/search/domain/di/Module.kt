package de.larshauke.swapi_android.search.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import de.larshauke.swapi_android.search.domain.use_case.LoadMore
import de.larshauke.swapi_android.search.domain.use_case.LoadMoreImpl
import de.larshauke.swapi_android.search.domain.use_case.Search
import de.larshauke.swapi_android.search.domain.use_case.SearchImpl

@Module
@InstallIn(ViewModelComponent::class)
class Module {

    @Provides
    @ViewModelScoped
    fun provideSearch(
        impl: SearchImpl
    ): Search = impl

    @Provides
    @ViewModelScoped
    fun provideLoadMore(
        impl: LoadMoreImpl
    ): LoadMore = impl
}