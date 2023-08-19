package stud.gilmon.ui.main.profile.settings

import android.app.Application
import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import stud.gilmon.BaseApplication
import javax.inject.Inject

class SettingsViewModel(application:Application) : AndroidViewModel(application){

    private val Context.dataStore by preferencesDataStore(name = "settings")
    private val dataStore = application.dataStore


    val darkThemeFlow = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val uiMode = preferences[darkModeKey] ?: false
            uiMode
        }
    companion object {
        val darkModeKey = booleanPreferencesKey("DARK_MODE_KEY")
    }


    fun setTheme(isDarkMode: Boolean) {
        viewModelScope.launch { dataStore.edit { preferences ->
            preferences[darkModeKey] = isDarkMode
        } }
    }


    fun getTheme(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                val uiMode = preferences[darkModeKey] ?: false
                uiMode
            }
    }
}