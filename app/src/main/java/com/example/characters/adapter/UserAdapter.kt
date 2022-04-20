package com.example.characters.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.characters.databinding.FragmentUserBinding
import com.example.characters.databinding.ItemLoadingBinding
import com.example.characters.model.UserLoading

class UserAdapter(
    context: Context,
    private val clickDetails: (UserLoading.User) -> Unit
) : ListAdapter<UserLoading, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int {
        return  when (getItem(position)){
            is UserLoading.User -> TYPE_USER
            is UserLoading.Loading -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_USER -> {
                UserViewHolder(
                    binding = FragmentUserBinding.inflate(layoutInflater, parent, false),
                    clickDetails = clickDetails
                )
            }

            TYPE_LOADING -> {
                LoadingViewHolder(
                    binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                )
            }
            else -> error("Error - $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val userLoadingVH = holder as? UserViewHolder ?: return
        val user = getItem(position) as? UserLoading.User ?: return
        userLoadingVH.bind(user)
    }

    companion object {

        private const val TYPE_USER = 0
        private const val TYPE_LOADING = 1

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<UserLoading>() {
            override fun areItemsTheSame(oldItem: UserLoading, newItem: UserLoading): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserLoading, newItem: UserLoading): Boolean {
                return oldItem == newItem
            }
        }
    }



}
