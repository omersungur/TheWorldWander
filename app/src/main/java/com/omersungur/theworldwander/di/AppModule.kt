package com.omersungur.theworldwander.di

import com.omersungur.theworldwander.data.remote.CountryApi
import com.omersungur.theworldwander.data.repository.CountryRepositoryImpl
import com.omersungur.theworldwander.domain.repository.CountryRepository
import com.omersungur.theworldwander.domain.use_case.CountryUseCases
import com.omersungur.theworldwander.domain.use_case.GetCountries
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

    @Provides
    @Singleton
    fun provideUseCases(
        countryRepository: CountryRepository,
    ): CountryUseCases {
        return CountryUseCases(
           getCountries = GetCountries(countryRepository)
        )
    }
}