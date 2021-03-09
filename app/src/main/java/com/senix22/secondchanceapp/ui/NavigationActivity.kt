package com.senix22.secondchanceapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.senix22.secondchanceapp.App
import com.senix22.secondchanceapp.R
import com.senix22.secondchanceapp.data.GithubAuthService
import com.senix22.secondchanceapp.data.models.AuthToken
import com.senix22.secondchanceapp.databinding.NavigationFrameBinding
import com.senix22.secondchanceapp.utils.AuthTokenSharedPref
import com.senix22.secondchanceapp.utils.GithubUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: NavigationFrameBinding
    val navigator: Navigator by lazy {
        Navigator(supportFragmentManager, R.id.container)
    }
    private val githubUtils: GithubUtils by lazy {
        GithubUtils
    }
    private val sharedPreferences by lazy {
        AuthTokenSharedPref(this)
    }

    @Inject
    lateinit var authTokenSharedPref: AuthTokenSharedPref

    @Inject
    lateinit var githubAuthService: GithubAuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NavigationFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        (application as App).daggerComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        val code = githubUtils.getCodeFromUri(uri = intent.data)
        code ?: return

        GlobalScope.launch {
            val response = githubUtils.getAccesToken(code)
            val token = "${response.tokenType} ${response.accessToken}"
            sharedPreferences.tokenType = token
            val user = githubUtils.getUser(token)

            Log.d("TAG_11", "user $user")
        }
    }

    private fun getAuthToken(code: String): AuthToken? {
        return githubAuthService.getAccessToken(
            GithubUtils.clientId,
            GithubUtils.clientSecret,
            code
        )

//        ).execute().body()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            if (authTokenSharedPref.tokenType.isEmpty() && authTokenSharedPref.tokenType.isEmpty()) {
                navigator.showAuthFragment()
            } else {
//                navigator.showUserInfoFragment(null)
            }
        }
    }
}