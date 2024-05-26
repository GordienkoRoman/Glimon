package stud.gilmon.data.model

data class FeedItem(
    val id :Int=0,
    val companyName: String,
    val promotionName: String,
    val description: String,
    val location: String,
    val imgUrl: String,
    val reviews: List<String>?=null,
    val downloads:Int =0,
    val likes:Int =0
)
