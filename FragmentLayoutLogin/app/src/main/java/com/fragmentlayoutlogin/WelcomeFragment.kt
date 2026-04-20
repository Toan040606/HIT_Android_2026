package com.fragmentlayoutlogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fragmentlayoutlogin.databinding.FragmentWelcomeBinding

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
                .replace(R.id.FragmentContainer, CreateFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.goSignIn.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer, LoginFragment())
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