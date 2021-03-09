package com.senix22.secondchanceapp.ui.autorization

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.senix22.secondchanceapp.R
import com.senix22.secondchanceapp.databinding.LoginFragmentBinding
import com.senix22.secondchanceapp.ui.BaseFragment
import com.senix22.secondchanceapp.utils.GithubUtils

class AuthFragment private constructor() : BaseFragment(R.layout.login_fragment) {

    private lateinit var binding: LoginFragmentBinding

    companion object {
        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = LoginFragmentBinding.bind(view)
        binding.btnSignIn.setOnClickListener {
            requireContext().startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    GithubUtils.buildAuthGitHubUrl()
                )
            )
        }
    }
}