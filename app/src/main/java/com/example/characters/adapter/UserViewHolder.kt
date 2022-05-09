package com.example.characters.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.characters.databinding.FragmentUserBinding
import com.example.characters.model.PageItem

class UserViewHolder(
    private val binding: FragmentUserBinding,
    private val clickDetails: (PageItem.User) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: PageItem.User) {
        binding.userName.text = user.name
        binding.userPhoto.load(user.userPhoto[0])

        binding.root.setOnClickListener {
            clickDetails(user)
        }
    }

}