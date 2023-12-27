package com.omersungur.theworldwander.data.repository

import com.omersungur.theworldwander.core.Constants.APP_ID
import com.omersungur.theworldwander.core.Resource
import com.omersungur.theworldwander.domain.model.CountryMongo
import com.omersungur.theworldwander.domain.repository.CountryMongoRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

object CountryMongoRepositoryImpl : CountryMongoRepository {
    private val app = App.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm

    init {
        configureTheRealm()
    }

    override fun configureTheRealm() {
        user?.let { user ->
            val config = SyncConfiguration.Builder(
                user,
                setOf(CountryMongo::class)
            ).initialSubscriptions { sub ->
                add(
                    query = sub.query<CountryMongo>("ownerId == $0", user.id),
                    name = "User's Images with Countries Info"
                )
            }.log(LogLevel.ALL)
                .build()
            realm = Realm.open(config)
        }
    }

    override fun getAllCountries(query: String): Flow<Resource<List<CountryMongo>>> {
        return if (user != null) {
            try {
                realm.query<CountryMongo>(query = "ownerId == $0", user.id).asFlow().map {
                    Resource.Success(data = it.list)
                }
            } catch (e: Exception) {
                println(e.message.toString())
                flow { emit(Resource.Error(message = e.toString())) }
            }
        } else {
            println("null")
            flow { emit(Resource.Error(message = UserNotAuthenticatedException().message.toString())) }
        }
    }

    override fun getSelectedCountry(countryId: ObjectId): Flow<Resource<CountryMongo>> {
        return if (user != null) {
            try {
                realm.query<CountryMongo>(query = "_id == $0", countryId).asFlow().map {
                    Resource.Success(data = it.list.first())
                }
            } catch (e: Exception) {
                flow { emit(Resource.Error(e.toString())) }
            }
        } else {
            flow { emit(Resource.Error(UserNotAuthenticatedException().message.toString())) }
        }
    }

    override suspend fun addCountry(country: CountryMongo): Resource<CountryMongo> {
        return if (user != null) {
            realm.write {
                try {
                    val addedDiary =
                        copyToRealm(
                            country.apply {
                                ownerId = user.id
                            }
                        )
                    Resource.Success(data = addedDiary)
                } catch (e: Exception) {
                    Resource.Error(message = e.toString())
                }
            }
        } else {
            Resource.Error(message = UserNotAuthenticatedException().message.toString())
        }
    }

    override suspend fun deleteAllImagesInMongoDB(): Resource<Boolean> {
        return if (user != null) {
            realm.write {
                val countries = this.query<CountryMongo>("ownerId == $0", user.id).find()
                try {
                    countries.map {
                        delete(it.images)
                    }
                    // delete(countries) // delete all countries info.
                    Resource.Success(data = true)
                } catch (e: Exception) {
                    Resource.Error(e.toString())
                }
            }
        } else {
            Resource.Error(UserNotAuthenticatedException().message.toString())
        }
    }

    override suspend fun updateCountry(country: CountryMongo): Resource<CountryMongo> {
        return if (user != null) {
            realm.write {
                val queriedCountry =
                    query<CountryMongo>(query = "_id == $0", country._id).first().find()
                if (queriedCountry != null) {
                    queriedCountry.apply {
                        countryName = country.countryName
                        countryArea = country.countryArea
                        countryFlag = country.countryFlag
                        countryRegion = country.countryRegion
                        countryPopulation = country.countryPopulation
                        countryCurrency = country.countryCurrency
                        countryLatitude = country.countryLatitude
                        countryLongitude = country.countryLongitude
                        countryLanguage = country.countryLanguage
                        isCountryIndependent = country.isCountryIndependent
                        countryCapital = country.countryName
                        countryCode = country.countryName
                        countryTimeZones = country.countryTimeZones
                        countryCallingCode = country.countryCallingCode
                        countryBorders = country.countryBorders
                        images = country.images
                    }
                    Resource.Success(data = queriedCountry)
                } else {
                    Resource.Error(message = Exception("Query didn't find").message.toString())
                }
            }
        } else {
            Resource.Error(UserNotAuthenticatedException().message.toString())
        }
    }

    override suspend fun deleteImage(countryId: ObjectId): Resource<Boolean> {
        return if (user != null) {
            realm.write {
                val country =
                    query<CountryMongo>(query = "_id == $0 AND ownerId == $1", countryId, user.id).first()
                        .find()
                if (country != null) {
                    try {
                        delete(country.images)
                        Resource.Success(data = true)
                    } catch (e: Exception) {
                        Resource.Error(e.toString())
                    }
                } else {
                    Resource.Error(Exception("Country doesn't exist").message.toString())
                }
            }
        } else {
            Resource.Error(UserNotAuthenticatedException().message.toString())
        }
    }
}

private class UserNotAuthenticatedException : Exception("User is not logged in.")

