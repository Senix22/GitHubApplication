package com.senix22.secondchanceapp.data

import com.senix22.secondchanceapp.data.models.AuthToken
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
    ): Call<AuthToken>
}