package com.fragmentlayoutlogin.features.auth.ui.view.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fragmentlayoutlogin.R
import com.fragmentlayoutlogin.core.network.RetrofitClient.retrofit
import com.fragmentlayoutlogin.core.ui.UiState
import com.fragmentlayoutlogin.databinding.FragmentLoginBinding
import com.fragmentlayoutlogin.features.auth.data.api.AuthService
import com.fragmentlayoutlogin.features.auth.data.repository.AuthRepository
import com.fragmentlayoutlogin.features.auth.ui.view.AuthVMFactory
import com.fragmentlayoutlogin.features.auth.ui.view.forgotpassword.ForgotPasswordFragment
import com.fragmentlayoutlogin.features.auth.ui.view.signup.CreateUserFragment
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<LoginViewModel> {
        AuthVMFactory(AuthRepository(retrofit.create(AuthService::class.java)))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createAccount.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount == 1) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.FragmentContainer, CreateUserFragment())
                    .addToBackStack("register")
                    .commit()
            } else {
                parentFragmentManager.popBackStack("register", 0)
            }
        }

        binding.goForgotPassword.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer, ForgotPasswordFragment())
                .addToBackStack("forgotPassword")
                .commit()
        }

        binding.signInBtn.setOnClickListener {
            val email = binding.usernameET.text.toString()
            val password = binding.passwordETs.text.toString()
            viewModel.login(email, password)
        }

        lifecycleScope.launch {
            viewModel.loginResult.collect { state ->
                when (state) {
                    is UiState.Idle -> binding.tvResult.text = "Hi, Wellcome!"
                    is UiState.Loading -> binding.tvResult.text = "Loading..."
                    is UiState.Success -> {
                        val message = state.data.message
                        binding.tvResult.text = message
                    }
                    is UiState.Error -> binding.tvResult.text = "Login failed: ${state.message}"
                }
            }
        }
    }

    // ③ Hủy binding để tránh memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}