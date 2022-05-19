package com.xelar.legayd.mathchildrengame.presentation.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.xelar.legayd.mathchildrengame.R
import com.xelar.legayd.mathchildrengame.databinding.FragmentGameFinishedBinding
import com.xelar.legayd.mathchildrengame.presentation.viewmodels.GameFinishedViewModel
import com.xelar.legayd.mathchildrengame.utils.GameResultImage
import com.xelar.legayd.mathchildrengame.utils.Screenshot
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.core.models.Size
import java.util.concurrent.TimeUnit


class GameFinishedFragment : Fragment() {

    companion object {
        private const val BACKGROUND_DIMMING_VALUE = 0.8F
    }

    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameFinishedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataBottomSheet()
        setColorBackgroundBody(args.gameResult.winner)
        setupClickListeners()
        bindViews()
        setColorVariableResult()
        emmetKonfetti(args.gameResult.winner)
        setBottomSheet()
        stateButtonRetry()
        viewModel.setGameResult(args.gameResult)
        //animationElementScreen(requireContext())
    }

    private fun stateButtonRetry() {
        viewModel.activeButtonRetry.observe(viewLifecycleOwner) {
            binding.buttonRetry.isEnabled = it
            if (it) {
                binding.buttonRetry.text = resources.getString(R.string.button_retry)
            }
        }
        viewModel.tickTimerButtonRetry.observe(viewLifecycleOwner) {
            binding.buttonRetry.text = it.toString()
            binding.buttonRetry.isEnabled = false
        }
    }

    private fun setProgressBarExp() {
        viewModel.progressBarExp.observe(viewLifecycleOwner) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.incBottomSheetRating.progressBarExp.setProgress(it.toInt(), true)
            } else {
                binding.incBottomSheetRating.progressBarExp.progress = it.toInt()
            }
        }
    }

    private fun setDataBottomSheet() {
        viewModel.userInfo.observe(viewLifecycleOwner) {
            if (it == null) {
                Log.d("userInfo", "it == null")
            } else {
                with(binding.incBottomSheetRating) {
                    tvCountStars.text = it.rating.toString()
                    tvStatus.text = it.statusSettings.statusName
                    tvCountNextStars.text = String.format(
                        resources.getString(R.string.bottom_sheet_schema_status),
                        it.statusSettings.totalRatingsStatus,
                        it.statusSettings.totalRatingNextStatus
                    )
                    tvCountRightAnswers.text = it.countOfRightAnswers.toString()
                    imageStatus.setImageResource(it.statusSettings.statusImage)
                }
            }
        }
    }

    private fun bindViews() {
        with(binding) {
            imageResult.setImageResource(getSmileResId())
            tvRequiredAnswers.text = String.format(
                getString(R.string.required_answers),
                args.gameResult.gameSettings.minCountOfRightAnswers
            )
            tvScoreAnswers.text = String.format(
                getString(R.string.score_answers),
                args.gameResult.countOfRightAnswers
            )
            tvRequiredPercentage.text = String.format(
                getString(R.string.required_percentage),
                args.gameResult.gameSettings.minPercentOfRightAnswers
            )
            tvScorePercentage.text = String.format(
                getString(R.string.score_percentage),
                getPercentOfRightAnswers()
            )
        }
    }

    private fun setColorVariableResult() {
        with(binding) {
            tvScoreAnswers.setTextColor(
                getColorText(
                    args.gameResult.countOfRightAnswers,
                    args.gameResult.gameSettings.minCountOfRightAnswers
                )
            )
            tvScorePercentage.setTextColor(
                getColorText(
                    getPercentOfRightAnswers(),
                    args.gameResult.gameSettings.minPercentOfRightAnswers
                )
            )
        }
    }

    private fun setColorBackgroundBody(win: Boolean) {
        with(binding) {
            if (win) {
                clBody.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.bg_finished_game_win)
                )
            } else {
                clBody.setBackgroundColor(
                    ContextCompat.getColor(requireContext(), R.color.bg_finished_game_los)
                )
            }
        }
    }

    private fun getSmileResId() = if (args.gameResult.winner) {
        GameResultImage.generateWinnerImage()
    } else {
        GameResultImage.generateLoserImage()
    }

    private fun getPercentOfRightAnswers() = with(args.gameResult) {
        if (countOfQuestions == 0) {
            0
        } else {
            ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
        }
    }

    private fun getColorText(curr: Int, set: Int): Int {
        val colorResId = if (curr >= set) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    private fun setupClickListeners() {
        with(binding) {
            buttonRetry.setOnClickListener {
                retryGame()
            }
            tvShowBottomSheet.setOnClickListener {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            viewBg.setOnClickListener {
                if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
            incBottomSheetRating.buttonShare.setOnClickListener {
                incBottomSheetRating.sectionBadge.visibility = View.VISIBLE
                incBottomSheetRating.bottomSheetRating.post {
                    view?.requestLayout()
                    view?.invalidate()
                    val screenshot = Screenshot(requireContext())
                    screenshot.sharePicture(
                        "rating",
                        incBottomSheetRating.ratingContent,
                        incBottomSheetRating.sectionBadge
                    )
                }
            }
        }
    }

    private fun checkPermission(activity: Activity): Boolean {
        val result = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
        if (result) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), PackageManager.PERMISSION_GRANTED
            )
        }
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
    }

    private fun emmetKonfetti(win: Boolean) {
        if (win) {
            val listColors = getColorsArray()
            val party = Party(
                speed = 0f,
                maxSpeed = 30f,
                damping = 0.9f,
                spread = 360,
                timeToLive = 3000,
                delay = 200,
                size = listOf(Size.LARGE, Size.MEDIUM, Size.SMALL),
                colors = listColors,
                emitter = Emitter(duration = 500, TimeUnit.MILLISECONDS).max(300),
                position = Position.Relative(0.5, 0.3)
            )
            binding.konfettiView.start(party)
        }
    }

    private fun getColorsArray(): List<Int> {
        val colorsRes: TypedArray = resources.obtainTypedArray(R.array.list_color_bg_answers)
        val colors = mutableListOf<Int>()
        for (i in 0 until colorsRes.length()) {
            colors.add(colorsRes.getColor(i, 0))
        }
        colorsRes.recycle()
        return colors
    }

    private fun setBottomSheet() {
        bottomSheetBehavior =
            BottomSheetBehavior.from(binding.incBottomSheetRating.bottomSheetRating)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                /*bottomSheet.post {
                    bottomSheet.requestLayout()
                    bottomSheet.invalidate()
                }*/
                if (newState == BottomSheetBehavior.STATE_EXPANDED) { // расширен
                    binding.buttonRetry.isEnabled = false
                    setProgressBarExp()
                }
                if (newState == BottomSheetBehavior.STATE_HIDDEN) { // свернуто
                    binding.viewBg.visibility = View.GONE
                    binding.buttonRetry.isEnabled = true
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //binding.coordinatorBody.bringChildToFront(bottomSheet)
                binding.viewBg.visibility = View.VISIBLE
                if (slideOffset < BACKGROUND_DIMMING_VALUE) {
                    binding.viewBg.alpha = slideOffset
                }
            }
        })
    }

    private fun animationElementScreen(context: Context) {
        val imageAnim = AnimationUtils.loadAnimation(context, R.anim.finished_screen_top_anim_image)
        val resultLayout = AnimationUtils.loadAnimation(context, R.anim.finished_screen_bottom_anim_result_layout)
        with(binding) {
            imageResult.animation = imageAnim
            containerResult.animation = resultLayout
        }
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}