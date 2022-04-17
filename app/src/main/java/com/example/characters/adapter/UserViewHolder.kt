package com.example.characters.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.characters.databinding.FragmentUserBinding
import com.example.characters.model.User

class UserViewHolder(
    private val binding: FragmentUserBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.userName.text = user.name
        binding.userPhoto.load("https://gkbzmcahsvowlfjslvnm.supabase.in/storage/v1/object/public/characters/Ao.webp")

    }
}