package com.xelar.legayd.mathchildrengame.presentation.onboarding.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.xelar.legayd.mathchildrengame.R
import com.xelar.legayd.mathchildrengame.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding: FragmentThirdBinding
        get() = _binding ?: throw RuntimeException("FragmentThirdBinding == null")

    private var imageAnim: Animation? = null
    private var titleAnim: Animation? = null
    private var descriptionAnim: Animation? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)

        binding.tvFinish.setOnClickListener {
            launchChoseLevelFragment()
            onBoardingFinished()
        }
        setAnimationElements()
        return binding.root
    }

    private fun launchChoseLevelFragment() {
        findNavController().navigate(R.id.action_viewPagerFragment_to_chooseLevelFragment)
    }

    private fun onBoardingFinished() {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

    private fun setAnimationElements() {
        imageAnim = AnimationUtils.loadAnimation(context, R.anim.top_anim_logo_onboard_screen)
        titleAnim = AnimationUtils.loadAnimation(context, R.anim.scale_in_content_onboard_screen)
        descriptionAnim =
            AnimationUtils.loadAnimation(context, R.anim.scale_in_content_onboard_screen)
    }

    private fun startAnimationElements() {
        with(binding) {
            imgLogo.startAnimation(imageAnim)
            tvTitle.startAnimation(titleAnim)
            tvDescription.startAnimation(descriptionAnim)
        }
    }

    private fun setVisibilityVisibleElements() {
        with(binding) {
            imgLogo.visibility = View.VISIBLE
            tvTitle.visibility = View.VISIBLE
            tvDescription.visibility = View.VISIBLE
        }
    }

    private fun setVisibilityGoneElements() {
        with(binding) {
            imgLogo.visibility = View.GONE
            tvTitle.visibility = View.GONE
            tvDescription.visibility = View.GONE
        }
    }

    override fun onPause() {
        super.onPause()
        setVisibilityGoneElements()
    }

    override fun onResume() {
        super.onResume()
        startAnimationElements()
        setVisibilityVisibleElements()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}