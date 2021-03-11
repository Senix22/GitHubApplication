package com.senix22.secondchanceapp.ui.repositoryDetails.pulls

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.senix22.secondchanceapp.data.GithubServiceRepository
import com.senix22.secondchanceapp.data.models.Pulls
import com.senix22.secondchanceapp.ui.State
import com.senix22.secondchanceapp.ui.Error
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class PullsListViewModel @Inject constructor(
    private val githubServiceRepository: GithubServiceRepository
) : ViewModel() {
    private val stateData = MutableLiveData<State<List<Pulls>, Error>>()
    val stateLiveData: LiveData<State<List<Pulls>, Error>> = stateData
    fun loadPulls(repoName: String, ownerName: String) {
        stateData.value = State.Loading
        GlobalScope.launch(Dispatchers.IO) {
            stateData.postValue(
                githubServiceRepository.getPulls(
                    repoName,
                    ownerName
                )
            )
        }
    }
}
