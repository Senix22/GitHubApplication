package com.senix22.secondchanceapp.ui.repositoryDetails.pulls

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.senix22.secondchanceapp.App
import com.senix22.secondchanceapp.R
import com.senix22.secondchanceapp.data.models.Pulls
import com.senix22.secondchanceapp.databinding.PullsListFragmentBinding
import com.senix22.secondchanceapp.ui.BaseFragment
import com.senix22.secondchanceapp.ui.NavigationActivity
import com.senix22.secondchanceapp.ui.State
import com.senix22.secondchanceapp.utils.viewmodel.ViewModelFactory
import javax.inject.Inject


class PullsListFragment : BaseFragment(R.layout.pulls_list_fragment) {

    private lateinit var binding: PullsListFragmentBinding
    private lateinit var viewModel: PullsListViewModel
    private val pullsListAdapter = PullsListAdapter {
        navigator.showUserInfoFragment(it)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    companion object {
        private const val REPOS_NAME_KEY = "REPOS_NAME_KEY"
        private const val OWNER_NAME_KEY = "OWNER_NAME_KEY"

        fun newInstance(reposName: String, reposOwner: String): PullsListFragment {
            return PullsListFragment().also {
                it.arguments = Bundle().apply {
                    putString(REPOS_NAME_KEY, reposName)
                    putString(OWNER_NAME_KEY, reposOwner)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PullsListFragmentBinding.bind(view)
        setupRecyclerView()
        initViewModel()
        observeViewModel()
        loadPulls()
    }

    private fun initViewModel() {
        ((requireActivity() as NavigationActivity).application as App).daggerComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[PullsListViewModel::class.java]
    }

    private fun loadPulls() {
        with(requireArguments()) {
            val reposName = getString(REPOS_NAME_KEY, "")
            val ownerName = getString(OWNER_NAME_KEY, "")
            viewModel.loadPulls(reposName, ownerName)
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvContributors) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = pullsListAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> showLoading()
                is State.Content -> showPulls(it.data)
                is State.Error -> errorHandling(it.error)
            }
        })
    }

    private fun showPulls(pulls: List<Pulls>) {
        hideLoading()
        pullsListAdapter.submitList(pulls)
    }

    private fun hideLoading() {
        with(binding) {
            pbLoading.visibility = View.GONE
            rvContributors.visibility = View.VISIBLE
        }
    }

    private fun showLoading() {
        with(binding) {
            pbLoading.visibility = View.VISIBLE
            rvContributors.visibility = View.GONE
        }
    }
}