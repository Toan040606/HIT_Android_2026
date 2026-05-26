package com.fragmentlayoutlogin.features.auth.ui.view.forgotpassword

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fragmentlayoutlogin.R
import com.fragmentlayoutlogin.core.network.RetrofitClient.retrofit
import com.fragmentlayoutlogin.core.ui.UiState
import com.fragmentlayoutlogin.databinding.FragmentForgotPasswordBinding
import com.fragmentlayoutlogin.features.auth.data.api.AuthService
import com.fragmentlayoutlogin.features.auth.data.repository.AuthRepository
import com.fragmentlayoutlogin.features.auth.ui.view.AuthSharedViewModel
import com.fragmentlayoutlogin.features.auth.ui.view.AuthVMFactory
import com.fragmentlayoutlogin.features.auth.ui.view.verifyemail.VerifyEmailFragment
import kotlinx.coroutines.launch

class ForgotPasswordFragment : Fragment() {
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ForgotPasswordViewModel> {
        AuthVMFactory(AuthRepository(retrofit.create(AuthService::class.java)))
    }
    private val sharedVM: AuthSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goSignIn.setOnClickListener {
            parentFragmentManager.popBackStack("login", 0)
        }

        binding.goVerifyEmail.setOnClickListener {
            val email = binding.emailET.text.toString()
            viewModel.forgotPassword(email)
            sharedVM.setEmail(email)
        }

        lifecycleScope.launch {
            viewModel.forgotPasswordResult.collect { state ->
                when (state) {
                    is UiState.Idle -> binding.tvResult.text = ""
                    is UiState.Loading -> binding.tvResult.text = "Loading..."
                    is UiState.Success -> {
                        val message = state.data.message
                        binding.tvResult.text = message
                        sharedVM.setOtp(state.data.data.otp.toString())
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.FragmentContainer, VerifyEmailFragment())
                            .addToBackStack("verifyEmail")
                            .commit()
                    }

                    is UiState.Error -> {
                        binding.tvResult.text = "Error: ${state.message}"
                    }
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}