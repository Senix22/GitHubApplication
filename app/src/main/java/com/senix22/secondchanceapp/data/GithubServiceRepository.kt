package com.senix22.secondchanceapp.data

import com.senix22.secondchanceapp.data.models.User
import com.senix22.secondchanceapp.ui.State
import com.senix22.secondchanceapp.utils.AuthTokenSharedPref
import javax.inject.Inject
import com.senix22.secondchanceapp.ui.Error

class GithubServiceRepository @Inject constructor(
    private val githubDataService: GithubDataService,
    private val authTokenSharedPref: AuthTokenSharedPref
) {
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