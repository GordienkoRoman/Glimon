package stud.gilmon.data.remote.userApi

import retrofit2.http.GET
import retrofit2.http.Query
import stud.gilmon.data.remote.RemoteUser

interface MailApi {
    @GET("userinfo")
    suspend fun getCurrentUser(
        @Query("access_token") key: String
    ): RemoteUser.RemoteMailUser
}
