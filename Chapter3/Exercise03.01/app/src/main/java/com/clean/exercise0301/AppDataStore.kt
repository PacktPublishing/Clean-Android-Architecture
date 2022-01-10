package com.clean.exercise0301

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val KEY_COUNT = intPreferencesKey("key_count")

class AppDataStore(private val dataStore: DataStore<Preferences>) {

    val savedCount: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[KEY_COUNT] ?: 0
        }

    suspend fun incrementCount() {
        dataStore.edit { preferences ->
            val currentValue = preferences[KEY_COUNT] ?: 0
            preferences[KEY_COUNT] = currentValue.inc()
        }
    }
}
