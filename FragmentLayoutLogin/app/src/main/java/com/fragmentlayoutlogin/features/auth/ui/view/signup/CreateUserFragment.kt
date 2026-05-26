package com.fragmentlayoutlogin.features.auth.ui.view.signup

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
import com.fragmentlayoutlogin.databinding.FragmentCreateBinding
import com.fragmentlayoutlogin.features.auth.data.api.AuthService
import com.fragmentlayoutlogin.features.auth.data.repository.AuthRepository
import com.fragmentlayoutlogin.features.auth.ui.view.AuthVMFactory
import com.fragmentlayoutlogin.features.auth.ui.view.login.LoginFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CreateUserFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CreateUserViewModel> {
        AuthVMFactory(AuthRepository(retrofit.create(AuthService::class.java)))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("CreateUserFragment", "back stack entry count: ${parentFragmentManager.backStackEntryCount}")

        binding.goSignIn.setOnClickListener {
            if (parentFragmentManager.backStackEntryCount == 1)
                parentFragmentManager.beginTransaction()
                    .replace(R.id.FragmentContainer, LoginFragment())
                    .addToBackStack("login")
                    .commit()
            else {
                parentFragmentManager.popBackStack("login", 0)
            }
        }

        binding.signUpBtn.setOnClickListener {
            val username = binding.usernameET.text.toString()
            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()
            val confirmPassword = binding.confirmPasswordET.text.toString()
            viewModel.register(username, email, password, confirmPassword)
        }

        lifecycleScope.launch {
            viewModel.registerResult.collect { state ->
                when (state) {
                    is UiState.Idle -> binding.tvResult.text = "Create Account"
                    is UiState.Loading -> binding.tvResult.text = "Loading..."
                    is UiState.Success -> {
                        val message = state.data.message
                        binding.tvResult.text = message

                        delay(2000)
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.FragmentContainer, LoginFragment())
                            .commit()
                    }
                    is UiState.Error -> binding.tvResult.text = "Login failed: ${state.message}"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}