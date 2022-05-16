package com.example.characters.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.characters.databinding.FragmentUserDetailsBinding
import com.example.characters.retrofit.ServiceLocator
import kotlinx.coroutines.launch

class UserDetailsFragment : Fragment() {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val args by navArgs<UserDetailsFragmentArgs>()
    private val userRepositoryDetails by lazy {
        ServiceLocator.provideRepository()
    }

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

        addCustomToolbar(args.userName)

        viewLifecycleOwner.lifecycleScope.launch {
            kotlin.runCatching { userRepositoryDetails.getDetailsUser(args.userId) }
                .fold(
                    onSuccess = {
                        val user = userRepositoryDetails.getDetailsUser(args.userId)
                        binding.nameDetails.text = user.name
                        binding.userPhotoDetails.load(user.userPhoto[0])
                        binding.pageHttp.text = user.pageHttp
                    },
                    onFailure = {
                        AlertDialog.Builder(requireContext())
                            .setMessage("Нет подключения к Интернету")
                            .setCancelable(false)
                            .setPositiveButton("OK") { _, _ -> findNavController().navigateUp() }
                            .show()
                    }
                )
        }
    }

    private fun addCustomToolbar(name: String) {
        with(binding) {
            toolbarDetails.title = name
            toolbarDetails.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}