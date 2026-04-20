package com.fragmentlayoutlogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fragmentlayoutlogin.databinding.FragmentPasswordChangedBinding
import com.fragmentlayoutlogin.databinding.FragmentResetPasswordBinding

class PasswordChangedFragment : Fragment() {
    private var _binding: FragmentPasswordChangedBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordChangedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goSignIn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer, LoginFragment())
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}