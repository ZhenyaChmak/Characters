package com.example.characters.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.characters.databinding.FragmentUserBinding
import com.example.characters.model.UserLoading

class UserViewHolder(
    private val binding: FragmentUserBinding,
    private val clickDetails: (UserLoading.User) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: UserLoading.User) {
        binding.userName.text = user.name
        binding.userPhoto.load(user.userPhoto[0])

        binding.root.setOnClickListener {
            clickDetails(user)
        }
    }

}