package com.xelar.legayd.myfirstgit.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.xelar.legayd.myfirstgit.databinding.FragmentCongratulationItemBinding
import com.xelar.legayd.myfirstgit.domain.Congratulation

class CongratulationItemFragment : Fragment() {

    private val args by navArgs<CongratulationItemFragmentArgs>()

    private var _binding: FragmentCongratulationItemBinding? = null
    private val binding: FragmentCongratulationItemBinding
        get() = _binding ?: throw RuntimeException("FragmentCongratulationItemBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[CongratulationItemViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCongratulationItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCongratulationItem(args.congratulationId).observe(viewLifecycleOwner) {
            showData(it)
        }
    }

    private fun showData(congratulation: Congratulation) {
        with(binding) {
            etMessage.setText(congratulation.message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}