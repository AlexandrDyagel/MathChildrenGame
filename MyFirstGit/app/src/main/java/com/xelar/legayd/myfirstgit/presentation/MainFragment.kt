package com.xelar.legayd.myfirstgit.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.xelar.legayd.myfirstgit.R
import com.xelar.legayd.myfirstgit.adapters.CategoryCongratulationAdapter
import com.xelar.legayd.myfirstgit.adapters.CongratulationAdapter
import com.xelar.legayd.myfirstgit.databinding.FragmentMainBinding
import java.lang.RuntimeException

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")

    private lateinit var viewModel: CongratulationViewModel
    private val categoryAdapter = CategoryCongratulationAdapter()
    private val congratulationAdapter = CongratulationAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[CongratulationViewModel::class.java]
        initRV()

        viewModel.listCongratulation.observe(viewLifecycleOwner) {
            Log.d("TAG", it.toString())
            congratulationAdapter.submitList(it)
        }
        viewModel.listCategoryCongratulation.observe(viewLifecycleOwner) {
            Log.d("TAG", it.toString())
            categoryAdapter.listCategoryCongratulation = it
        }

        congratulationAdapter.onCongratulationClickListener =
            {
                val fragment = CongratulationItemFragment.newInstance(it.id)
                launchFragment(fragment)
            }
        categoryAdapter.onCategoryClickListener = {
            Toast.makeText(
                context,
                "Нажата категория '${it.name}'",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initRV() = with(binding) {
        rvCategoryCongratulations.adapter = categoryAdapter
        rvCongratulations.adapter = congratulationAdapter
    }

    private fun launchFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}