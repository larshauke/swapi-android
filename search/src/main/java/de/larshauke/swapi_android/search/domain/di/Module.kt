package de.larshauke.swapi_android.search.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.larshauke.swapi_android.search.domain.use_case.Search
import de.larshauke.swapi_android.search.domain.use_case.SearchImpl

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    fun provideSearch(
        impl: SearchImpl
    ): Search = impl
}