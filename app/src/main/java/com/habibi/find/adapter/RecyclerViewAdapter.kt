package com.habibi.find.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.habibi.core.data.source.local.entity.UsersEntity
import com.habibi.find.databinding.ItemSearchUsersBinding
import com.habibi.find.utils.setImage

class CustomAdapter(
    private val dataSet: List<UsersEntity>,
    private val onClick: (UsersEntity) -> Unit
): RecyclerView.Adapter<CustomAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemSearchUsersBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(
            view,
            onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int =
        dataSet.size

    inner class ViewHolder(
        private val binding: ItemSearchUsersBinding,
        val onClick: (UsersEntity) -> Unit
    ): RecyclerView.ViewHolder(binding.root){

        private var currentItem: UsersEntity? = null

        init {
            binding.root.setOnClickListener {
                currentItem?.let {
                    onClick(it)
                }
            }
        }

        fun bind(data: UsersEntity){
            currentItem = data

            binding.apply {
                setImage(ivAvatar, data.avatarUrl)
                tvName.text = data.login
                tvType.text = data.type
            }
        }
    }
}