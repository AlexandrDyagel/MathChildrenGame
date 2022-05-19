package com.xelar.legayd.mathchildrengame.presentation.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.xelar.legayd.mathchildrengame.R
import com.xelar.legayd.mathchildrengame.databinding.FragmentChooseLevelBinding
import com.xelar.legayd.mathchildrengame.domain.models.Level
import com.xelar.legayd.mathchildrengame.presentation.dialogs.DialogAppEvaluationFragment

class ChooseLevelFragment : Fragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private var _binding: FragmentChooseLevelBinding? = null
    private val binding: FragmentChooseLevelBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseLevelBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomSheet()
        with(binding) {
            buttonLevelTest.setOnClickListener {
                launchGameFragment(Level.TEST)
            }
            buttonLevelVeryEasy.setOnClickListener {
                launchGameFragment(Level.VERY_EASY)
            }
            buttonLevelEasy.setOnClickListener {
                launchGameFragment(Level.EASY)
            }
            buttonLevelNormal.setOnClickListener {
                launchGameFragment(Level.NORMAL)
            }
            buttonLevelHard.setOnClickListener {
                launchGameFragment(Level.HARD)
            }
            buttonLevelVeryHard.setOnClickListener {
               val dialog = DialogAppEvaluationFragment()
                dialog.show(requireActivity().supportFragmentManager, "ratingDialog")
                //launchGameFragment(Level.VERY_HARD)
            }
            buttonLevelCustom.setOnClickListener {
                launchCustomSettingsFragment()
            }
        }
    }

    private fun setBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.incBottomSheet.bottomSheetDescRewards)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN){
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
    }

    private fun launchGameFragment(level: Level) {
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level, null)
        )
    }

    private fun launchCustomSettingsFragment(){
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToCustomSettingsFragment()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}