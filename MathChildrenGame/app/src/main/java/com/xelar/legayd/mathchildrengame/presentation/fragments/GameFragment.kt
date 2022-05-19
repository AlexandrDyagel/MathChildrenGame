package com.xelar.legayd.mathchildrengame.presentation.fragments

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipDescription
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.xelar.legayd.mathchildrengame.R
import com.xelar.legayd.mathchildrengame.data.entities.GameCustomSettings
import com.xelar.legayd.mathchildrengame.databinding.FragmentGameBinding
import com.xelar.legayd.mathchildrengame.domain.models.GameResult
import com.xelar.legayd.mathchildrengame.domain.models.Level
import com.xelar.legayd.mathchildrengame.presentation.viewmodels.GameViewModel
import com.xelar.legayd.mathchildrengame.utils.BounceInterpolator


class GameFragment : Fragment() {

    private val args by navArgs<GameFragmentArgs>()

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[GameViewModel::class.java]
    }

    private val tvOptions by lazy {
        mutableListOf<TextView>().apply {
            add(binding.tvOption1)
            add(binding.tvOption2)
            add(binding.tvOption3)
            add(binding.tvOption4)
            add(binding.tvOption5)
            add(binding.tvOption6)
        }
    }

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")

    private var colorsBgAnswers = mutableListOf<Int>()

    private var defColor: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        colorsBgAnswers.addAll(getColorBackgroundAnswers())
        colorsBgAnswers.shuffle()
        observeViewModel()
        startGame()
        setTouchListenersOptions()
        setOnDragListenerQuestion()
    }

    private fun startGame() {
        when (args.level) {
            Level.CUSTOM -> viewModel.startGame(args.gameCustomSettings as GameCustomSettings)
            else -> {
                viewModel.startGame(args.level)
            }
        }
    }

    private fun setTouchListenersOptions() {
        for (tvOption in tvOptions) {
            touchOptionListener(tvOption)
            /* tvOption.setOnClickListener {
                 startAnimationTvQuestion(it as TextView)
                 viewModel.chooseAnswer(tvOption.text.toString().toInt())
                 setColorBackgroundAnswers()
             }*/
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun touchOptionListener(tvOption: TextView) {
        tvOption.setOnTouchListener{ view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    dragAndDropOption(tvOption, view)
                }
            }
            true
        }
    }

    private fun dragAndDropOption(tvOption: TextView, view: View) {
        val clipText = tvOption.text
        val item = ClipData.Item(clipText)
        val mimiTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val data = ClipData(clipText, mimiTypes, item)

        val dragShadowBuilder = View.DragShadowBuilder(view)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.startDragAndDrop(data, dragShadowBuilder, view, 0)
        } else {
            view.startDrag(data, dragShadowBuilder, view, 0)
        }
        view.visibility = View.INVISIBLE
    }

    private fun setOnDragListenerQuestion() {
        val dragListener = View.OnDragListener { view, dragEvent ->

            when (dragEvent.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    Log.d("userInfo", "ACTION_DRAG_STARTED")
                    if (dragEvent.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        val viewOption = dragEvent.localState as TextView
                        val optionDrawable = viewOption.background as ColorDrawable
                        val color = optionDrawable.color
                        view.setBackgroundColor(color)
                        enteredAnimationTvQuestion(view as TextView)
                    }
                    true
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    Log.d("userInfo", "ACTION_DRAG_ENTERED")

                    view.invalidate()
                    true
                }
                DragEvent.ACTION_DRAG_LOCATION -> {
                    Log.d("userInfo", "ACTION_DRAG_LOCATION")
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    Log.d("userInfo", "ACTION_DRAG_EXITED")
                    clearAnimationTvQuestion(view as TextView)
                    view.invalidate()
                    true
                }
                DragEvent.ACTION_DROP -> {
                    Log.d("userInfo", "ACTION_DROP")

                    val item = dragEvent.clipData.getItemAt(0)
                    val dragData = item.text

                    viewModel.chooseAnswer(dragData.toString().toInt())
                    setColorBackgroundAnswers()
                    view.invalidate()

                    /*val v = dragEvent.localState as TextView
                    val owner = v.parent as ViewGroup
                    owner.removeView(v)*/

                    val v = dragEvent.localState as TextView
                    /*val destination = view as ConstraintLayout
                    destination.addView(v)*/
                    v.visibility = View.VISIBLE
                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    Log.d("userInfo", "ACTION_DRAG_ENDED")
                    clearAnimationTvQuestion(view as TextView)
                    if (defColor != null){
                        view.setBackgroundColor(defColor as Int)}
                    val v = dragEvent.localState as TextView
                    v.visibility = View.VISIBLE
                    view.invalidate()
                    true
                }
                else -> false
            }
        }
        binding.tvQuestion.setOnDragListener(dragListener)
    }

    private fun observeViewModel() {
        viewModel.question.observe(viewLifecycleOwner) {
            defColor = (binding.tvQuestion.background as ColorDrawable).color
            binding.tvSum.text = it.sum.toString()
            binding.tvLeftNumber.text = it.visibleNumber.toString()
            for (i in 0 until tvOptions.size) {
                tvOptions[i].text = it.options[i].toString()
            }
        }
        viewModel.percentOfRightAnswers.observe(viewLifecycleOwner) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.progressBar.setProgress(it, true)
            } else {
                binding.progressBar.progress = it
            }
        }
        viewModel.enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.tvAnswersProgress.setTextColor(color)
        }
        viewModel.enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
            val color = getColorByState(it)
            binding.progressBar.progressTintList = ColorStateList.valueOf(color)
        }
        viewModel.formattedTime.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it
        }
        viewModel.minPercent.observe(viewLifecycleOwner) {
            binding.progressBar.secondaryProgress = it
        }
        viewModel.gameResult.observe(viewLifecycleOwner) {
            launchGameFinishedFragment(it)
        }
        viewModel.progressAnswers.observe(viewLifecycleOwner) {
            binding.tvAnswersProgress.text = it
        }
        viewModel.checkRightAnswers.observe(viewLifecycleOwner) {
            Log.d("checkRightAnswers", it.toString())
            val color = getColorByState(it)
            binding.tvQuestion.setBackgroundColor(color)
            startAnimationTvQuestion(binding.tvQuestion)
        }
    }

    private fun startAnimationTvQuestion(tv: TextView) {
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.box_answers)
        val interpolator = BounceInterpolator(0.4, 20.0)
        anim.interpolator = interpolator
        tv.startAnimation(anim)
    }

    private fun enteredAnimationTvQuestion(tv: TextView) {
        val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.enter_box_question)
        tv.startAnimation(anim)
    }

    private fun clearAnimationTvQuestion(tv: TextView) {
        tv.clearAnimation()
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if (goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
    }

    private fun setColorBackgroundAnswers() {
        binding.apply {
            colorsBgAnswers.shuffle()
            for ((index, tvOption) in tvOptions.withIndex()) {
                tvOption.setBackgroundColor(colorsBgAnswers[index])
            }
        }
    }

    private fun getColorBackgroundAnswers(): List<Int> {
        val colorsRes: TypedArray = resources.obtainTypedArray(R.array.list_color_bg_answers)
        val colors = mutableListOf<Int>()
        for (i in 0 until colorsRes.length()) {
            colors.add(colorsRes.getColor(i, 0))
        }
        colorsRes.recycle()
        return colors
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToGameFinishedFragment(
                gameResult
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}