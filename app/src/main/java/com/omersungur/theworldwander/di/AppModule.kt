package com.omersungur.theworldwander.di

import com.omersungur.theworldwander.data.local.database.ImageToDeleteDao
import com.omersungur.theworldwander.data.local.database.ImageToUploadDao
import com.omersungur.theworldwander.data.remote.CountryApi
import com.omersungur.theworldwander.data.repository.CountryRepositoryImpl
import com.omersungur.theworldwander.domain.repository.CountryRepository
import com.omersungur.theworldwander.domain.use_case.CountryUseCases
import com.omersungur.theworldwander.domain.use_case.GetCountries
import com.omersungur.theworldwander.domain.use_case.mongo.AddCountriesInMongoDB
import com.omersungur.theworldwander.domain.use_case.mongo.AddImagesToFirebase
import com.omersungur.theworldwander.domain.use_case.mongo.CountryMongoUseCases
import com.omersungur.theworldwander.domain.use_case.mongo.DeleteAllImagesFromMongoDB
import com.omersungur.theworldwander.domain.use_case.mongo.DeleteImagesFromFirebase
import com.omersungur.theworldwander.domain.use_case.mongo.FilterCountries
import com.omersungur.theworldwander.domain.use_case.mongo.GetAllCountriesFromMongoDB
import com.omersungur.theworldwander.domain.use_case.mongo.GetSelectedCountry
import com.omersungur.theworldwander.domain.use_case.mongo.UpdateCountryImage
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

    @Provides
    @Singleton
    fun provideCountryMongoUseCases(
        imageToDeleteDao: ImageToDeleteDao,
        imageToUploadDao: ImageToUploadDao
    ): CountryMongoUseCases {
        return CountryMongoUseCases(
            addCountriesInMongoDB = AddCountriesInMongoDB(),
            filterCountries = FilterCountries(),
            getAllCountriesFromMongoDB = GetAllCountriesFromMongoDB(),
            deleteAllImagesFromMongoDB = DeleteAllImagesFromMongoDB(imageToDeleteDao),
            getSelectedCountry = GetSelectedCountry(),
            addImagesToFirebase = AddImagesToFirebase(imageToUploadDao),
            deleteImagesFromFirebase = DeleteImagesFromFirebase(imageToDeleteDao),
            updateCountryImage = UpdateCountryImage()
        )
    }
}