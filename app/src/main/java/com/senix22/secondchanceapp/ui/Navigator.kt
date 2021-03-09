package com.senix22.secondchanceapp.ui

import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import com.senix22.secondchanceapp.ui.autorization.AuthFragment
import com.senix22.secondchanceapp.ui.login.LoginFragment


class Navigator(
    private val fragmentManager: FragmentManager,
    @IdRes private val container: Int
) {
    companion object {
        private const val USER_INFO_FRAGMENT = "USER_INFO_FRAGMENT"
        private const val AUTH_FRAGMENT = "AUTH_FRAGMENT"
        private const val ISSUE_DETAILS_FRAGMENT = "ISSUE_DETAILS_FRAGMENT"
        private const val REPOSITORY_DETAILS_FRAGMENT = "REPOSITORY_DETAILS_FRAGMENT"
    }

    fun showLoginScreen() {
        fragmentManager.beginTransaction()
            .add(container, LoginFragment.newInstance())
            .commit()
    }
    fun showAuthFragment() {
        fragmentManager.beginTransaction()
            .add(container, AuthFragment.newInstance())
            .addToBackStack(AUTH_FRAGMENT)
            .commit()
    }

}