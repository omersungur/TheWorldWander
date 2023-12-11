package com.omersungur.theworldwander.di

import com.omersungur.theworldwander.data.remote.CountryApi
import com.omersungur.theworldwander.data.repository.CountryRepositoryImpl
import com.omersungur.theworldwander.domain.repository.CountryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(countryApi: CountryApi): CountryRepository {
        return CountryRepositoryImpl(countryApi)
    }

}