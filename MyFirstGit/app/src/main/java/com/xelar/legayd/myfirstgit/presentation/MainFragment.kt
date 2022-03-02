package com.xelar.legayd.myfirstgit.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.xelar.legayd.myfirstgit.R
import com.xelar.legayd.myfirstgit.presentation.adapters.CategoryCongratulationAdapter
import com.xelar.legayd.myfirstgit.presentation.adapters.CongratulationAdapter
import com.xelar.legayd.myfirstgit.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[CongratulationViewModel::class.java]
    }
    private val categoryAdapter = CategoryCongratulationAdapter()
    private val congratulationAdapter = CongratulationAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRV()

        observeViewModel()

        setupClickListeners()
    }

    private fun setupClickListeners() {
        congratulationAdapter.onCongratulationClickListener =
            {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToCongratulationItemFragment(it.id)
                )
            }
        categoryAdapter.onCategoryClickListener = {
            Toast.makeText(
                context,
                "Нажата категория '${it.name}'",
                Toast.LENGTH_SHORT
            ).show()
            showNotification(it.name)
        }
    }

    private fun showNotification(cat: String) {
        val notificationManager =
            requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "channel_id",
                "channel_name",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notification = NotificationCompat.Builder(requireContext(), "channel_id")
            .setContentTitle("Сообщение от меня")
            .setContentText("Нажата категория $cat")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setChannelId("channel_id")
            .build()
        notificationManager.notify(1, notification)

    }

    private fun observeViewModel() {
        viewModel.listCongratulation.observe(viewLifecycleOwner) {
            Log.d("TAG", it.toString())
            congratulationAdapter.submitList(it)
        }
        viewModel.listCategoryCongratulation.observe(viewLifecycleOwner) {
            Log.d("TAG", it.toString())
            categoryAdapter.listCategoryCongratulation = it
        }
    }

    private fun initRV() = with(binding) {
        rvCategoryCongratulations.adapter = categoryAdapter
        rvCongratulations.adapter = congratulationAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}