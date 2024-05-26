package stud.gilmon.presentation.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import stud.gilmon.data.local.entities.UsersEntity
import stud.gilmon.domain.RoomRepository
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    context: Context,
    val roomRepository: RoomRepository
) : ViewModel() {
    fun getUser(login:String):UsersEntity?{
        val user = roomRepository.getUser(login)
        return user
    }
    fun updateUserData(usersEntity: UsersEntity){
        roomRepository.upsertUser(usersEntity)
    }
}

