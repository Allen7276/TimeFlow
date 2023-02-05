package com.aseane.timeflow

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.aseane.timeflow.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val alarmViewModel: MainViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }
    private lateinit var timeChangeReceiver: TimeBroadcast
    private lateinit var dateChangeReceiver: DateBroadcast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        _binding = ActivityMainBinding.inflate(layoutInflater).apply {
            this.viewModel = alarmViewModel
        }
        setContentView(binding.root)

        binding.materialCardView.setOnClickListener {
            binding.tvDate.visibility = when (binding.tvDate.visibility) {
                View.GONE, View.INVISIBLE -> {
                    View.VISIBLE
                }
                else -> {
                    View.GONE
                }
            }
        }

        handleViewModel()

        timeFormatViewModel()

        val intentFilterTimeChange = IntentFilter()
        intentFilterTimeChange.addAction(Intent.ACTION_TIME_TICK)
        intentFilterTimeChange.addAction(Intent.ACTION_TIME_CHANGED)
        intentFilterTimeChange.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        intentFilterTimeChange.addAction(Intent.ACTION_LOCALE_CHANGED)

        timeChangeReceiver = TimeBroadcast(alarmViewModel)
        registerReceiver(timeChangeReceiver, intentFilterTimeChange)

        val intentFilterDateChange = IntentFilter()
        intentFilterDateChange.addAction(Intent.ACTION_DATE_CHANGED)
        intentFilterDateChange.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        intentFilterDateChange.addAction(Intent.ACTION_LOCALE_CHANGED)

        dateChangeReceiver = DateBroadcast(alarmViewModel)
        registerReceiver(dateChangeReceiver, intentFilterDateChange)
    }

    override fun onResume() {
        super.onResume()
        alarmViewModel.updateTime()
        alarmViewModel.updateDate()
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
//                binding.timeFormatInAlarmActivity.visibility = View.GONE
                binding.tvTimeFormatInAlarmActivity.visibility = View.GONE
            } else {
//                binding.timeFormatInAlarmActivity.visibility = View.VISIBLE
                binding.tvTimeFormatInAlarmActivity.visibility = View.VISIBLE
            }
            alarmViewModel.updateTime()
        }
        alarmViewModel.currentDate.observe(this) {
            binding.tvDate.text = it
        }
    }

    /**
     * ## 时间格式的初始化
     */
    private fun timeFormatViewModel() {
        // 先对 timeFormat进行初始化
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 12) {
            // 中午12点之后
//            binding.timeFormatInAlarmActivity.setImageResource(timeFormatHash["pm"]!!)
            binding.tvTimeFormatInAlarmActivity.text = this.baseContext.getString(R.string.pm)
        } else {
            // 中午12点之前
//            binding.timeFormatInAlarmActivity.setImageResource(timeFormatHash["am"]!!)
            binding.tvTimeFormatInAlarmActivity.text = this.baseContext.getString(R.string.am)
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        // 开启全屏 隐藏状态栏
        WindowCompat.getInsetsController(window, window.decorView)?.apply {
            if (hasFocus) {
                this.hide(WindowInsetsCompat.Type.statusBars())
            } else {
                this.show(WindowInsetsCompat.Type.statusBars())
            }
        }
    }
}