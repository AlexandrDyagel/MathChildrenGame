package com.xelar.legayd.mathchildrengame.presentation.onboarding

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.xelar.legayd.mathchildrengame.databinding.FragmentViewPagerBinding
import com.xelar.legayd.mathchildrengame.presentation.onboarding.screens.FirstFragment
import com.xelar.legayd.mathchildrengame.presentation.onboarding.screens.SecondFragment
import com.xelar.legayd.mathchildrengame.presentation.onboarding.screens.ThirdFragment
import com.xelar.legayd.mathchildrengame.utils.AdsApp

class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding: FragmentViewPagerBinding
        get() = _binding ?: throw RuntimeException("FragmentViewPagerBinding == null")

    private var adsApp: AdsApp? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        val fragmentList = arrayListOf(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        with(binding) {
            viewPager.adapter = adapter
            indicator.setViewPager(viewPager)
        }
        return binding.root
    }

    private fun initAdsApp() {
        adsApp = AdsApp(requireContext(), binding.adView)
        adsApp?.loadAd()
    }

    private fun setFullScreen(activity: Activity) {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            activity.window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            activity.window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setFullScreen(requireActivity())
    }

    override fun onStart() {
        super.onStart()
        initAdsApp()
    }

    override fun onResume() {
        super.onResume()
        adsApp?.resume()
    }

    override fun onPause() {
        super.onPause()
        adsApp?.pause()
    }

    override fun onDestroy() {
        adsApp?.destroy()
        super.onDestroy()
        _binding = null
    }
}