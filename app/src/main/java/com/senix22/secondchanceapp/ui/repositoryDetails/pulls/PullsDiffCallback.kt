package com.senix22.secondchanceapp.ui.repositoryDetails.pulls

import androidx.recyclerview.widget.DiffUtil
import com.senix22.secondchanceapp.data.models.Pulls

class PullsDiffCallback : DiffUtil.ItemCallback<Pulls>() {
    override fun areItemsTheSame(oldItem: Pulls, newItem: Pulls): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Pulls, newItem: Pulls): Boolean {
        return oldItem == newItem
    }
}