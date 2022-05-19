package com.xelar.legayd.mathchildrengame.presentation.activities

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.xelar.legayd.mathchildrengame.R
import com.xelar.legayd.mathchildrengame.presentation.viewmodels.SplashScreenViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[SplashScreenViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MathChildrenGame)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.destination(onBoardingFinished())
        viewModel.state.observe(this) {
            if (it) {
                launchChooseLevelFragment()
            }
        }
    }

    private fun launchChooseLevelFragment() {
        findNavController(R.id.mainContainer).navigate(R.id.action_viewPagerFragment_to_chooseLevelFragment)
    }

    private fun onBoardingFinished(): Boolean {
        val sharedPref = getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }
}