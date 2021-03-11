package com.senix22.secondchanceapp.data

import com.senix22.secondchanceapp.data.models.Repository
import com.senix22.secondchanceapp.data.models.User
import com.senix22.secondchanceapp.ui.userInfo.UserInfo
import com.senix22.secondchanceapp.ui.State
import com.senix22.secondchanceapp.ui.Error
import com.senix22.secondchanceapp.utils.AuthTokenSharedPref
import com.senix22.secondchanceapp.data.models.*
import javax.inject.Inject

class GithubServiceRepository @Inject constructor(
    private val githubDataService: GithubDataService,
    private val authTokenSharedPref: AuthTokenSharedPref
) {

    fun getUserInfo(userName: String?): State<UserInfo, Error> {
        val authToken = "${authTokenSharedPref.tokenType} ${authTokenSharedPref.accessToken}"
        var user: User?
        var repositories: List<Repository>?
        if (userName == null) {
            githubDataService.getUserByToken(authToken).execute().let { userResponse ->
                if (!userResponse.isSuccessful) {
                    return getErrorByResponseCode(userResponse.code())
                }
                user = userResponse.body()
            }
            githubDataService.getRepositoriesByToken(authToken).execute()
                .let { repositoriesResponse ->
                    if (!repositoriesResponse.isSuccessful) {
                        return getErrorByResponseCode(repositoriesResponse.code())
                    }
                    repositories = repositoriesResponse.body()
                }
        } else {
            githubDataService.getUser(authToken, userName).execute().let { userResponse ->
                if (!userResponse.isSuccessful) {
                    return getErrorByResponseCode(userResponse.code())
                }
                user = userResponse.body()
            }
            githubDataService.getUserRepositories(userName).execute().let { repositoriesResponse ->
                if (!repositoriesResponse.isSuccessful) {
                    return getErrorByResponseCode(repositoriesResponse.code())
                }
                repositories = repositoriesResponse.body()
            }
        }
        user?.let { user ->
            repositories?.let { repositories ->
                return State.Content(UserInfo(user, repositories))
            }
        }
        return State.Error(Error.LOADING_ERROR)
    }


    fun getPulls(
        repo: String,
        owner: String
    ): State<List<Pulls>, Error> {
        githubDataService.getPulls(owner, repo).execute()
            .let { pulls ->
                if (!pulls.isSuccessful) {
                    return getErrorByResponseCode(pulls.code())
                }
                pulls.body()?.let { pulls ->
                    return State.Content(pulls)
                }
            }
        return State.Error(Error.LOADING_ERROR)
    }

    fun searchUsers(q: String): List<User> {
        githubDataService.search(q).execute().let { searchResponse ->
            if (!searchResponse.isSuccessful) {
                return emptyList()
            }
            searchResponse.body()?.let { searchUserResponse ->
                return searchUserResponse.items
            }
        }
        return emptyList()
    }

    private fun <T> getErrorByResponseCode(code: Int): State<T, Error> = when (code) {
        401 -> State.Error(Error.UNAUTHORIZED_ERROR)
        403 -> State.Error(Error.FORBIDDEN_ERROR)
        404 -> State.Error(Error.NOT_FOUND_ERROR)
        else -> State.Error(Error.LOADING_ERROR)
    }
}