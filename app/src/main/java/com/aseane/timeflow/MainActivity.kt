package com.aseane.timeflow

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.aseane.timeflow.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val alarmViewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private lateinit var timeChangeReceiver: TimeBroadcast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        // 开启全屏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleViewModel()

        timeFormatViewModel()

        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_TIME_TICK)
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED)
        intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        intentFilter.addAction(Intent.ACTION_LOCALE_CHANGED)

        timeChangeReceiver = TimeBroadcast(alarmViewModel)
        registerReceiver(timeChangeReceiver, intentFilter)

        binding.clockCardView.setOnClickListener {
            alarmViewModel.changeCalendarFormat()
        }
    }

    override fun onResume() {
        super.onResume()
        alarmViewModel.updateTime()
    }

    /**
     * ## ViewModel的监听
     */
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
        alarmViewModel.timeFormat.observe(this) {
            if (alarmViewModel.timeFormat.value == MainViewModel.TimeFormat.Base24) {
                timeFormatViewModel()
                binding.timeFormatInAlarmActivity.visibility = View.GONE
            } else {
                binding.timeFormatInAlarmActivity.visibility = View.VISIBLE
            }
            alarmViewModel.updateTime()
        }
    }

    /**
     * ## 时间格式的初始化
     */
    private fun timeFormatViewModel() {
        // 先对 timeFormat进行初始化
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 12) {
            // 中午12点之后
            binding.timeFormatInAlarmActivity.setImageResource(timeFormatHash["pm"]!!)
        } else {
            // 中午12点之前
            binding.timeFormatInAlarmActivity.setImageResource(timeFormatHash["am"]!!)
        }
    }
}