package stud.gilmon.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import stud.gilmon.data.local.entities.CouponsEntity
import stud.gilmon.data.local.entities.ReviewsEntity

@Dao
interface ReviewsDao {
    @Insert(entity = ReviewsEntity::class)
    fun insertReview(reviewsEntity: ReviewsEntity)


    @Query("SELECT * FROM reviews WHERE user_id = :userId")
    fun getById(userId: String): List<ReviewsEntity>

    @Delete(entity = ReviewsEntity::class)
    fun deleteCoupon(reviewsEntity: ReviewsEntity)
}