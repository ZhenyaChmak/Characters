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
import com.example.characters.R
import com.example.characters.databinding.FragmentUserDetailsBinding
import com.example.characters.model.UserDetailsViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserDetailsFragment : Fragment() {

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by viewModel<UserDetailsViewModel>{
        parametersOf(args.userId)
    }

    private val args by navArgs<UserDetailsFragmentArgs>()

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

       // viewModel.onLoadMoreDetails(args.userId)

        viewModel
            .dataFlow
            .onEach {
                it.fold(
                    onSuccess = { user ->
                        binding.nameDetails.text = user.name
                        binding.userPhotoDetails.load(user.userPhoto[0])
                        binding.pageHttp.text = user.pageHttp
                    },
                    onFailure = {
                        AlertDialog.Builder(requireContext())
                            .setMessage(R.string.is_no_internet)
                            .setCancelable(false)
                            .setPositiveButton(R.string.ok) { _, _ -> findNavController().navigateUp() }
                            .show()
                    }
                )
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
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