package com.senix22.secondchanceapp.data

import com.senix22.secondchanceapp.data.models.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubDataService {
    @GET("/search/users")
    fun search(
        @Query("q") q: String
    ): Call<SearchUserResponse>
}