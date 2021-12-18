package com.aseane.timeflow

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.aseane.timeflow.databinding.ActivityMainBinding

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

        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_TICK")

        timeChangeReceiver = TimeBoardcast(alarmViewModel)
        registerReceiver(timeChangeReceiver, intentFilter)
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
}