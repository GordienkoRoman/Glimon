package stud.gilmon.presentation.ui.feed.FeedItemScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import stud.gilmon.data.model.FeedItem
import stud.gilmon.domain.RoomRepository
import javax.inject.Inject

class FeedItemViewModel @Inject constructor(
    context: Context,
    private val roomRepository: RoomRepository,
) : ViewModel() {

    fun insertCoupon(feedItem: FeedItem, userId:String){
        roomRepository.insertCoupon(feedItem,userId)
    }
    fun insertReview(feedItem: FeedItem, userId:String,review:String){
        roomRepository.insertReview(feedItem, userId,review)
    }
}