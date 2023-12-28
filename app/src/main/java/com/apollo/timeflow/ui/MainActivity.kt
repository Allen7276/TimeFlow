package com.apollo.timeflow.ui

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.apollo.timeflow.DateBroadcast
import com.apollo.timeflow.R
import com.apollo.timeflow.TimeBroadcast
import com.apollo.timeflow.databinding.ActivityMainBinding
import com.apollo.timeflow.imageHash
import com.apollo.timeflow.lifecycleUtils.LifecycleUtils.quickStartCoroutineScope
import com.apollo.timeflow.viewmodel.MainViewModel
import com.apollo.timeflow.viewmodel.MainViewModelProviderFactory
import kotlinx.coroutines.flow.collectLatest
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(
            this, MainViewModelProviderFactory()
        )[MainViewModel::class.java]
    }
    private lateinit var timeChangeReceiver: TimeBroadcast
    private lateinit var dateChangeReceiver: DateBroadcast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        handleViewModel()
        updateTimeFormat()

        binding?.materialCardView?.setOnClickListener {
            val afterChangeDateShow = (binding?.tvDate?.visibility == View.GONE)
            mainViewModel.isDateShow(afterChangeDateShow)
        }

        binding?.clockCardView?.setOnClickListener {
            mainViewModel.timeFormatRecordUpdate(
                binding?.tvTimeFormatInAlarmActivity?.visibility in setOf(
                    View.VISIBLE
                )
            )
            mainViewModel.updateTime()
        }

        val intentFilterTimeChange = IntentFilter()
        intentFilterTimeChange.addAction(Intent.ACTION_TIME_TICK)
        intentFilterTimeChange.addAction(Intent.ACTION_TIME_CHANGED)
        intentFilterTimeChange.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        intentFilterTimeChange.addAction(Intent.ACTION_LOCALE_CHANGED)

        timeChangeReceiver = TimeBroadcast {
            mainViewModel.updateTime()
        }

        ContextCompat.registerReceiver(
            applicationContext,
            timeChangeReceiver,
            intentFilterTimeChange,
            ContextCompat.RECEIVER_NOT_EXPORTED
        )

        val intentFilterDateChange = IntentFilter()
        intentFilterDateChange.addAction(Intent.ACTION_DATE_CHANGED)
        intentFilterDateChange.addAction(Intent.ACTION_TIMEZONE_CHANGED)
        intentFilterDateChange.addAction(Intent.ACTION_LOCALE_CHANGED)

        dateChangeReceiver = DateBroadcast(mainViewModel)
        registerReceiver(dateChangeReceiver, intentFilterDateChange)

        mainViewModel.updateDate()
        mainViewModel.updateTime()
    }

    override fun onResume() {
        super.onResume()
        mainViewModel.updateTime()
    }

    /**
     * ## ViewModel的监听
     */
    private fun handleViewModel() {
        quickStartCoroutineScope {
            mainViewModel.topLeftModelFlowOf.collectLatest {
                binding?.topLeftAlarmNumberInAlarmActivity?.setImageResource(imageHash[it]!!)
            }
        }

        quickStartCoroutineScope {
            mainViewModel.topRightModelFlowOf.collectLatest {
                binding?.topRightAlarmNumberInAlarmActivity?.setImageResource(imageHash[it]!!)
            }
        }

        quickStartCoroutineScope {
            mainViewModel.bottomLeftModelFlowOf.collectLatest {
                binding?.boottomLeftAlarmNumberInAlarmActivity?.setImageResource(imageHash[it]!!)
            }
        }

        quickStartCoroutineScope {
            mainViewModel.bottomRightModelFlowOf.collectLatest {
                binding?.bottomRightAlarmNumberInAlarmActivity?.setImageResource(imageHash[it]!!)
            }
        }

        quickStartCoroutineScope {
            mainViewModel.currentDate.collectLatest {
                binding?.tvDate?.text = it
            }
        }

        quickStartCoroutineScope {
            mainViewModel.timeFormat.collectLatest {
                updateTimeFormat()
                mainViewModel.updateTime()
            }
        }

        quickStartCoroutineScope {
            mainViewModel.isDateShowDataStoreFlow.collectLatest {
                binding?.tvDate?.visibility = if (it) View.VISIBLE else View.GONE
                mainViewModel.updateDate()
            }
        }

        quickStartCoroutineScope {
            mainViewModel.timeFormatRecordDataStoreFlow.collectLatest {
                binding?.tvTimeFormatInAlarmActivity?.visibility =
                    if (it) View.GONE else View.VISIBLE
                mainViewModel.editTimeFormat(if (it) MainViewModel.TimeFormat.Base24 else MainViewModel.TimeFormat.Base12)
            }
        }
    }

    /**
     * ## 时间格式的初始化
     */
    private fun updateTimeFormat() {
        binding?.tvTimeFormatInAlarmActivity?.text =
            if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 12) {
                this.baseContext.getString(R.string.pm)
            } else {
                this.baseContext.getString(R.string.am)
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
            }
        }
    }
}