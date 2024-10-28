package de.larshauke.swapi_android.details.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import de.larshauke.swapi_android.details.domain.use_case.Details
import de.larshauke.swapi_android.details.domain.use_case.DetailsImpl

@Module
@InstallIn(ViewModelComponent::class)
class Module {

    @Provides
    @ViewModelScoped
    fun provideDetails(
        impl: DetailsImpl
    ): Details = impl

}