package stud.gilmon.data.remote.userApi

import retrofit2.http.GET
import retrofit2.http.Query
import stud.gilmon.data.remote.RemoteUser

interface OkApi {
    @GET("users.getInfo")
    suspend fun getCurrentUser(
        @Query("access_token") token: String
    ): RemoteUser
}