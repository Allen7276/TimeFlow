package com.aseane.timeflow.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aseane.timeflow.DateBroadcast
import com.aseane.timeflow.R
import com.aseane.timeflow.TimeBroadcast
import com.aseane.timeflow.databinding.ActivityMainBinding
import com.aseane.timeflow.imageHash
import com.aseane.timeflow.viewmodel.MainViewModel
import com.aseane.timeflow.viewmodel.MainViewModelProviderFactory
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelProviderFactory()
        )[MainViewModel::class.java]
    }
    private lateinit var timeChangeReceiver: TimeBroadcast
    private lateinit var dateChangeReceiver: DateBroadcast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.isDateShowDataStoreFlow.collect {
                    binding.tvDate.visibility = if (it) View.GONE else View.VISIBLE
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.timeFormatRecordDataStoreFlow.collect {
                    binding.tvTimeFormatInAlarmActivity.visibility =
                        if (it) View.VISIBLE else View.GONE
                }
            }
        }

        binding.materialCardView.setOnClickListener {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mainViewModel.isDateShow(binding.tvDate.visibility in setOf(View.VISIBLE))
                }
            }
        }

        binding.clockCardView.setOnClickListener {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mainViewModel.timeFormatRecordUpdate(
                        binding.tvTimeFormatInAlarmActivity.visibility in setOf(
                            View.GONE
                        )
                    )
                    mainViewModel.updateTime()
                    updateTimeFormat()
                }
            }
        }

        handleViewModel()

        updateTimeFormat()

        val intentFilterTimeChange = IntentFilter()
        intentFilterTimeChange.addAction(Intent.ACTION_TIME_TICK)
        intentFilterTimeChange.addAction(Intent.ACTION_TIME_CHANGED)
        intentFilterTimeChange.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        intentFilterTimeChange.addAction(Intent.ACTION_LOCALE_CHANGED)

        timeChangeReceiver = TimeBroadcast(mainViewModel)
        registerReceiver(timeChangeReceiver, intentFilterTimeChange)

        val intentFilterDateChange = IntentFilter()
        intentFilterDateChange.addAction(Intent.ACTION_DATE_CHANGED)
        intentFilterDateChange.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        intentFilterDateChange.addAction(Intent.ACTION_LOCALE_CHANGED)

        dateChangeReceiver = DateBroadcast(mainViewModel)
        registerReceiver(dateChangeReceiver, intentFilterDateChange)
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.updateTime()
        mainViewModel.updateDate()
    }

    /**
     * ## ViewModel的监听
     */
    private fun handleViewModel() {
        mainViewModel.topLeftModel.observe(this) {
            binding.topLeftAlarmNumberInAlarmActivity.setImageResource(imageHash[it]!!)
        }

        mainViewModel.topRightModel.observe(this) {
            binding.topRightAlarmNumberInAlarmActivity.setImageResource(imageHash[it]!!)
        }

        mainViewModel.bottomLeftModel.observe(this) {
            binding.boottomLeftAlarmNumberInAlarmActivity.setImageResource(imageHash[it]!!)
        }

        mainViewModel.bottomRightModel.observe(this) {
            binding.bottomRightAlarmNumberInAlarmActivity.setImageResource(imageHash[it]!!)
        }

        mainViewModel.currentDate.observe(this) {
            binding.tvDate.text = it
        }
    }

    /**
     * ## 时间格式的初始化
     */
    private fun updateTimeFormat() {
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
        // 开启全屏
        // 隐藏顶部状态栏和使用全面屏手势操作时候的底部状态栏
        WindowCompat.getInsetsController(window, window.decorView).apply {
            if (hasFocus) {
                this.hide(WindowInsetsCompat.Type.statusBars())
                this.hide(WindowInsetsCompat.Type.navigationBars())
            } else {
                this.show(WindowInsetsCompat.Type.statusBars())
                this.show(WindowInsetsCompat.Type.navigationBars())
            }
        }
    }
}