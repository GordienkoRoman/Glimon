package stud.gilmon.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import stud.gilmon.data.local.dao.CouponsDao
import stud.gilmon.data.local.dao.ReviewsDao
import stud.gilmon.data.local.dao.UsersDao
import stud.gilmon.data.local.entities.CouponsEntity
import stud.gilmon.data.local.entities.ReviewsEntity
import stud.gilmon.data.local.entities.UsersEntity

@Database(
    entities = [UsersEntity::class,
        ReviewsEntity::class,
        CouponsEntity::class
],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract val usersDao: UsersDao
    abstract val couponsDao: CouponsDao
    abstract val reviewsDao: ReviewsDao
}