package com.xelar.legayd.mathchildrengame.presentation.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.xelar.legayd.mathchildrengame.R
import com.xelar.legayd.mathchildrengame.databinding.FragmentCustomSettingsBinding
import com.xelar.legayd.mathchildrengame.presentation.adapters.ColorDropDownAdapter
import com.xelar.legayd.mathchildrengame.presentation.viewmodels.CustomSettingsViewModel


class CustomSettingsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        )[CustomSettingsViewModel::class.java]
    }

    private var _binding: FragmentCustomSettingsBinding? = null
    private val binding: FragmentCustomSettingsBinding
        get() = _binding ?: throw RuntimeException("CustomSettingsFragment == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    private var colorBgButton: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        observerErrors()
        textChangedListeners()
        validInputFields()
        stateButtonUserCustomSettings()
        selectColorsSpinner()
    }

    private fun getColorBackgroundItems(): List<Int>{
        val colorsRes: TypedArray = resources.obtainTypedArray(R.array.list_color_bg_items)
        val colors = mutableListOf<Int>()
        for (i in 0 until colorsRes.length()){
            colors.add(colorsRes.getColor(i, 0))
        }
        colorsRes.recycle()
        return colors
    }

    private fun selectColorsSpinner() {
        val listColors = arrayListOf<Int>()
        listColors.addAll(getColorBackgroundItems())
        val adapterColors = ColorDropDownAdapter(requireContext(), listColors)
        val item = binding.spinner
        item.adapter = adapterColors
        item.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, p2: Int, p3: Long) {
                colorBgButton = listColors[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun stateButtonUserCustomSettings() {
        viewModel.gameSettings.observe(viewLifecycleOwner){
            binding.buttonListCustomSettings.isEnabled = !it.isEmpty()
        }
    }

    private fun validInputFields() {
        viewModel.validInputFields.observe(viewLifecycleOwner){
            if (it) {
                showSnackBar()
                with(binding){
                    defaultValueFields(
                        etTitle,
                        etMaxSumValue,
                        etMinCountOfRightAnswers,
                        etMinPercentOfRightAnswers,
                        etGameTimeInSeconds,
                        cbWithoutTimeGame,
                        tilGameTimeInSeconds
                    )
                }
                viewModel.resetInputValidFields()
            }
        }
    }

    private fun showSnackBar() {
        val snackBar = Snackbar.make(
            requireContext(),
            binding.root,
            getString(R.string.snack_bar_add_custom_settings),
            Snackbar.LENGTH_SHORT
        )
        val view = snackBar.view
        val tv = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            tv.textAlignment = TEXT_ALIGNMENT_CENTER;
        } else {
            tv.gravity = Gravity.CENTER_HORIZONTAL;
        }
        snackBar.show()
    }

    private fun defaultValueFields(
        etTitle: AppCompatEditText,
        etMaxSumValue: AppCompatEditText,
        etMinCountOfRightAnswers: AppCompatEditText,
        etMinPercentOfRightAnswers: AppCompatEditText,
        etGameTimeInSeconds: AppCompatEditText,
        cbWithoutTimeGame: AppCompatCheckBox,
        tilGameTimeInSeconds: TextInputLayout
    ) {
        etTitle.setText("")
        etMaxSumValue.setText("")
        etMinCountOfRightAnswers.setText("")
        etMinPercentOfRightAnswers.setText("")
        etGameTimeInSeconds.setText("")
        cbWithoutTimeGame.isChecked = false
        tilGameTimeInSeconds.isEnabled = true
        etTitle.clearFocus()
        etMaxSumValue.clearFocus()
        etMinCountOfRightAnswers.clearFocus()
        etMinPercentOfRightAnswers.clearFocus()
        etGameTimeInSeconds.clearFocus()
        cbWithoutTimeGame.clearFocus()

        val imm: InputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(etTitle.windowToken, 0)
    }

    private fun textChangedListeners() {
        binding.etTitle.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputTitle()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.etMaxSumValue.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputMaxSumValue()
                viewModel.resetErrorInputMaxSumValueMin()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.etMinCountOfRightAnswers.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputMinCountOfRightAnswers()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.etMinPercentOfRightAnswers.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputMinPercentOfRightAnswers()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.etGameTimeInSeconds.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputGameTimeInSeconds()
                viewModel.resetErrorInputGameTimeInSecondsMaxValue()
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
        binding.cbWithoutTimeGame.setOnCheckedChangeListener { button, isChecked ->
            if (isChecked) {
                Log.d("userInfo", "checkbox = true")
            } else {

                Log.d("userInfo", "checkbox = false")
            }
        }
    }

    private fun observerErrors() {
        viewModel.errorInputTitle.observe(viewLifecycleOwner){
            val messageError = if (it){
                getString(R.string.error_message_edit_text)
            } else {
                null
            }
            binding.tilTitle.error = messageError
        }
        viewModel.errorInputMaxSumValue.observe(viewLifecycleOwner){
            val messageError = if (it){
                getString(R.string.error_message_edit_text)
            } else {
                null
            }
            binding.tilMaxSumValue.error = messageError
        }
        viewModel.errorInputMaxSumValueMin.observe(viewLifecycleOwner){
            val messageError = if (it){
                getString(R.string.error_message_edit_text_min_value)
            } else {
                null
            }
            binding.tilMaxSumValue.error = messageError
        }
        viewModel.errorInputMinCountOfRightAnswers.observe(viewLifecycleOwner){
            val messageError = if (it){
                getString(R.string.error_message_edit_text)
            } else {
                null
            }
            binding.tilMinCountOfRightAnswers.error = messageError
        }
        viewModel.errorInputMinPercentOfRightAnswers.observe(viewLifecycleOwner){
            val messageError = if (it){
                getString(R.string.error_message_edit_text)
            } else {
                null
            }
            binding.tilMinPercentOfRightAnswers.error = messageError
        }
        viewModel.errorInputGameTimeInSeconds.observe(viewLifecycleOwner){
            val messageError = if (it){
                getString(R.string.error_message_edit_text)
            } else {
                null
            }
            binding.tilGameTimeInSeconds.error = messageError
        }
        viewModel.errorInputGameTimeInSecondsMaxValue.observe(viewLifecycleOwner){
            val messageError = if (it){
                getString(R.string.error_message_edit_text_max_value)
            } else {
                null
            }
            binding.tilGameTimeInSeconds.error = messageError
        }
    }

    private fun launchUserListCustomFragment() {
        findNavController().navigate(
           CustomSettingsFragmentDirections.actionCustomSettingsFragmentToUserCustomSettings()
        )
    }

    private fun setOnClickListeners() {
        with(binding){
            buttonAddSettings.setOnClickListener {
                saveCustomSettings()
            }
            buttonListCustomSettings.setOnClickListener {
                launchUserListCustomFragment()
            }
            cbWithoutTimeGame.setOnClickListener{
                tilGameTimeInSeconds.isEnabled = !cbWithoutTimeGame.isChecked
            }
        }
    }

    private fun saveCustomSettings() {
        with(binding) {
            viewModel.setDataFields(
                _isChecked = cbWithoutTimeGame.isChecked,
                _title = etTitle.text.toString(),
                _colorBg = colorBgButton,
                _maxSumValue = etMaxSumValue.text.toString(),
                _minCountOfRightAnswers = etMinCountOfRightAnswers.text.toString(),
                _minPercentOfRightAnswers = etMinPercentOfRightAnswers.text.toString(),
                _gameTimeInSeconds = etGameTimeInSeconds.text.toString()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            tilGameTimeInSeconds.isEnabled = !cbWithoutTimeGame.isChecked
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
