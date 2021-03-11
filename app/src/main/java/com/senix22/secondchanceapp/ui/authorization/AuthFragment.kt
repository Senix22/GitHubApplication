package com.senix22.secondchanceapp.ui.authorization

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.senix22.secondchanceapp.R
import com.senix22.secondchanceapp.databinding.AuthorizationFragmentBinding
import com.senix22.secondchanceapp.ui.BaseFragment
import com.senix22.secondchanceapp.utils.GithubUtils

class AuthFragment private constructor() : BaseFragment(R.layout.authorization_fragment) {

    private lateinit var binding: AuthorizationFragmentBinding

    companion object {
        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AuthorizationFragmentBinding.bind(view)
        binding.btnAuth.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    GithubUtils.buildAuthGitHubUrl()
                )
            )
        }
    }
}