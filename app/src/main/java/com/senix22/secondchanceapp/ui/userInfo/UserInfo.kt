package com.senix22.secondchanceapp.ui.userInfo

import com.senix22.secondchanceapp.data.models.Repository
import com.senix22.secondchanceapp.data.models.User

data class UserInfo(
    val user: User,
    val repositories: List<Repository>
)
