package com.example.characters.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.characters.databinding.FragmentUserBinding
import com.example.characters.domain.model.User

class UserViewHolder(
    private val binding: FragmentUserBinding,
    private val onUserClicked: (User) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        with(binding) {
            userName.text = user.name
            userPhoto.load(user.userPhoto[0])

            root.setOnClickListener {
                onUserClicked(user)
            }
        }
    }

}