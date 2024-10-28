package de.larshauke.swapi_android.details.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import de.larshauke.swapi_android.details.data.repository.DetailsRepositoryImpl
import de.larshauke.swapi_android.details.data.webservice.DetailsApi
import de.larshauke.swapi_android.details.domain.repository.DetailsRepository
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
class Module {

    @Provides
    @ViewModelScoped
    fun provideDetailsApi(
        retrofit: Retrofit
    ): DetailsApi =
        retrofit.create(DetailsApi::class.java)

    @Provides
    @ViewModelScoped
    fun provideDetailsRepository(
        impl: DetailsRepositoryImpl
    ): DetailsRepository = impl
}