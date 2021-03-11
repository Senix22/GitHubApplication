package com.senix22.secondchanceapp.utils

import android.net.Uri
import com.senix22.secondchanceapp.data.GithubAuthService
import com.senix22.secondchanceapp.data.GithubServiceRepository
import com.senix22.secondchanceapp.data.models.AuthToken
import com.senix22.secondchanceapp.data.models.User
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit


object GithubUtils {
    const val clientId = "Iv1.2e4d209685a203dd"
    const val clientSecret = "368775dc94960542e9c8c243a7c80d3d2643b13e"
    private const val redirectUrl = "secondchance://callback"
    private const val scopes = "repo, user"
    private const val schema = "https"
    private const val githubHost = "github.com"

    fun buildAuthGitHubUrl(): Uri {
        return Uri.Builder()
            .scheme(schema)
            .authority(githubHost)
            .appendEncodedPath("login/oauth/authorize")
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("scope", scopes)
            .appendQueryParameter("redirect_url", redirectUrl)
            .build()
    }

    fun getCodeFromUri(uri: Uri?): String? {
        uri ?: return null
        if (!uri.toString().startsWith(redirectUrl)) {
            return null
        }
        return uri.getQueryParameter("code")
    }
}