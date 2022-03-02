package com.xelar.legayd.myfirstgit.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xelar.legayd.myfirstgit.R
import com.xelar.legayd.myfirstgit.databinding.FragmentCongratulationItemBinding
import com.xelar.legayd.myfirstgit.domain.Congratulation
import java.lang.RuntimeException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM = "id"

class CongratulationItemFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param: Int? = null
    private var _binding: FragmentCongratulationItemBinding? = null
    private val binding: FragmentCongratulationItemBinding
        get() = _binding ?: throw RuntimeException("FragmentCongratulationItemBinding == null")

    private lateinit var viewModel: CongratulationItemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getInt(ARG_PARAM)
        }
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
        viewModel = ViewModelProvider(this)[CongratulationItemViewModel::class.java]

        if (param != null) {
            val id = param as Int
            viewModel.getCongratulationItem(id).observe(viewLifecycleOwner) {
                showData(it)
            }
        } else throw RuntimeException("Нет данных с интента по требуемому ключу")
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

    companion object {
        @JvmStatic
        fun newInstance(congratulationId: Int) =
            CongratulationItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM, congratulationId)
                }
            }
    }
}