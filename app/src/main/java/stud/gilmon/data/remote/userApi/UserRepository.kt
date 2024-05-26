package stud.gilmon.data.remote.userApi

import stud.gilmon.data.remote.RemoteUser
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val githubApi: GithubApi
   // private val mailApi: MailApi?
) {

   suspend fun getUser(){
        githubApi.getCurrentUser()
    }
}