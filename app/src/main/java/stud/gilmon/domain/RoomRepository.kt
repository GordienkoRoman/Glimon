package stud.gilmon.domain

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import kotlinx.coroutines.runBlocking
import stud.gilmon.data.local.dao.CouponsDao
import stud.gilmon.data.local.dao.ReviewsDao
import stud.gilmon.data.local.dao.UsersDao
import stud.gilmon.data.local.entities.CouponsEntity
import stud.gilmon.data.local.entities.ReviewsEntity
import stud.gilmon.data.local.entities.UsersEntity
import stud.gilmon.data.model.FeedItem
import stud.gilmon.data.model.ReviewItem
import timber.log.Timber
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val usersDao: UsersDao,
    private val couponsDao: CouponsDao,
    private val reviewsDao: ReviewsDao,
    val context: Context
) {
    suspend fun upsertUser(usersEntity: UsersEntity) {
                usersDao.upsertUser(usersEntity)
    }

    suspend fun insertCoupon(feedItem: FeedItem, userId: String) {
                couponsDao.insertCoupon(CouponsEntity.fromFeedItem(feedItem, userId))
    }

    suspend fun deleteCoupon(feedItem: FeedItem, userId: String) {
                couponsDao.deleteCoupon(
                    couponsEntity = CouponsEntity.fromFeedItem(
                        feedItem,
                        userId
                    )
                )
    }

    suspend fun insertReview(feedItem: FeedItem, userId: String, review: String) {
        val couponsEntity = CouponsEntity.fromFeedItem(feedItem, "")
        val id = couponsDao.insertCoupon(couponsEntity)
        reviewsDao.insertReview(ReviewsEntity(id = id, review = review, userId = userId))
    }

    suspend fun deleteReview(reviewItem: ReviewItem, userId: String) {
        reviewsDao.deleteReview(ReviewsEntity.fromReviewItem(reviewItem, userId))
    }

    fun getUser(login: String): UsersEntity? {
        return usersDao.getUserByLogin(login)
    }

    suspend fun getCoupons(login: String): List<FeedItem> {
        return couponsDao.getByUserId(login).map { it.toFeedItem() }
    }

    suspend fun getReviews(login: String): List<ReviewItem> {
        return reviewsDao.getById(login).map {
            val feedItem = couponsDao.getById(it.id).toFeedItem()
            it.toReviewItem(feedItem)
        }
    }
}