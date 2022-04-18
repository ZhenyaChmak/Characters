package com.example.characters.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.characters.databinding.FragmentUserDetailsBinding
import com.example.characters.model.UserDetails
import com.example.characters.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class UserDetailsFragment : Fragment() {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val args by navArgs<UserDetailsFragmentArgs>()
    private var retrofitServiceDetails: Call<UserDetails>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentUserDetailsBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDetailsUser(args.userId)
    }


    private fun loadingDetailsUser(id: Long) {
        retrofitServiceDetails = RetrofitService.loadingRetrofitService().getDetailsUser(id)
            retrofitServiceDetails?.enqueue(object : Callback<UserDetails> {
                override fun onResponse(
                    call: Call<UserDetails>,
                    response: Response<UserDetails>
                ) {
                    if(response.isSuccessful){
                        val detailsUser = response.body() ?: return
                        binding.nameDetails.text = detailsUser.name
                        binding.userPhotoDetails.load(detailsUser.userPhoto[0])
                    } else {
                        HttpException(response).message()
                    }
                    retrofitServiceDetails = null
                }

                override fun onFailure(call: Call<UserDetails>, t: Throwable) {
                    Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG)
                        .show()
                    retrofitServiceDetails = null
                }

            })
    }


    override fun onDestroyView() {
        retrofitServiceDetails?.cancel()
        _binding = null
        super.onDestroyView()
    }
}
