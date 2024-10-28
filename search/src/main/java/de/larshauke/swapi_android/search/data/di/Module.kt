package de.larshauke.swapi_android.search.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import de.larshauke.swapi_android.search.data.repository.SearchRepositoryImpl
import de.larshauke.swapi_android.search.data.webservice.SearchApi
import de.larshauke.swapi_android.search.domain.repository.SearchRepository
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class Module {

    @Provides
    @ViewModelScoped
    fun provideSearchApi(
        retrofit: Retrofit
    ): SearchApi =
        retrofit.create(SearchApi::class.java)

    @Provides
    @ViewModelScoped
    fun provideSearchRepository(
        impl: SearchRepositoryImpl
    ): SearchRepository = impl
}