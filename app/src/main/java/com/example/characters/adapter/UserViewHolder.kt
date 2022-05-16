package com.example.characters.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.characters.databinding.FragmentUserBinding
import com.example.characters.model.PageItem
import com.example.characters.model.User

class UserViewHolder(
    private val binding: FragmentUserBinding,
    private val click: (PageItem.Context<out User>) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: PageItem.Context<out User>) {
        with(binding) {
            userName.text = user.data.name
            userPhoto.load(user.data.userPhoto[0])

            root.setOnClickListener {
                click(user)
            }
        }
    }

}