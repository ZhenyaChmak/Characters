package com.example.characters.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.characters.databinding.FragmentUserBinding
import com.example.characters.databinding.ItemLoadingBinding
import com.example.characters.model.PageItem

class UserAdapter(
    context: Context,
    private val click: (PageItem.User) -> Unit
) : ListAdapter<PageItem, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PageItem.User -> TYPE_USER
            is PageItem.Loading -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_USER -> {
                UserViewHolder(
                    binding = FragmentUserBinding.inflate(layoutInflater, parent, false),
                    click = click
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
        val item = getItem(position) as? PageItem.User ?: return
        userLoadingVH.bind(item)
    }

    companion object {

        private const val TYPE_USER = 0
        private const val TYPE_LOADING = 1

        private val DIFF_UTIL = object : DiffUtil.ItemCallback<PageItem>() {
            override fun areItemsTheSame(oldItem: PageItem, newItem: PageItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PageItem, newItem: PageItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}
