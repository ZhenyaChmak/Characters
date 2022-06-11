package com.example.characters.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.characters.R
import com.example.characters.adapter.UserAdapter
import com.example.characters.databinding.FragmentListUsersBinding
import com.example.characters.decoration.addDecorationUser
import com.example.characters.model.PageItem
import com.example.characters.model.UserListViewModel
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserListFragment : Fragment() {

    private var _binding: FragmentListUsersBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<UserListViewModel>()

    private val adapter by lazy {
        UserAdapter(requireContext())
        {
            viewModel.getNextDetails(it)
        }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val layoutManager = LinearLayoutManager(requireContext())

            usersList.adapter = adapter
            usersList.layoutManager = layoutManager
            usersList.addDecorationUser(bottomDecorator = BOTTOM_DECORATION)

            usersList
                .addPaginationScrollListener(layoutManager, ITEMS_TO_LOADING) {
                    viewModel.onLoadMore()
                }

            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = !(viewModel.onRefresh())
            }

            viewModel
                .nextDetails
                .onEach {
                    findNavController().navigate(
                        UserListFragmentDirections.toUserDetails(
                            it.id, it.name
                        )
                    )
                }.launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel
                .getData
                .onEach { list ->
                    if (list.isEmpty()) {
                        AlertDialog.Builder(requireContext())
                            .setMessage(R.string.is_no_internet)
                            .show()
                    } else {
                        adapter.submitList(
                            list.map {
                                PageItem.Element(it)
                            } + PageItem.Loading)
                    }
                }.launchIn(viewLifecycleOwner.lifecycleScope)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val BOTTOM_DECORATION = 15
        private const val ITEMS_TO_LOADING = 1

    }
}

fun RecyclerView.addPaginationScrollListener(
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
}
