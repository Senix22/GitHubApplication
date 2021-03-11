package com.senix22.secondchanceapp.ui.userInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.senix22.secondchanceapp.data.GithubServiceRepository
import com.senix22.secondchanceapp.data.models.User
import com.senix22.secondchanceapp.ui.State
import com.senix22.secondchanceapp.ui.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserInfoViewModel @Inject constructor(
    private val githubServiceRepository: GithubServiceRepository
) : ViewModel() {
    private val userInfoStateLiveData = MutableLiveData<State<UserInfo, Error>>()
    val stateLiveData: LiveData<State<UserInfo, Error>> = userInfoStateLiveData

    private val _searchUsersStateLiveData = MutableLiveData<List<User>>()
    val searchUsersStateLiveData: LiveData<List<User>> = _searchUsersStateLiveData

    fun loadUserInfo(userName: String?) {
        userInfoStateLiveData.value = State.Loading
        GlobalScope.launch(Dispatchers.IO) {
            userInfoStateLiveData.postValue(
                githubServiceRepository.getUserInfo(userName)
            )
        }
    }

    fun searchUsers(q: String) {
        GlobalScope.launch(Dispatchers.IO) {
            _searchUsersStateLiveData.postValue(
                githubServiceRepository.searchUsers(q)
            )
        }
    }
}