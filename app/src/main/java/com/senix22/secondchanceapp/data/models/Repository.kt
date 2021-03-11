package com.senix22.secondchanceapp.data.models

import com.google.gson.annotations.SerializedName
import com.senix22.secondchanceapp.data.models.User

data class Repository(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("owner") val owner: User
)
