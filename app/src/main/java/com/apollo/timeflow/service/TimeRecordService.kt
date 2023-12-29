package com.apollo.timeflow.service

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "Time Unit Record DataStore")

/**
 *  Service 获取数据
 */
class TimeFormatRecordDataStoreService private constructor(
    private val context: Context? = null
) {
    val isDateShow: Flow<Boolean> = context?.dataStore?.data
        ?.map { preferences ->
            preferences[IS_DATE_SHOW] ?: false
        } ?: flowOf(false)

    suspend fun isDateShow(value: Boolean) {
        context?.dataStore?.edit { settings ->
            settings[IS_DATE_SHOW] = value
        }
    }

    val timeFormatRecord: Flow<Boolean> = context?.dataStore?.data
        ?.map { preferences ->
            preferences[TIME_FORMAT_RECORD_TAG] ?: false
        } ?: flowOf(false)

    suspend fun timeFormat(value: Boolean) {
        context?.dataStore?.edit { settings ->
            settings[TIME_FORMAT_RECORD_TAG] = value
        }
    }

    companion object {
        val IS_DATE_SHOW = booleanPreferencesKey("is date show")
        val TIME_FORMAT_RECORD_TAG = booleanPreferencesKey("time format")

        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: TimeFormatRecordDataStoreService? = null

        fun getInstance(context: Context?): TimeFormatRecordDataStoreService = synchronized(this) {
            if (instance == null) {
                instance = TimeFormatRecordDataStoreService(context)
            }
            return instance!!
        }
    }
}