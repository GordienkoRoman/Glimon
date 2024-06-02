package stud.gilmon.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import stud.gilmon.data.local.AppDataBase
import stud.gilmon.data.local.dao.CouponsDao
import stud.gilmon.data.local.dao.ReviewsDao
import stud.gilmon.data.local.dao.UsersDao
import stud.gilmon.di.components.AppScope
import stud.gilmon.domain.RoomRepository

@Module
class DataBaseModule {

    @AppScope
    @Provides
    fun provideAppDatabase(context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()

    }

    @AppScope
    @Provides
    fun provideUsersDao(appDataBase: AppDataBase): UsersDao = appDataBase.usersDao

    @AppScope
    @Provides
    fun provideReviewsDao(appDataBase: AppDataBase): ReviewsDao = appDataBase.reviewsDao

    @AppScope
    @Provides
    fun provideCouponsDao(appDataBase: AppDataBase): CouponsDao = appDataBase.couponsDao

    @AppScope
    @Provides
    fun provideRoomRepository(usersDao: UsersDao,couponsDao: CouponsDao,reviewsDao: ReviewsDao, context: Context):RoomRepository {
        return RoomRepository(usersDao = usersDao,couponsDao,reviewsDao, context = context)
    }
}