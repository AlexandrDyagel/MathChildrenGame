package com.xelar.legayd.congratulationapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio

object ImagePicker {

    const val PICK_REQUEST_CODE = 433
    const val CAPTURE_REQUEST_CODE = 422
    const val CAMERA_PERMISSION_CODE = 100

    fun getIntentImageFromStorage() = Intent(Intent.ACTION_PICK)
        .apply {
            type = "image/*"
        }

    fun getIntentImageFromCamera() = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
}