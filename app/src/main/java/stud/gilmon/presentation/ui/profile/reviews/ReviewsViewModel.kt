package stud.gilmon.presentation.ui.profile.reviews

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReviewsViewModel @Inject constructor() : ViewModel(){
    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    val screenState: MutableSharedFlow<List<Int>> = MutableSharedFlow()

    init {
        coroutineScope.launch {
            screenState.emit(listOf(0,0,0))
        }
    }

}