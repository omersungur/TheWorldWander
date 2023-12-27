package com.omersungur.theworldwander.domain.model

import com.omersungur.theworldwander.core.toRealmInstant
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId
import java.time.Instant

open class CountryMongo : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId.invoke()
    var ownerId: String = ""
    var countryName: String = ""
    var countryFlag: String = ""
    var countryLanguage: String = ""
    var countryRegion: String = ""
    var countryPopulation: String = ""
    var countryLatitude: String = ""
    var countryLongitude: String = ""
    var countryArea: String = ""
    var countryCurrency: String = ""
    var isCountryIndependent: Boolean = false
    var countryCapital: String = ""
    var countryTimeZones: RealmList<String> = realmListOf()
    var countryCallingCode: RealmList<String> = realmListOf()
    var countryCode: String = ""
    var countryBorders: RealmList<String> = realmListOf()
    var images: RealmList<String> = realmListOf()
    var date: RealmInstant = Instant.now().toRealmInstant()
}