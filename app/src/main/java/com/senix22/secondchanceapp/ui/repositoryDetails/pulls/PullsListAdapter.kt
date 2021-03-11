package com.senix22.secondchanceapp.ui.repositoryDetails.pulls

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.senix22.secondchanceapp.R
import com.senix22.secondchanceapp.data.models.Pulls
import com.senix22.secondchanceapp.databinding.UsersListItemBinding

class PullsListAdapter(
    private val onClickItemCallback: (String) -> Unit
) :
    ListAdapter<Pulls, PullsListAdapter.ContributorViewHolder>(PullsDiffCallback()) {

    class ContributorViewHolder(
        private val binding: UsersListItemBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pulls: Pulls) {
            with(binding) {
                Glide.with(binding.root.context)
                    .load(pulls.user.avatar_url)
                    .placeholder(R.drawable.loading_placeholder)
                    .into(ivAvatar)
                tvName.text = pulls.user.login
                pullsId.text = pulls.id.toString()
                tvTitlePulls.text  = pulls.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContributorViewHolder {
        val binding = UsersListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContributorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContributorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}