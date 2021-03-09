package com.senix22.secondchanceapp.data

import com.senix22.secondchanceapp.data.models.AuthToken
import com.senix22.secondchanceapp.data.models.User
import retrofit2.Call
import retrofit2.http.*

interface GithubAuthService {
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String,
    ): AuthToken
    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/user")
    suspend fun getUser(@Header("Authorization") auth: String): User

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/repositories")
    suspend fun getRepos(@Header("Authorization") auth: String): User
}