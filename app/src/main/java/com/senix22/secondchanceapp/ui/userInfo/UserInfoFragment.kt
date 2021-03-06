package com.senix22.secondchanceapp.ui.userInfo

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.senix22.secondchanceapp.App
import com.senix22.secondchanceapp.R
import com.senix22.secondchanceapp.databinding.UserInfoFragmentBinding
import com.senix22.secondchanceapp.ui.BaseFragment
import com.senix22.secondchanceapp.ui.NavigationActivity
import com.senix22.secondchanceapp.ui.State
import javax.inject.Inject

class UserInfoFragment : BaseFragment(R.layout.user_info_fragment) {
    private val repositoryListAdapter = RepositoryListAdapter {
        navigator.showRepositoryDetailsFragment(it)
    }
    private val usersListAdapter = SearchUsersListAdapter {
        navigator.showUserInfoFragment(it)
    }
    private lateinit var binding: UserInfoFragmentBinding
    private lateinit var viewModel: UserInfoViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        private const val USER_NAME_KEY = "USER_NAME_KEY"

        fun newInstance(userName: String?): UserInfoFragment {
            return UserInfoFragment().also {
                it.arguments = Bundle().apply {
                    putString(USER_NAME_KEY, userName)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = UserInfoFragmentBinding.bind(view)
        setupRepositoryRecyclerView()
        setupSearchUserRecyclerView()
        setupSearchView()
        initVieModel()
        observeViewModel()
        loadUserInfo()

    }

    private fun initVieModel() {
        ((requireActivity() as NavigationActivity).application as App).daggerComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserInfoViewModel::class.java]
    }

    private fun setupSearchView() {
        with(binding) {
            svSearch.setOnSearchClickListener {
                rvUsers.visibility = View.VISIBLE
            }
            svSearch.setOnCloseListener {
                rvUsers.visibility = View.GONE
                usersListAdapter.submitList(emptyList())
                false
            }
            svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) {
                        viewModel.searchUsers(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (!newText.isNullOrEmpty()) {
                        viewModel.searchUsers(newText)
                    }
                    return true
                }

            })
        }
    }

    private fun loadUserInfo() {
        requireArguments().apply {
            viewModel.loadUserInfo(getString(USER_NAME_KEY))
        }
    }

    private fun setupSearchUserRecyclerView() {
        with(binding.rvUsers) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = usersListAdapter
        }
    }

    private fun setupRepositoryRecyclerView() {
        with(binding.rvRepositories) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = repositoryListAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> showLoading()
                is State.Content -> showUserInfo(it.data)
                is State.Error -> errorHandling(it.error)
            }
        })
        viewModel.searchUsersStateLiveData.observe(viewLifecycleOwner, {
            usersListAdapter.submitList(it)
        })
    }

    private fun showLoading() {
        with(binding) {
            userInfoGroup.visibility = View.GONE
            pbLoading.visibility = View.VISIBLE
        }
    }

    private fun hideLoading() {
        with(binding) {
            userInfoGroup.visibility = View.VISIBLE
            pbLoading.visibility = View.GONE
        }
    }

    private fun showUserInfo(userInfo: UserInfo) {
        hideLoading()
        with(binding) {
            tvUserName.text = userInfo.user.login
            Glide.with(requireContext())
                .load(userInfo.user.avatar_url)
                .placeholder(R.drawable.loading_placeholder)
                .into(ivAvatar)
            repositoryListAdapter.submitList(userInfo.repositories)
        }
    }
}