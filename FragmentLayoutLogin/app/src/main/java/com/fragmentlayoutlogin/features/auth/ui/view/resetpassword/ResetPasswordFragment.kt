package com.fragmentlayoutlogin.features.auth.ui.view.resetpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fragmentlayoutlogin.R
import com.fragmentlayoutlogin.core.network.RetrofitClient.retrofit
import com.fragmentlayoutlogin.core.ui.UiState
import com.fragmentlayoutlogin.databinding.FragmentResetPasswordBinding
import com.fragmentlayoutlogin.features.auth.data.api.AuthService
import com.fragmentlayoutlogin.features.auth.data.repository.AuthRepository
import com.fragmentlayoutlogin.features.auth.ui.view.AuthSharedViewModel
import com.fragmentlayoutlogin.features.auth.ui.view.AuthVMFactory
import com.fragmentlayoutlogin.features.auth.ui.view.login.LoginFragment
import com.fragmentlayoutlogin.features.auth.ui.view.passwordchanged.PasswordChangedFragment
import com.fragmentlayoutlogin.features.auth.ui.view.verifyemail.VerifyEmailViewModel
import kotlinx.coroutines.launch
import kotlin.getValue

class ResetPasswordFragment : Fragment() {
    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!
    private val viewModels by viewModels<ResetPasswordViewModel> {
        AuthVMFactory(AuthRepository(retrofit.create(AuthService::class.java)))
    }
    private val sharedVM : AuthSharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goSignIn.setOnClickListener {
            binding.goSignIn.setOnClickListener {
                parentFragmentManager.popBackStack("login", 0)
            }
        }

        binding.goPasswordChanged.setOnClickListener {
            val password = binding.passwordET.text.toString()
            val confirmPassword = binding.confirmPasswordET.text.toString()
            viewModels.resetPassword(sharedVM.email.value, sharedVM.otp.value, password, confirmPassword)
            sharedVM.clear()
        }

        lifecycleScope.launch {
            viewModels.resetPasswordResult.collect { state ->
                when (state) {
                    is UiState.Idle -> binding.tvResult.text = ""
                    is UiState.Loading -> binding.tvResult.text = "Loading..."
                    is UiState.Success -> {
                        binding.tvResult.text = state.data.message

                        parentFragmentManager.popBackStack("login", 0)
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.FragmentContainer, PasswordChangedFragment())
                            .addToBackStack("passwordChanged")
                            .commit()
                    }
                    is UiState.Error -> {
                        binding.tvResult.text = "Error: ${state.message}"
                        binding.passwordET.text?.clear()
                        binding.confirmPasswordET.text?.clear()
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