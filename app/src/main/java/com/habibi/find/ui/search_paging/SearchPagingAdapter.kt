package com.habibi.find.ui.search_paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.habibi.core.data.source.remote.response.UsersItem
import com.habibi.find.databinding.ItemSearchUsersBinding
import com.habibi.find.utils.setImage

class SearchPagingAdapter : PagingDataAdapter<UsersItem, SearchPagingAdapter.ViewHolder>(REPO_COMPARATOR) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSearchUsersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(
            view
        )
    }

    inner class ViewHolder(
        private val binding: ItemSearchUsersBinding
    ): RecyclerView.ViewHolder(binding.root){

        private var currentItem: UsersItem? = null

        fun bind(data: UsersItem){
            currentItem = data

            binding.apply {
                setImage(ivAvatar, data.avatarUrl)
                tvName.text = data.login
                tvType.text = data.type
            }
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<UsersItem>() {
            override fun areItemsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean =
                oldItem.login == newItem.login

            override fun areContentsTheSame(oldItem: UsersItem, newItem: UsersItem): Boolean =
                oldItem == newItem
        }
    }
}