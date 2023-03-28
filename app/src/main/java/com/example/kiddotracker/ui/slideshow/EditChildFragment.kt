package com.example.kiddotracker.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kiddotracker.databinding.EditChildFragmentBinding

class SlideshowFragment : Fragment() {

    private var _binding: EditChildFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val editChildViewModel =
            ViewModelProvider(this)[EditChildViewModel::class.java]

        _binding = EditChildFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.editChildFragment
        editChildViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}