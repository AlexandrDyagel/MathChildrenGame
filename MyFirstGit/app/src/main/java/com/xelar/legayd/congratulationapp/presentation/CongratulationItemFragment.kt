package com.xelar.legayd.congratulationapp.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.xelar.legayd.congratulationapp.databinding.FragmentCongratulationItemBinding
import com.xelar.legayd.congratulationapp.domain.entities.Congratulation
import com.xelar.legayd.congratulationapp.utils.GetImageContract
import com.xelar.legayd.congratulationapp.utils.ImagePicker
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.jsoup.Jsoup


class CongratulationItemFragment : Fragment() {
    var ivUri: Uri? = null
    val imageUri = registerForActivityResult(GetImageContract()) {
        Log.d("ADS", it.toString())
        binding.ivImage.setImageURI(it)
    }

    private val args by navArgs<CongratulationItemFragmentArgs>()

    private var _binding: FragmentCongratulationItemBinding? = null
    private val binding: FragmentCongratulationItemBinding
        get() = _binding ?: throw RuntimeException("FragmentCongratulationItemBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[CongratulationItemViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCongratulationItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCongratItem(args.congratulationId).observe(viewLifecycleOwner) {
            showData(it)
        }

        binding.btnMore.setOnClickListener {
            //selectImageFromCamera()
            sendMessage(binding.etMessage.text.toString())
        }

        binding.btnDeleteLink.setOnClickListener {
            //selectImageFromStorage()
            imageUri.launch("image/*")
        }
    }

    private fun sendMessage(message: String) {
        val sendIntent = Intent()
        with(sendIntent) {
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, Uri.parse("content://com.atomicadd.fotos.media/photo/18589"))
            //putExtra(Intent.EXTRA_TEXT, message)
            setDataAndType(Uri.parse("content://com.atomicadd.fotos.media/photo/18589"), "image/*")
            //setType("text/plain")
            //setType("image/*")
        }
        startActivity(Intent.createChooser(sendIntent, "Через что будем поздравлять?"))
    }

    private fun showData(congratulation: Congratulation) {
        with(binding) {
            etMessage.setText(congratulation.message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun selectImageFromCamera(){
        val intent = ImagePicker.getIntentImageFromCamera()
        if (checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                ImagePicker.CAMERA_PERMISSION_CODE)
        } else {
            startActivityForResult(intent, ImagePicker.CAPTURE_REQUEST_CODE)
        }
    }

    fun selectImageFromStorage() {
        val intent = Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
        startActivityForResult(intent, ImagePicker.PICK_REQUEST_CODE)
    }


    var i = 1
    var count = 0
    val listCongrat = arrayListOf<String>()
    private fun parseHtml(page: Int, maxPage: Int) {
         Observable.fromCallable {
            Jsoup.connect("https://pozdravok.ru/pozdravleniya/den-rozhdeniya/$page").get()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
             .map { it.normalise() }
            .map {it.select("div.content p") }
            .subscribe ({

                count += it.size
                if (i <= maxPage) {
                    parseHtml(++i, maxPage)
                    binding.etMessage.setText(it[0].text())
                    Log.d("ADS", "Count = ${it.text()}")
                }
                //Log.d("ADS", it.size.toString())
            },{
                Log.d("ADS", "Ошибка: ${it.message}\n Количество поздравлений: $count")
            })
    }
}
