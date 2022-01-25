package com.habibi.find.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.habibi.core.data.source.remote.response.UserRepositoryResponseItem
import com.habibi.find.databinding.ItemUserRepositoryBinding
import com.habibi.find.utils.setImage

class UserRepositoryAdapter(
    private val dataSet: List<UserRepositoryResponseItem?>
) : RecyclerView.Adapter<UserRepositoryAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemUserRepositoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataSet[position]
        holder.bind(data!!)
    }

    override fun getItemCount(): Int =
        dataSet.size

    inner class ViewHolder(
        private val binding: ItemUserRepositoryBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(data: UserRepositoryResponseItem){
            binding.apply {
                setImage(ivAvatar, data.owner?.avatarUrl)

                tvNameRepository.text = data.name
                tvStar.text = data.stargazersCount.toString()

                tvDescription.text =
                    if (data.description == null)
                        "-"
                    else
                        data.description
            }
        }
    }
}