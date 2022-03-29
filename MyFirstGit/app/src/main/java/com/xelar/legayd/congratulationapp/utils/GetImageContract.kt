package com.xelar.legayd.congratulationapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract


class GetImageContract() : ActivityResultContract<String, Uri?>() {
    override fun createIntent(context: Context, input: String): Intent {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = input
        }
        return intent
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? = when {
        resultCode != Activity.RESULT_OK -> null
        else -> intent?.data
    }


}