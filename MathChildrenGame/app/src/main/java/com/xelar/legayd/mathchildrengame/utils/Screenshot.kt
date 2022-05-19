package com.xelar.legayd.mathchildrengame.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.os.StrictMode
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.Group
import com.xelar.legayd.mathchildrengame.R
import java.io.File
import java.io.FileOutputStream

class Screenshot(private val context: Context) {

    fun sharePicture(fileName: String, contentView: View, sectionBadge: Group) {
        //TODO("что-то нужно делать с бейджем")

        val bitmap = Bitmap.createBitmap(contentView.width, contentView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = contentView.background
        bgDrawable.draw(canvas)
        contentView.draw(canvas)

        val path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val fileNameExt = "$fileName.jpg"
        var fileScreenShot = File(path, fileNameExt)
        if (fileScreenShot.exists()) {
            fileScreenShot.delete()
            fileScreenShot = File(path, fileName)
        }
        try {
            val fileOutputStream = FileOutputStream(fileScreenShot)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
            sectionBadge.visibility = View.GONE
        } catch (ex: Exception) {
            sectionBadge.visibility = View.GONE
            Toast.makeText(context, "Ошибка отправки", Toast.LENGTH_SHORT).show()
        }
        shareScreenshot(fileScreenShot)
    }

    private fun shareScreenshot(fileScreenShot: File) {
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        val bmpUri = Uri.fromFile(fileScreenShot)
        if (bmpUri != null) {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, bmpUri)
                type = "image/*"
            }
            context.startActivity(
                Intent.createChooser(
                    shareIntent,
                    context.getString(R.string.share_screenshot_chooser_title)
                )
            )
        }
    }
}