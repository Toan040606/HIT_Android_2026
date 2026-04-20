package com.fragmentlayoutlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fragmentlayoutlogin.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


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
            parentFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer, CreateFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.goForgotPassword.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer, ForgotPasswordFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    // ③ Hủy binding để tránh memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}