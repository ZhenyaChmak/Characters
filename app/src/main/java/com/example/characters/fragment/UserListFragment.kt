package com.example.characters.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.characters.adapter.UserAdapter
import com.example.characters.database.appDatabase
import com.example.characters.databinding.FragmentListUsersBinding
import com.example.characters.decoration.addDecorationUser
import com.example.characters.model.PageItem
import com.example.characters.model.User
import com.example.characters.retrofit.ServiceLocator
import com.example.characters.retrofit.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserListFragment : Fragment() {

    private var _binding: FragmentListUsersBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var isLoading = false

    private val adapter by lazy {
        UserAdapter(requireContext())
        {
            findNavController().navigate(
                UserListFragmentDirections.toUserDetails(
                    it.data.id,
                    it.data.name
                )
            )
        }
    }

    private val userRepository by lazy {
        ServiceLocator.provideRepository()
    }

    private val userDao by lazy {
        requireContext().appDatabase.userDao()
    }

    private val pagingSource by lazy {
        PagingSource(userRepository, userDao)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListUsersBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            adapter.submitList(userDao.getUsers().map {
                PageItem.Context(it)
            })

            val users = kotlin.runCatching { userRepository.getUsers() }
                .fold(
                    onSuccess = { it },
                    onFailure = {
                        AlertDialog.Builder(requireContext())
                            .setMessage("Нет подключения к Интернету")
                            .show()
                        userDao.getUsers()
                    }
                )

            userDao.insertUser(users)
            adapter.submitList(users.map {
                PageItem.Context(it)
            } + PageItem.Loading)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val layoutManager = LinearLayoutManager(requireContext())

            usersList.adapter = adapter
            usersList.layoutManager = layoutManager
            usersList.addDecorationUser(bottomDecorator = BOTTOM_DECORATION)
            
            /* swipeRefresh.setOnRefreshListener {
                 pagingSource.onRefresh()
                 swipeRefresh.isRefreshing = false
             }*/

            // pagingSource.onLoadMore()

            /* usersList
                 .addPaginationScrollListener(layoutManager, 5) {
                     pagingSource.onLoadMore()
                 }

             pagingSource
                 .getData()
                 .onEach {
                     adapter.submitList(it.map {
                         PageItem.Context(it)
                     } + PageItem.Loading)
                 }
                 .launchIn(viewLifecycleOwner.lifecycleScope)*/

            /*usersList.addPaginationScrollFlow(layoutManager,5)
                .map { userRepository.getUsers() }
                .onEach {
                    adapter.submitList(it.map {
                        PageItem.Context(it)
                    } + PageItem.Loading)
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)*/
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val BOTTOM_DECORATION = 15
    }
}

/*fun RecyclerView.addPaginationScrollListener(
    layoutManager: LinearLayoutManager,
    itemsToLoading: Int,
    onLoadMore: () -> Unit
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val totalItemCount = layoutManager.itemCount
            val lastVisibility = layoutManager.findLastVisibleItemPosition()

            if (dy != 0 && totalItemCount <= (lastVisibility + itemsToLoading)) {
                recyclerView.post(onLoadMore)
            }
        }
    })
}*/

/*

fun RecyclerView.addPaginationScrollFlow(
    layoutManager: LinearLayoutManager,
    itemsToLoading: Int,
) = callbackFlow {
    val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val totalItemCount = layoutManager.itemCount
            val lastVisibility = layoutManager.findLastVisibleItemPosition()

            if (dy != 0 && totalItemCount <= (lastVisibility + itemsToLoading)) {
                trySend(Unit)
            }
        }
    }
    addOnScrollListener(listener)

    awaitClose {
        removeOnScrollListener(listener)
    }
}*/
