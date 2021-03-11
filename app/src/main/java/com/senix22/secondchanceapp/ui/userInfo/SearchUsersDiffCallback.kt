package com.senix22.secondchanceapp.ui.userInfo

import androidx.recyclerview.widget.DiffUtil
import com.senix22.secondchanceapp.data.models.User

class SearchUsersDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}