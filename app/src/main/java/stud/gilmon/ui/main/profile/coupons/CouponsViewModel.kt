package stud.gilmon.ui.main.profile.coupons

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.Lazily
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


class CouponsViewModel @Inject constructor() : ViewModel()
{
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
     val screenState: MutableSharedFlow<List<Int>> = MutableSharedFlow()

    var couponsStatus : Int = 0
    init {
        coroutineScope.launch {
            screenState.emit(listOf(0,0,0))
        }
    }

}