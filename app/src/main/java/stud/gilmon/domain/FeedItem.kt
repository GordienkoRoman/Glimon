package stud.gilmon.domain

import java.time.LocalDate
import java.util.Date

data class FeedItem(
    val id:Int,
    val title:String="Title",
 //   val validTill:Date = LocalDate.now() as Date,
    val location : String = "Location"
)
