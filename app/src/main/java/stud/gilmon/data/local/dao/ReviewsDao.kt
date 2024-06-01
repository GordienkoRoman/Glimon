package stud.gilmon.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import stud.gilmon.data.local.entities.ReviewsEntity

@Dao
interface ReviewsDao {
    @Insert(entity = ReviewsEntity::class)
    suspend fun insertReview(reviewsEntity: ReviewsEntity)


    @Query("SELECT * FROM reviews WHERE user_id = :userId")
    suspend fun getById(userId: String): List<ReviewsEntity>

    @Delete(entity = ReviewsEntity::class)
    suspend fun deleteReview(reviewsEntity: ReviewsEntity)
}