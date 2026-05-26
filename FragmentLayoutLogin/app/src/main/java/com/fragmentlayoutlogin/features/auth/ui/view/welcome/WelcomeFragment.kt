package com.fragmentlayoutlogin.features.auth.ui.view.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fragmentlayoutlogin.features.auth.ui.view.signup.CreateUserFragment
import com.fragmentlayoutlogin.R
import com.fragmentlayoutlogin.databinding.FragmentWelcomeBinding
import com.fragmentlayoutlogin.features.auth.ui.view.login.LoginFragment

class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createAccount.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer, CreateUserFragment())
                .addToBackStack("register")
                .commit()
        }

        binding.goSignIn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer, LoginFragment())
                .addToBackStack("login")
                .commit()
        }
    }

    // ③ Hủy binding để tránh memory leak
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}