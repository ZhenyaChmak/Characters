package com.example.characters.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.characters.databinding.FragmentUserBinding
import com.example.characters.model.PageItem

class UserViewHolder(
    private val binding: FragmentUserBinding,
    private val click: (PageItem.User) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: PageItem.User) {
        with(binding){
            userName.text = user.name
            userPhoto.load(user.userPhoto[0])

            root.setOnClickListener {
                click(user)
            }
        }
    }

}