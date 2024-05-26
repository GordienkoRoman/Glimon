package stud.gilmon.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import stud.gilmon.data.local.entities.CouponsEntity

@Dao
interface CouponsDao {
    @Insert(entity = CouponsEntity::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertCoupon(couponsEntity: CouponsEntity):Long

    @Query("SELECT * FROM coupons WHERE user_id = :userId")
    fun getByUserId(userId: String): List<CouponsEntity>

    @Query("SELECT * FROM coupons WHERE coupon_id = :couponId")
    fun getById(couponId: Long): CouponsEntity

    @Delete(entity = CouponsEntity::class)
    fun deleteCoupon(couponsEntity: CouponsEntity)
}