package com.xelar.legayd.congratulationapp.presentation

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.xelar.legayd.congratulationapp.R
import com.xelar.legayd.congratulationapp.utils.ImagePicker
import javax.inject.Inject


class ContainerActivity : AppCompatActivity() {

    @Inject
    lateinit var ctx: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ImagePicker.CAMERA_PERMISSION_CODE) {
            when(requestCode){
                ImagePicker.CAPTURE_REQUEST_CODE -> {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()
                    }
                }
                ImagePicker.PICK_REQUEST_CODE -> {

                }
            }
        }
    }
}


