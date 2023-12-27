package com.omersungur.theworldwander.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.omersungur.theworldwander.core.Constants.PREFERENCES_GET_DATA_FOR_THE_FIRST_TIME
import com.omersungur.theworldwander.core.Constants.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(PREFERENCES_NAME)

@ViewModelScoped
class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private object PreferenceKeys {
        val getSaveLastClickedCountry =
            stringPreferencesKey(PREFERENCES_GET_DATA_FOR_THE_FIRST_TIME)
    }

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveLastClickedCountry(countryName: String) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.getSaveLastClickedCountry] = countryName
        }
    }

    val readLastClickedCountry: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        } .map { preferences ->
            val getDataFromApiForTheFirstTime = preferences[PreferenceKeys.getSaveLastClickedCountry] ?: "Nothing clicked!"
            getDataFromApiForTheFirstTime
        }
}