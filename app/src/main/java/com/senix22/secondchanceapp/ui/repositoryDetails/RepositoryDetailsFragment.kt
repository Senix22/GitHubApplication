package com.senix22.secondchanceapp.ui.repositoryDetails

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.senix22.secondchanceapp.R
import com.senix22.secondchanceapp.data.models.Repository
import com.senix22.secondchanceapp.databinding.RepositoryDetailsFragmentBinding
import com.senix22.secondchanceapp.ui.BaseFragment
import com.senix22.secondchanceapp.ui.repositoryDetails.pulls.PullsListFragment


class RepositoryDetailsFragment : BaseFragment(R.layout.repository_details_fragment) {

    private lateinit var binding: RepositoryDetailsFragmentBinding

    companion object {
        private const val REPOS_NAME_KEY = "REPOS_NAME_KEY"
        private const val REPOS_DESCRIPTION_KEY = "REPOS_DESCRIPTION_KEY"
        private const val REPOS_OWNER_NAME_KEY = "REPOS_OWNER_NAME_KEY"

        fun newInstance(repository: Repository): RepositoryDetailsFragment {
            return RepositoryDetailsFragment().also {
                it.arguments = Bundle().apply {
                    putString(REPOS_NAME_KEY, repository.name)
                    putString(REPOS_DESCRIPTION_KEY, repository.description)
                    putString(REPOS_OWNER_NAME_KEY, repository.owner.login)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = RepositoryDetailsFragmentBinding.bind(view)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            val reposName = requireArguments().getString(REPOS_NAME_KEY, "")
            val reposDescription = requireArguments().getString(REPOS_DESCRIPTION_KEY, "")
            tvRepositoryName.text = reposName
            tvRepositoryDescription.text = reposDescription
            val reposOwnerName = requireArguments().getString(REPOS_OWNER_NAME_KEY, "")
            val fragments = listOf(
                PullsListFragment.newInstance(reposName, reposOwnerName),
            )
            vpDetails.adapter =
                ViewPagerAdapter(
                    this@RepositoryDetailsFragment,
                    fragments
                )
            val tabNames = listOf(
                getString(R.string.contributors_tab),

                )
            TabLayoutMediator(tlTabs, vpDetails) { tab, position ->
                tab.text = tabNames[position]
            }.attach()
        }
    }
}