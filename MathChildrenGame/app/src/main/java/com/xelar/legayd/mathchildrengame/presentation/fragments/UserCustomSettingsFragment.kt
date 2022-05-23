package com.xelar.legayd.mathchildrengame.presentation.fragments

import android.content.ClipDescription
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.xelar.legayd.mathchildrengame.data.entities.GameCustomSettings
import com.xelar.legayd.mathchildrengame.databinding.FragmentUserCustomSettingsBinding
import com.xelar.legayd.mathchildrengame.domain.models.GameMode
import com.xelar.legayd.mathchildrengame.domain.models.Level
import com.xelar.legayd.mathchildrengame.presentation.adapters.UserCustomSettingsAdapter
import com.xelar.legayd.mathchildrengame.presentation.viewmodels.UserCustomSettingsViewModel
import java.util.*

class UserCustomSettingsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[UserCustomSettingsViewModel::class.java]
    }

    private var _binding: FragmentUserCustomSettingsBinding? = null
    private val binding: FragmentUserCustomSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentUserCustomSettingsBinding == null")

    private val adapter by lazy {
        UserCustomSettingsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserCustomSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSwipeListener()

        val recyclerView = binding.recyclerListButtons
        recyclerView.adapter = adapter

        viewModel.customSettings.observe(viewLifecycleOwner) {
            checkEmptyListSettings(it)
            adapter.submitList(it)
        }
        adapter.onCustomSettingsClickListener = {
            launchGameFragment(it)
        }
    }

    private fun checkEmptyListSettings(listSettings: List<GameCustomSettings>?) {
        if (listSettings?.isEmpty() == true){
            binding.tvEmptyListSettings.visibility = View.VISIBLE
        }
    }


    private fun setupSwipeListener() {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                 viewModel.deleteCustomSettings(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerListButtons)
    }

    private fun launchGameFragment(gameCustomSettings: GameCustomSettings) {
        findNavController().navigate(
            UserCustomSettingsFragmentDirections.actionUserCustomSettingsToGameFragment(
                Level.CUSTOM,
                GameMode.CLICK,//TODO("Не забыть закодить")
                gameCustomSettings
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}