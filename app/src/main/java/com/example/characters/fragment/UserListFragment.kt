package com.example.characters.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.characters.adapter.UserAdapter
import com.example.characters.databinding.FragmentListUsersBinding
import com.example.characters.decoration.addDecorationUser
import com.example.characters.model.PageItem
import com.example.characters.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketException

class UserListFragment : Fragment() {

    private var _binding: FragmentListUsersBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var currentCall: Call<List<PageItem.User>>? = null
    private var isLoading = false
    private var iSLoadingCount = false

    private val adapter by lazy {
        UserAdapter(requireContext())
        {
            findNavController().navigate(
                UserListFragmentDirections.toUserDetails(
                    it.id,
                    it.name
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingUserRetrofit()
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

            swipeRefresh.setOnRefreshListener {
                iSLoadingCount = false
             //   loadingUserRetrofit()
                swipeRefresh.isRefreshing = false
            }

            val layoutManager = LinearLayoutManager(requireContext())

            listUsers.adapter = adapter
            listUsers.layoutManager = layoutManager
            listUsers.addDecorationUser(bottomDecorator = BUTTOM_DECORATION)

            listUsers.addPaginationScrollListener(layoutManager, 1) {
                if (!isLoading) {
                    loadingUserRetrofit()
                }
            }
        }
    }

    private fun loadingUserRetrofit() {
        currentCall = RetrofitService.loadingRetrofitService().getUsers()
        isLoading = true
        currentCall?.enqueue(object : Callback<List<PageItem.User>> {
            override fun onResponse(
                call: Call<List<PageItem.User>>,
                response: Response<List<PageItem.User>>
            ) {
                if (response.isSuccessful) {
                    val user = response.body() ?: return
                    if (iSLoadingCount) {
                        val currentList = adapter.currentList.toList().dropLast(1)
                        val resultList = currentList
                            .plus(user)
                            .plus(PageItem.Loading)
                        adapter.submitList(resultList)
                        isLoading = false
                    } else {
                        adapter.submitList(user + PageItem.Loading)
                        iSLoadingCount = true
                        isLoading = false
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        HttpException(response).message(),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                currentCall = null
            }

            override fun onFailure(call: Call<List<PageItem.User>>, t: Throwable) {
                if (call.isCanceled && t is SocketException) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG)
                        .show()
                }

                currentCall = null
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentCall?.cancel()
        _binding = null
    }

    companion object {
        private const val BUTTOM_DECORATION = 15
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
                onLoadMore()
            }
        }
    })
}
