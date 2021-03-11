package com.senix22.secondchanceapp.data.models

import com.google.gson.annotations.SerializedName


data class Pulls(
    @SerializedName("title") val title: String,
    @SerializedName("state") val state: String,
    @SerializedName("user") val user: User,
    @SerializedName("id") val id: Int
)