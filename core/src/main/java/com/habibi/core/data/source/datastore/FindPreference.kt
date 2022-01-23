package com.habibi.core.data.source.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FindPreference(private val context: Context) {

    private val keywordSearch = stringPreferencesKey("keyword_search")

    fun getKeywordSearch(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[keywordSearch] ?: ""
        }
    }

    suspend fun saveKeywordSearch(keyword: String) {
        context.dataStore.edit { preferences ->
            preferences[keywordSearch] = keyword
        }
    }

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "search")
    }
}