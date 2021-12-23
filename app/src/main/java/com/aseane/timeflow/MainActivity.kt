package com.aseane.timeflow

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aseane.timeflow.Model.changeCalendarFormat
import com.aseane.timeflow.Model.updateTime
import com.aseane.timeflow.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var alarmViewModel: MainViewModel
    private lateinit var timeChangeReceiver: TimeBoardcast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alarmViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        handleViewModel()
        timeFormatViewModel()

        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_TIME_TICK)
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED)
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED)

        timeChangeReceiver = TimeBoardcast(alarmViewModel)
        registerReceiver(timeChangeReceiver, intentFilter)

        binding.clockCardView.setOnClickListener {
            changeCalendarFormat(alarmViewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        updateTime(alarmViewModel)
    }

    private fun handleViewModel() {
        alarmViewModel.topLeftModel.observe(this) {
            binding.topLeftAlarmNumberInAlarmActivity.setImageResource(imageHash[it]!!)
        }
        alarmViewModel.topRightModel.observe(this) {
            binding.topRightAlarmNumberInAlarmActivity.setImageResource(imageHash[it]!!)
        }
        alarmViewModel.bottomLeftModel.observe(this) {
            binding.boottomLeftAlarmNumberInAlarmActivity.setImageResource(imageHash[it]!!)
        }
        alarmViewModel.bottomRightModel.observe(this) {
            binding.bottomRightAlarmNumberInAlarmActivity.setImageResource(imageHash[it]!!)
        }
    }

    private fun timeFormatViewModel() {
        // 先对 timeFormat进行初始化
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)>=12) binding.timeFormatInAlarmActivity.setImageResource(timeFormatHash["pm"]!!)
        else binding.timeFormatInAlarmActivity.setImageResource(timeFormatHash["am"]!!)
        alarmViewModel.timeFormat.observe(this) {
            if (alarmViewModel.timeFormat.value == MainViewModel.TimeFormat.Base24) {
                if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)>=12) binding.timeFormatInAlarmActivity.setImageResource(timeFormatHash["pm"]!!)
                else binding.timeFormatInAlarmActivity.setImageResource(timeFormatHash["am"]!!)
                binding.timeFormatInAlarmActivity.visibility = View.GONE
            }
            else {
                binding.timeFormatInAlarmActivity.visibility = View.VISIBLE
            }
            updateTime(alarmViewModel)
        }
    }
}