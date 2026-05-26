package com.fragmentlayoutlogin.features.auth.ui.view.verifyemail

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fragmentlayoutlogin.R
import com.fragmentlayoutlogin.core.network.RetrofitClient.retrofit
import com.fragmentlayoutlogin.core.ui.UiState
import com.fragmentlayoutlogin.databinding.FragmentVerifyEmailBinding
import com.fragmentlayoutlogin.features.auth.data.api.AuthService
import com.fragmentlayoutlogin.features.auth.data.repository.AuthRepository
import com.fragmentlayoutlogin.features.auth.ui.view.AuthSharedViewModel
import com.fragmentlayoutlogin.features.auth.ui.view.AuthVMFactory
import com.fragmentlayoutlogin.features.auth.ui.view.login.LoginFragment
import com.fragmentlayoutlogin.features.auth.ui.view.resetpassword.ResetPasswordFragment
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.security.Key

class VerifyEmailFragment : Fragment() {
    private var _binding: FragmentVerifyEmailBinding? = null
    private val binding get() = _binding!!

    private val viewModels by viewModels<VerifyEmailViewModel> {
        AuthVMFactory(AuthRepository(retrofit.create(AuthService::class.java)))
    }
    private val sharedVM: AuthSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVerifyEmailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.goSignIn.setOnClickListener {
            parentFragmentManager.popBackStack("login", 0)
        }

        val email = sharedVM.email.value
        binding.emailForgotPassword.text = email

        setupOtpInputs()

        binding.goResetPassword.setOnClickListener {
            val otp = binding.otp1.text.toString() +
                    binding.otp2.text.toString() +
                    binding.otp3.text.toString() +
                    binding.otp4.text.toString()
            viewModels.verifyEmail(email, otp, sharedVM.otp.value)
        }

        lifecycleScope.launch {
            viewModels.verifyEmailResult.collect { state ->
                when (state) {
                    is UiState.Loading -> binding.tvResult.text = "Loading..."
                    is UiState.Idle -> binding.tvResult.text = ""
                    is UiState.Success -> {
                        binding.tvResult.text = state.data.message

                        parentFragmentManager.beginTransaction()
                            .replace(R.id.FragmentContainer, ResetPasswordFragment())
                            .addToBackStack("resetPassword")
                            .commit()
                    }
                    is UiState.Error -> {
                        binding.tvResult.text = state.message
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun moveToNext(current: TextInputEditText, next: TextInputEditText) {
        current.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    next.requestFocus()
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun moveToPrevious(current: TextInputEditText, previous: TextInputEditText) {
        current.setOnKeyListener { _, keyCode, event ->
            if (
                keyCode == KeyEvent.KEYCODE_DEL &&
                event.action == KeyEvent.ACTION_DOWN &&
                current.length() <= 1
            ) {
                current.text?.clear()
                previous.requestFocus()
                true
            } else {
                false
            }
        }
    }

    private fun setupOtpInputs() {
        moveToNext(binding.otp1, binding.otp2)
        moveToNext(binding.otp2, binding.otp3)
        moveToNext(binding.otp3, binding.otp4)

        moveToPrevious(binding.otp2, binding.otp1)
        moveToPrevious(binding.otp3, binding.otp2)
        moveToPrevious(binding.otp4, binding.otp3)
    }
}