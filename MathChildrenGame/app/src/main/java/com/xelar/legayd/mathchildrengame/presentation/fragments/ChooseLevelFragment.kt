package com.xelar.legayd.mathchildrengame.presentation.fragments

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.xelar.legayd.mathchildrengame.R
import com.xelar.legayd.mathchildrengame.databinding.FragmentChooseLevelBinding
import com.xelar.legayd.mathchildrengame.domain.models.GameMode
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
        getGameMode()
        with(binding) {
            buttonLevelTest.setOnClickListener {
                launchGameFragment(Level.TEST, getGameMode())
            }
            buttonLevelVeryEasy.setOnClickListener {
                launchGameFragment(Level.VERY_EASY, getGameMode())
            }
            buttonLevelEasy.setOnClickListener {
                launchGameFragment(Level.EASY, getGameMode())
            }
            buttonLevelNormal.setOnClickListener {
                launchGameFragment(Level.NORMAL, getGameMode())
            }
            buttonLevelHard.setOnClickListener {
                launchGameFragment(Level.HARD, getGameMode())
            }
            buttonLevelVeryHard.setOnClickListener {
                val dialog = DialogAppEvaluationFragment()
                dialog.show(requireActivity().supportFragmentManager, "ratingDialog")
                //launchGameFragment(Level.VERY_HARD, getGameMode())
            }
            buttonLevelCustom.setOnClickListener {
                launchCustomSettingsFragment()
            }
        }
    }

    private fun setColorTextViewGameMode(gameMode: GameMode, defaultColor: ColorStateList) {
        when (gameMode) {
            GameMode.CLICK -> {
                binding.tvClickMode.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.tvGameModeClick
                    )
                )
                binding.tvSwipeMode.setTextColor(defaultColor)
            }
            GameMode.SWIPE -> {
                binding.tvSwipeMode.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.tvGameModeSwipe
                    )
                )
                binding.tvClickMode.setTextColor(defaultColor)
            }
        }
    }

    private fun getGameMode(): GameMode {
        val defaultColor = binding.tvSwipeMode.textColors
        setColorTextViewGameMode(GameMode.CLICK, defaultColor)
        binding.switchMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                setColorTextViewGameMode(GameMode.SWIPE, defaultColor)
            } else {
                setColorTextViewGameMode(GameMode.CLICK, defaultColor)
            }
        }
        return if (binding.switchMode.isChecked) GameMode.SWIPE else GameMode.CLICK
    }

    private fun setBottomSheet() {
        bottomSheetBehavior =
            BottomSheetBehavior.from(binding.incBottomSheet.bottomSheetDescRewards)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
    }

    private fun launchGameFragment(level: Level, gameMode: GameMode) {
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(
                level,
                gameMode,
                null
            )
        )
    }

    private fun launchCustomSettingsFragment() {
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToCustomSettingsFragment()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}