package com.example.characters.fragment

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.characters.adapter.UserListAdapter
import com.example.characters.databinding.FragmentListUsersBinding
import com.example.characters.model.User
import com.example.characters.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersListFragment : Fragment() {

    private var _binding: FragmentListUsersBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val adapter by lazy { UserListAdapter(requireContext()) }
    private var retrofitService: Call<List<User>>? = null

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

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = false
        }

        binding.listUsers.adapter = adapter

        //TODO сделать отдельную эксеншин фнкцию
        binding.listUsers.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.left = 20
                outRect.right = 20
                val item = parent.adapter?.itemCount ?: return
                val position = parent.getChildAdapterPosition(view)
                if (position != item - 1)
                    outRect.bottom = 25
            }
        })

        retrofitService = RetrofitService.loadingRetrofitService().getUsers()
        retrofitService?.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val user = response.body() ?: return
                    adapter.submitList(user)
                }
                retrofitService = null
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                retrofitService = null
            }
        })


    }

    override fun onDestroyView() {
        retrofitService?.cancel()
        _binding = null
        super.onDestroyView()

    }

    /*companion object {
        private val
    }*/

}