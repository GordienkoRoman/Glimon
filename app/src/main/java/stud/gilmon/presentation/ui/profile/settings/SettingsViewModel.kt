package stud.gilmon.presentation.ui.profile.settings

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import stud.gilmon.data.github.RemoteGithubUser
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import net.openid.appauth.AuthorizationService
import stud.gilmon.R
import stud.gilmon.data.github.GithubApi
import stud.gilmon.data.github.UserRepository
import stud.gilmon.data.oauth.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    context: Context,
    private val githubApi: GithubApi) : ViewModel(){

    private val Context.dataStore by preferencesDataStore(name = "settings")
    private val dataStore = context.dataStore
    private val loadingMutableStateFlow = MutableStateFlow(false)
    private val userInfoMutableStateFlow = MutableStateFlow<RemoteGithubUser?>(null)
    private val toastEventChannel = Channel<Int>(Channel.BUFFERED)
    private val logoutPageEventChannel = Channel<Intent>(Channel.BUFFERED)
    private val logoutCompletedEventChannel = Channel<Unit>(Channel.BUFFERED)

    private val authService: AuthorizationService = AuthorizationService(context.applicationContext)

    private val authRepository = AuthRepository()
    private val userRepository = UserRepository()

    val loadingFlow: Flow<Boolean>
        get() = loadingMutableStateFlow.asStateFlow()

    val userInfoFlow: Flow<RemoteGithubUser?>
        get() = userInfoMutableStateFlow.asStateFlow()

    val logoutPageFlow: Flow<Intent>
        get() = logoutPageEventChannel.receiveAsFlow()

    val logoutCompletedFlow: Flow<Unit>
        get() = logoutCompletedEventChannel.receiveAsFlow()


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

    fun loadUserInfo() {

        viewModelScope.launch {
            loadingMutableStateFlow.value = true
            runCatching {
                githubApi.getCurrentUser()
                userRepository.getUserInformation()
            }.onSuccess {
                userInfoMutableStateFlow.value = it
                loadingMutableStateFlow.value = false
            }.onFailure {
                Timber.tag("Oauth").d(it)
                loadingMutableStateFlow.value = false
                userInfoMutableStateFlow.value = null
                toastEventChannel.trySendBlocking(R.string.get_user_error)
            }
        }
    }
    fun logout() {
        val customTabsIntent = CustomTabsIntent.Builder().build()

        val logoutPageIntent = authService.getEndSessionRequestIntent(
            authRepository.getEndSessionRequest(),
            customTabsIntent
        )

        logoutPageEventChannel.trySendBlocking(logoutPageIntent)
    }
    fun webLogoutComplete() {
        authRepository.logout()
        logoutCompletedEventChannel.trySendBlocking(Unit)
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }
}
class SettingsViewModelFactory @Inject constructor(
    private val context: Context,
    private val githubApi: GithubApi
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            SettingsViewModel(this.context, this.githubApi) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}