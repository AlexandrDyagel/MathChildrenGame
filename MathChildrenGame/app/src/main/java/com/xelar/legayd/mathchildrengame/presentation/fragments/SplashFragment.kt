package com.xelar.legayd.mathchildrengame.presentation.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.xelar.legayd.mathchildrengame.R
import com.xelar.legayd.mathchildrengame.databinding.FragmentSplashBinding
import java.lang.RuntimeException

class SplashFragment : Fragment() {
    companion object{
        private const val SPLASH_OUT_DURATION = 3000L
    }

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding
    get() = _binding ?: throw RuntimeException("FragmentSplashBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        animationElementScreen(requireContext())
        Handler(Looper.getMainLooper()).postDelayed({
            /*if (onBoardingFinished()){
                findNavController().navigate(R.id.action_splashFragment_to_chooseLevelFragment)
            }else {
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }*/
        }, SPLASH_OUT_DURATION)

        return binding.root
    }

    private fun animationElementScreen(context: Context) {
        val logoAnim = AnimationUtils.loadAnimation(context, R.anim.top_anim_logo_onboard_screen)
        val titleOneAnim = AnimationUtils.loadAnimation(context, R.anim.left_to_right_title_onboard_screen)
        val titleSecondAnim = AnimationUtils.loadAnimation(context, R.anim.right_to_left_title_onboard_screen)
        val titleThirdAnim = AnimationUtils.loadAnimation(context, R.anim.left_to_right_title_onboard_screen)
        with(binding){
            imgLogo.animation = logoAnim
            title1.animation = titleOneAnim
            title2.animation = titleSecondAnim
            title3.animation = titleThirdAnim
        }
    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

    private fun setFullScreenWindow(){
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setFullScreenWindow()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}