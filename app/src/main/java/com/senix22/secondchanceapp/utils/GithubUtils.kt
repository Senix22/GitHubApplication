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
    const val clientId = "Iv1.579fd42febefaf65"
    const val clientSecret = "680cbb713513d806fe83fe305adfb5d00167e674"
     const val redirectUrl = "gitsecondchance://callback"
     const val scopes = "repo, user"
     const val schema = "https"
     const val githubHost = "github.com"


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

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .client(
                OkHttpClient().newBuilder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .baseUrl(HttpUrl.Builder().scheme(schema).host(githubHost).build())
            .build()
    }

    private  val githubService: GithubAuthService by lazy {
        retrofit.create(GithubAuthService::class.java)
    }

    fun getCodeFromUri(uri: Uri?): String? {
        uri ?: return null
        if (!uri.toString().startsWith(redirectUrl)) {
            return null
        }
        return uri.getQueryParameter("code")
    }
    suspend fun getAccesToken(code: String): AuthToken {
        return githubService.getAccessToken(clientId, clientSecret, code)
    }

    suspend fun getUser(token: String): User {
        return githubService.getUser(token)
    }
}