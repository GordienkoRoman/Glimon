package stud.gilmon.data.model

import androidx.room.PrimaryKey

data class PersonalData(
    val firstName:String,
    val lastName:String,
    val gender:String,
    val age:String,
    val familyStatus:String,
    val aboutMe:String)
