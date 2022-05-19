package com.xelar.legayd.mathchildrengame.presentation.dialogs

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.xelar.legayd.mathchildrengame.R
import com.xelar.legayd.mathchildrengame.databinding.FragmentChooseLevelBinding
import com.xelar.legayd.mathchildrengame.databinding.FragmentDialogAppEvaluationBinding

class DialogAppEvaluationFragment : DialogFragment() {

    companion object{
        private const val URL_APP_IN_GOOGLE_STORE =
            "https://play.google.com/store/apps/details?id=org.telegram.messenger" // ?id=${context.packageName}
    }

    private var _binding: FragmentDialogAppEvaluationBinding? = null
    private val binding: FragmentDialogAppEvaluationBinding
        get() = _binding ?: throw RuntimeException("FragmentDialogAppEvaluationBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogAppEvaluationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonUnderstand.setOnClickListener{
            transitionToEvaluation()
        }
    }

    private fun transitionToEvaluation() {
        val share = Intent().apply {
            action = Intent.ACTION_VIEW
            data =
                Uri.parse(URL_APP_IN_GOOGLE_STORE)
        }
        startActivity(share)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}