package com.fragmentlayoutlogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fragmentlayoutlogin.databinding.FragmentForgotPasswordBinding
import com.fragmentlayoutlogin.databinding.FragmentVerifyEmailBinding

class VerifyEmailFragment : Fragment() {
    private var _binding: FragmentVerifyEmailBinding? = null
    private val binding get() = _binding!!


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
            parentFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer, LoginFragment())
                .commit()
        }

        binding.goResetPassword.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer, ResetPasswordFragment())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}