package com.senix22.secondchanceapp.ui.userInfo

import androidx.recyclerview.widget.DiffUtil
import com.senix22.secondchanceapp.data.models.Repository

class RepositoryDiffCallback : DiffUtil.ItemCallback<Repository>() {
    override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
        return oldItem == newItem
    }

}