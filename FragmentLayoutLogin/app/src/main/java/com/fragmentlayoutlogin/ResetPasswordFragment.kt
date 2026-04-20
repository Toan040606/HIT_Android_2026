package com.fragmentlayoutlogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fragmentlayoutlogin.databinding.FragmentForgotPasswordBinding
import com.fragmentlayoutlogin.databinding.FragmentResetPasswordBinding

class ResetPasswordFragment : Fragment() {
    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!


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
                parentFragmentManager.beginTransaction()
                    .replace(R.id.FragmentContainer, LoginFragment())
                    .commit()
            }
        }

        binding.goPasswordChanged.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer, PasswordChangedFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}