package stud.gilmon.data.remote.userApi

import retrofit2.http.GET
import stud.gilmon.data.remote.RemoteUser

interface GithubApi {
    @GET("user")
    suspend fun getCurrentUser(
    ): RemoteUser.RemoteGithubUser
}