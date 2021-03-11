package com.senix22.secondchanceapp.data

import com.senix22.secondchanceapp.data.models.Pulls
import com.senix22.secondchanceapp.data.models.Repository
import com.senix22.secondchanceapp.data.models.SearchUserResponse
import com.senix22.secondchanceapp.data.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubDataService {
    @GET("/user")
    fun getUserByToken(@Header("Authorization") auth: String): Call<User>

    @GET("/users/{username}")
    fun getUser(
        @Header("Authorization") auth: String,
        @Path("username") userName: String
    ): Call<User>

    @GET("/users/{owner}/starred")
    fun getUserRepositories(@Path("owner") owner: String): Call<List<Repository>>

    @GET("/user/starred")
    fun getRepositoriesByToken(@Header("Authorization") auth: String): Call<List<Repository>>


    @GET("/repos/{owner}/{repo}/pulls")
    fun getPulls(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<List<Pulls>>


    @GET("/search/users")
    fun search(
        @Query("q") q: String
    ): Call<SearchUserResponse>
}