package com.xelar.legayd.mathchildrengame.presentation.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.xelar.legayd.mathchildrengame.R
import com.xelar.legayd.mathchildrengame.databinding.FragmentSecondBinding

class SecondFragment : Fragment() {

    companion object {
        private const val THIRD_SCREEN = 2
    }

    private var imageAnim: Animation? = null
    private var titleAnim: Animation? = null
    private var descriptionAnim: Animation? = null

    private var _binding: FragmentSecondBinding? = null
    private val binding: FragmentSecondBinding
        get() = _binding ?: throw RuntimeException("FragmentSecondBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
        binding.tvNext.setOnClickListener {
            viewPager.currentItem = THIRD_SCREEN
        }
        setAnimationElements()
        return binding.root
    }

    private fun setAnimationElements() {
        imageAnim = AnimationUtils.loadAnimation(context, R.anim.top_anim_logo_onboard_screen)
        titleAnim = AnimationUtils.loadAnimation(context, R.anim.right_to_left_title_onboard_screen)
        descriptionAnim =
            AnimationUtils.loadAnimation(context, R.anim.left_to_right_title_onboard_screen)
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