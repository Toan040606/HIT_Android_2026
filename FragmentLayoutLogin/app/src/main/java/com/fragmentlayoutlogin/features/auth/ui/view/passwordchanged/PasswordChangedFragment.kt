package com.fragmentlayoutlogin.features.auth.ui.view.passwordchanged

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.fragmentlayoutlogin.databinding.FragmentPasswordChangedBinding

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

        for (i in 0 until parentFragmentManager.backStackEntryCount) {
            val entry = parentFragmentManager.getBackStackEntryAt(i)
            Log.d("BACKSTACK", "Index: $i, Name: ${entry.name}")
        }

        binding.goSignIn.setOnClickListener {
            parentFragmentManager.popBackStack("login", 0)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}