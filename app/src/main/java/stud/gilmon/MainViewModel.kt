package stud.gilmon

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import stud.gilmon.data.local.entities.UsersEntity
import stud.gilmon.data.model.FeedItem
import stud.gilmon.data.remote.UnsplashApi
import stud.gilmon.data.remote.UnsplashDto
import stud.gilmon.data.remote.paging.UnsplashPhotosPagingSource
import stud.gilmon.domain.DataStoreRepository
import stud.gilmon.domain.RoomRepository
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    context: Context,
    private val pagingSource: UnsplashPhotosPagingSource.Factory,
    private val dataStoreRepository: DataStoreRepository,
    private val roomRepository: RoomRepository,
    private val unsplashApi: UnsplashApi
) : ViewModel() {
    private val userKey = stringPreferencesKey("USER_KEY")

    val feedItems: StateFlow<PagingData<FeedItem>> = Pager(PagingConfig(10))
    { pagingSource.create() }.flow
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    //    private val Context.dataStore by preferencesDataStore(name = "settings")
//    private val dataStore = context.dataStore
    private val loadingMutableStateFlow = MutableStateFlow(true)
    val loadingFlow
        get() = loadingMutableStateFlow.asStateFlow()

    private val remoteRandomPhotosMutableStateFlow =
        MutableStateFlow<List<UnsplashDto>?>(null)
    val remoteRandomPhotosStateFlow: Flow<List<UnsplashDto>?>
        get() = remoteRandomPhotosMutableStateFlow.asStateFlow()
    var login = ""

    init {

    }

    val readFromDataStore = dataStoreRepository.loginFlow.asLiveData()

    //    fun get() : String{
//        var login = ""
//        viewModelScope.launch {
//             login =dataStoreRepository.getTUser() ?: "bull"
//            loadingMutableStateFlow.value= false
//        }
//        return login
//    }

    fun getPhotos() {

        viewModelScope.launch {
            loadingMutableStateFlow.value = true
            runCatching {


                unsplashApi.getRandomPhotos("cfU1iXa1PBM5G1SfHlT7YhUOSDy9cwXF4GSQ1lDCsAM", 2)
            }.onSuccess {
                remoteRandomPhotosMutableStateFlow.value = it
                loadingMutableStateFlow.value = false
                /*    MailAuthConfig.login -> {
                        remoteUserMailInfoMutableStateFlow.value = it as RemoteUser.RemoteMailUser
                        insertItem(it as RemoteUser)
                    }*/
            }.onFailure {
                Timber.tag("Oauth").d(it)
                loadingMutableStateFlow.value = false
            }
        }
    }

    fun getUser(login: String): UsersEntity? {
        val user = roomRepository.getUser(login)
        return user
    }
}