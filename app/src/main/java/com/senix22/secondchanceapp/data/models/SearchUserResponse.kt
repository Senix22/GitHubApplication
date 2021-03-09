package com.senix22.secondchanceapp.data.models

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(
    @SerializedName("items") val items: List<User>
)