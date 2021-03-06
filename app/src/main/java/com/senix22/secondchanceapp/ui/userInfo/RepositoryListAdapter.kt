package com.senix22.secondchanceapp.ui.userInfo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.senix22.secondchanceapp.data.models.Repository
import com.senix22.secondchanceapp.databinding.RepositoryItemBinding

class RepositoryListAdapter(private val onClickItemCallback: (Repository) -> Unit) :
    ListAdapter<Repository, RepositoryListAdapter.RepositoryViewHolder>(
        RepositoryDiffCallback()
    ) {
    class RepositoryViewHolder(
        private val binding: RepositoryItemBinding,
        private val onClickItemCallback: (Repository) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: Repository) {
            with(binding) {
                tvRepositoryName.text = repository.name
                tvRepositoryDescription.text = repository.description
                root.setOnClickListener {
                    onClickItemCallback(repository)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = RepositoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return RepositoryViewHolder(binding, onClickItemCallback)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}